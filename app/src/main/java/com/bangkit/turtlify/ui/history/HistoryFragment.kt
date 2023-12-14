package com.bangkit.turtlify.ui.history

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.turtlify.R
import com.bangkit.turtlify.data.database.repository.Turtle
import com.bangkit.turtlify.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    private lateinit var viewModel: HistoryViewModel
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initialize RecyclerView from the binding
        val rvHistories = binding.rvHistories
        rvHistories.layoutManager = LinearLayoutManager(requireContext())
        viewModel = ViewModelProvider(this)[HistoryViewModel::class.java]

        viewModel.getHistories()
        viewModel.histories.observe(viewLifecycleOwner) { turtles ->
            turtles?.let { showRecyclerList(it) }
        }
        viewModel.isLoading.observe(viewLifecycleOwner){
            binding.progressCircular.visibility = if (it) View.VISIBLE else View.GONE
        }

        return view
    }

    private fun showRecyclerList(turtles: List<Turtle>) {
        val rvHistories = binding.rvHistories // Access RecyclerView from binding

        val listHeroAdapter = HistoryAdapter(requireContext(), turtles)
        rvHistories.adapter = listHeroAdapter

        listHeroAdapter.setOnItemClickCallback(object : HistoryAdapter.OnItemClickCallback {
            override fun onItemClicked(turtle: Turtle) {
//                val intent = Intent(this@HistoryFragment, EnsiklopediaDetail::class.java)
//                intent.putStringExtra("turtle_id", turtle_id)
//                startActivity(intent)
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryFragment()
    }
}
