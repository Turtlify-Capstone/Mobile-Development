package com.bangkit.turtlify.ui.report

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.turtlify.R
import com.bangkit.turtlify.databinding.FragmentReportTurtleBinding
import com.bangkit.turtlify.ui.viemodels.ReportTurtleViewModel
import com.bangkit.turtlify.ui.viemodels.Turtle
import com.bumptech.glide.Glide

data class TurtleLocation(
    var lat: String,
    var long: String
)
data class FormData(
    var reporterName: String,
    var turtleLocation: TurtleLocation,
    var contactId: String,
    var turtleId: String
)


class ReportTurtleFragment : Fragment() {
    private var binding: FragmentReportTurtleBinding? = null
    private var contactList: MutableList<String> = mutableListOf()
    private var turtleList: MutableList<Turtle> = mutableListOf()

    private lateinit var contactsAdapter: ArrayAdapter<String>
    private lateinit var turtlesAdapter: TurtleAdapter
    private lateinit var viewModel: ReportTurtleViewModel
    private var formData = FormData("", TurtleLocation("",""),"","")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReportTurtleBinding.inflate(inflater, container, false)
        binding!!.btnSubmitReport.setOnClickListener{
            submitReportForm()
        }
        setupViewModel()
        setupContactDropdown()
        setupTurtleDropdown()
        observeViewModel()
        setupLocationEditText()
        return binding?.root
    }

    private fun submitReportForm(){
        formData.reporterName = binding?.edName?.text.toString()
        if (formData.reporterName.isEmpty() ||
            formData.turtleLocation.lat.isEmpty() ||
            formData.turtleLocation.long.isEmpty() ||
            formData.contactId.isEmpty() || formData.turtleId.isEmpty()
        ) {
            Toast.makeText(requireContext(), "Make sure all input's are filled", Toast.LENGTH_LONG).show()
            return
        } else {
            val response = viewModel.submitReportForm(formData)
            if (response != null) {
                Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG).show()
                if (response.success) clearForm()
            }
        }
    }

    private fun clearForm(){
        binding?.edName?.setText("")
        binding?.edLocation?.setText("")
        binding?.contactDropdownSelector?.setText("")
        binding?.turtleDropdownSelector?.setText("")
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[ReportTurtleViewModel::class.java]
        contactsAdapter = ArrayAdapter(requireContext(), R.layout.contact_dropdown_item, contactList)
        contactsAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        turtlesAdapter = TurtleAdapter(requireContext(), turtleList)
        turtlesAdapter.setDropDownViewResource(R.layout.turtle_dropdown_item)
    }

    private fun setupContactDropdown() {
        binding?.contactDropdownSelector?.setAdapter(contactsAdapter)
        binding?.contactDropdownSelector?.setOnItemClickListener{ _, _, position, _ ->
            val selectedItem = contactList[position]
            formData.contactId = selectedItem
        }
    }

    private fun setupTurtleDropdown() {
        binding?.turtleDropdownSelector?.setAdapter(turtlesAdapter)
        binding?.turtleDropdownSelector?.setOnItemClickListener{ _, _,position, _ ->
            val selectedTurtle = turtlesAdapter.getItem(position)
            val selectedTurtleName = selectedTurtle?.name ?: ""
            //TODO 1 change the tuertle name to turtle id
            formData.turtleId = selectedTurtle?.name ?: ""

            binding?.turtleDropdown?.editText?.setText(selectedTurtleName)
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
            turtlesAdapter.notifyDataSetChanged()
        }
        viewModel.isFormSubmitting.observe(viewLifecycleOwner){isSubmitting ->
            binding?.progressBar?.visibility  = if (isSubmitting) View.VISIBLE else View.GONE
        }
        viewModel.fetchContacts()
        viewModel.fetchTurtles()
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

class TurtleAdapter(private val mContext: Context, mTurtleList: List<Turtle>) :
    ArrayAdapter<Turtle?>(mContext, 0, mTurtleList) {
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
        val turtle = getItem(position)

        if (turtle != null && textView != null && imageView != null) {
            textView.text = turtle.name
            Glide.with(mContext).load(turtle.image).centerCrop()
                .into(imageView)
        }

        return view ?: LayoutInflater.from(mContext).inflate(R.layout.turtle_dropdown_item, parent, false)
    }
}