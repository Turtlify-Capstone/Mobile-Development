package com.bangkit.turtlify.ui.report

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bangkit.turtlify.R
import com.bangkit.turtlify.data.model.report.FormData
import com.bangkit.turtlify.data.model.report.Report
import com.bangkit.turtlify.data.model.report.TurtleLocation
import com.bangkit.turtlify.data.network.model.FetchTurtlesResponseItem
import com.bangkit.turtlify.databinding.FragmentReportTurtleBinding
import com.bangkit.turtlify.ui.maps.MapsViewModel
import com.bangkit.turtlify.ui.settings.SettingsActivity
import com.bangkit.turtlify.ui.viemodels.ReportTurtleViewModel
import com.bangkit.turtlify.utils.ViewModelFactory

import com.bumptech.glide.Glide

class ReportTurtleFragment : Fragment() {
    private var binding: FragmentReportTurtleBinding? = null
    private var contactList: MutableList<String> = mutableListOf()
    private var turtleList: MutableList<FetchTurtlesResponseItem> = mutableListOf()
    private var simpleTurtle: MutableList<String> = mutableListOf()

    private lateinit var contactsAdapter: ArrayAdapter<String>
    private lateinit var turtlesAdapter: TurtleAdapter

    private val viewModel by viewModels<ReportTurtleViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private var formData = FormData("", "", TurtleLocation("",""),"","")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReportTurtleBinding.inflate(inflater, container, false)

        setupViewModel()
        setupContactDropdown()
        setupTurtleDropdown()
        observeViewModel()
        setupLocationEditText()

        binding!!.btnSubmitReport.setOnClickListener{
            submitReportForm()
        }
        binding!!.logoSettings.setOnClickListener{
            startActivity(Intent(activity, SettingsActivity::class.java))
        }

        return binding?.root
    }

    private fun submitReportForm(){
        formData.reporterName = binding?.edReporterName?.text.toString()
        formData.reporterContact = binding?.edReporterContect?.text.toString()
        val name = formData.reporterName
        val email = formData.reporterContact
        val location = binding?.edLocation?.text.toString()
        val contactBksda = formData.contact
        val turtle = formData.turtleName

        val message = "Nama: $name \n" +
                "Tujuan BKSDA: $contactBksda \n" +
                "$location \n" +
                "Kura-kura yang ditemukan: $turtle"

        val reportData = Report(email, message)
        if (formData.reporterName.isEmpty() ||
            formData.reporterContact.isEmpty() ||
            formData.turtleLocation.lat.isEmpty() ||
            formData.turtleLocation.long.isEmpty() ||
            formData.contact.isEmpty() || formData.turtleName.isEmpty()
        ) {
            Toast.makeText(requireContext(), "Make sure all input's are filled", Toast.LENGTH_LONG).show()
            return
        } else {
            viewModel.submitReportForm(reportData) { success ->
                if (success) {
                    clearForm()
                    Toast.makeText(requireContext(), "Report sent successfully ", Toast.LENGTH_SHORT).show()
                    Log.d(ContentValues.TAG, "sendFeedback: Email Succesfully Send ")
                } else {
                    Toast.makeText(requireContext(), "Failed to send Report", Toast.LENGTH_SHORT).show()
                    Log.d(ContentValues.TAG, "sendFeedback: Email Failed to Send ")
                }
            }
        }
    }

    private fun clearForm(){
        binding?.edReporterName?.setText("")
        binding?.edReporterContect?.setText("")
        binding?.edLocation?.setText("")
        binding?.contactDropdownSelector?.setText("")
        binding?.turtleDropdownSelector?.setText("")
    }

    private fun setupViewModel() {
        contactsAdapter = ArrayAdapter(requireContext(), R.layout.contact_dropdown_item, contactList)
        contactsAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        turtlesAdapter = TurtleAdapter(requireContext(), simpleTurtle, turtleList)
        turtlesAdapter.setDropDownViewResource(R.layout.turtle_dropdown_item)
    }

    private fun setupContactDropdown() {
        binding?.contactDropdownSelector?.setAdapter(contactsAdapter)
        binding?.contactDropdownSelector?.setOnItemClickListener{ _, _, position, _ ->
            val selectedItem = contactList[position]
            formData.contact = selectedItem
        }
    }

    private fun setupTurtleDropdown() {
        binding?.turtleDropdownSelector?.setAdapter(turtlesAdapter)
        binding?.turtleDropdownSelector?.setOnItemClickListener { _, _, position, _ ->
            val selectedTurtle = turtlesAdapter.getItem(position)
            formData.turtleName = selectedTurtle!!
        }
    }

    private fun observeViewModel() {
        viewModel.contactsLiveData.observe(viewLifecycleOwner) { contacts ->
            contactList.clear()
            contactList.addAll(contacts)
            contactsAdapter.notifyDataSetChanged()
        }
        viewModel.turtlesLiveData.observe(viewLifecycleOwner) { turtles ->
            turtleList.clear()
            turtleList.addAll(turtles)
            turtles.forEach {turtle ->
                simpleTurtle.add(turtle.namaLokal!!)
            }
            turtlesAdapter.notifyDataSetChanged()
        }
        viewModel.fetchContacts(
            onError = {errorMsg ->
                Toast.makeText(activity, errorMsg, Toast.LENGTH_LONG).show()
            }
        )
        viewModel.fetchTurtles(
            onError = {errorMsg ->
                Toast.makeText(activity, errorMsg, Toast.LENGTH_LONG).show()
            }
        )
    }

    private fun setupLocationEditText() {
        binding?.edLocation?.setOnFocusChangeListener { _, focused ->
            if (focused) {
                val intent = Intent(requireContext(), LocationSelectorActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val latitude = data?.getDoubleExtra("latitude", 0.0)
            val longitude = data?.getDoubleExtra("longitude", 0.0)
            binding?.edLocation?.setText("Lat: $latitude\nLong: $longitude")
            formData.turtleLocation = TurtleLocation(latitude.toString(), longitude.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val REQUEST_CODE = 123
    }
}

class TurtleAdapter(private val mContext: Context, mTurtleList: List<String>, private val turtleList: List<FetchTurtlesResponseItem>) :
    ArrayAdapter<String?>(mContext, 0, mTurtleList) {
    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    private fun createItemView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = mInflater.inflate(R.layout.turtle_dropdown_item, parent, false)
        }
        val imageView = view?.findViewById<ImageView>(R.id.image_turtle)
        val textView = view?.findViewById<TextView>(R.id.tv_turtle_name)
        val turtleName = getItem(position)
        val imageUrl = turtleList[position].image!!.split(",").first()

        if (turtleName != null && textView != null && imageView != null) {
            textView.text = turtleName
            Glide.with(mContext).load(imageUrl).centerCrop()
                .into(imageView)
        }

        return view ?: LayoutInflater.from(mContext).inflate(R.layout.turtle_dropdown_item, parent, false)
    }
}