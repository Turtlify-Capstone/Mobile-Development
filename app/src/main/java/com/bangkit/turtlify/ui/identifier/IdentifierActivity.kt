package com.bangkit.turtlify.ui.identifier

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.FLASH_MODE_OFF
import androidx.camera.core.ImageCapture.FLASH_MODE_ON
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bangkit.turtlify.R
import com.bangkit.turtlify.data.network.model.AdditionalData
import com.bangkit.turtlify.data.network.model.FetchTurtlesResponseItem
import com.bangkit.turtlify.databinding.ActivityIdentifierBinding
import com.bangkit.turtlify.ui.encyclopediadetail.EncyclopediaDetailActivity
import com.bangkit.turtlify.utils.ViewModelFactory
import com.bangkit.turtlify.utils.createCustomTempFile
import com.bangkit.turtlify.utils.reduceFileImage
import com.bangkit.turtlify.utils.uriToFile
import com.bumptech.glide.Glide

class IdentifierActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIdentifierBinding
    private val viewModel by viewModels<IdentifierViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var currentImageUri: Uri? = null
    private var isFlashOn = false
    private var imageCapture: ImageCapture? = null

    private val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            val message = if (isGranted) "Permission request granted" else "Permission request denied"
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            if (isGranted) startCameraX() else finish()
        }
    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdentifierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        checkPermissions()
    }

    private fun setupViews() {
        with(binding) {
            btnGallery.setOnClickListener { startGallery() }
            btnClose.setOnClickListener {
                if (currentImageUri != null) {
                    currentImageUri = null
                    previewImage.visibility = View.GONE
                    captureImage.setImageResource(R.drawable.baseline_control_camera_24)
                } else {
                    finish()
                }
            }
            captureImageBtn.setOnClickListener { if (currentImageUri != null) uploadImage() else takePhoto() }
            flashLightButton.setOnClickListener {
                isFlashOn = !isFlashOn
                binding.flashLightButtonImage.setImageResource( if (isFlashOn) R.drawable.baseline_flash_on_24 else R.drawable.baseline_flash_off_24)
            }
        }
    }

    private fun checkPermissions() {
        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        } else {
            startCameraX()
        }
    }

    private fun startCameraX() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageCapture
                )
            } catch (exc: Exception) {
                Toast.makeText(
                    this@IdentifierActivity,
                    "Gagal memunculkan kamera.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(TAG, "startCamera: ${exc.message}")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        imageCapture.flashMode = if(isFlashOn) FLASH_MODE_ON else FLASH_MODE_OFF
        val photoFile = createCustomTempFile(application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    Log.d("IMAGEOUTPUT", output.savedUri.toString())
                    currentImageUri = output.savedUri
                    showImage()
                    uploadImage()
                }
                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        this@IdentifierActivity,
                        "Gagal mengambil gambar.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(TAG, "onError: ${exc.message}")
                }
            }
        )
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
            binding.captureImage.setImageResource(R.drawable.baseline_check_24)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImage.visibility = View.VISIBLE
            Glide.with(this)
                .load(currentImageUri).optionalCenterCrop()
                .into(binding.previewImage)
        }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            showLoading(true)
            viewModel.uploadImage(imageFile,
                onSuccess = { responseTurtle ->
                    showLoading(false)

                    currentImageUri = null
                    binding.previewImage.visibility = View.GONE
                    binding.captureImage.setImageResource(R.drawable.baseline_control_camera_24)

                    viewModel.insertTurtle(imageFile.absolutePath, responseTurtle)

                    navigateToDetail(responseTurtle)
                },
                onError = { errorMessage ->
                    showToast(errorMessage)
                    showLoading(false)
                    currentImageUri = null
                    binding.previewImage.visibility = View.GONE
                    binding.captureImage.setImageResource(R.drawable.baseline_control_camera_24)
                }
            )
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun navigateToDetail(responseTurtle : AdditionalData) {
        val intent = Intent(this, EncyclopediaDetailActivity::class.java)
        val turtleData = FetchTurtlesResponseItem(
            responseTurtle.namaLokal,
            responseTurtle.persebaranHabitat,
            responseTurtle.image,
            responseTurtle.habitat,
            responseTurtle.namaLatin,
            responseTurtle.description,
            responseTurtle.latitude,
            responseTurtle.id!!,
            responseTurtle.longitude,
            responseTurtle.statusKonversi
        )
        intent.putExtra("turtleData", turtleData)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressCircular.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
        private const val TAG = "CameraActivity"
    }
}