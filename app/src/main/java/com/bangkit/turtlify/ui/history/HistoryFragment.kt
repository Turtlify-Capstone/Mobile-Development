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
import com.bangkit.turtlify.data.database.TurtlifyDatabase
import com.bangkit.turtlify.data.database.entity.Turtle
import com.bangkit.turtlify.data.database.repository.TurtlifyRepository
import com.bangkit.turtlify.data.network.model.FetchTurtlesResponseItem
import com.bangkit.turtlify.databinding.FragmentHistoryBinding
import com.bangkit.turtlify.ui.encyclopediadetail.EncyclopediaDetailActivity

class HistoryFragment : Fragment() {
    private lateinit var viewModel: HistoryViewModel
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val view = binding.root

        setupViewModel()
        setupRecyclerView()

        return view
    }

    private fun setupViewModel() {
        val turtlifyDao = TurtlifyDatabase.getDatabase(requireContext()).turtlifyDao()
        val repository = TurtlifyRepository(turtlifyDao)
        viewModel = ViewModelProvider(this, HistoryViewModelFactory(repository))[HistoryViewModel::class.java]

        viewModel.getAllTurtles()
        viewModel.histories.observe(viewLifecycleOwner) { turtles ->
            showTurtlesList(turtles)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
    }

    private fun setupRecyclerView() {
        val rvHistories = binding.rvHistories
        rvHistories.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun showTurtlesList(turtles: List<Turtle>) {
        val rvHistories = binding.rvHistories
        val listHeroAdapter = HistoryAdapter(requireContext(), turtles)

        rvHistories.adapter = listHeroAdapter

        listHeroAdapter.setOnItemClickCallback(object : HistoryAdapter.OnItemClickCallback {
            override fun onItemClicked(turtle: Turtle) {
                val intent = Intent(requireContext(), EncyclopediaDetailActivity::class.java)
                intent.putExtra("turtleData", FetchTurtlesResponseItem(
                    turtle.namaLokal,
                    turtle.persebaranHabitat,
                    turtle.image,
                    turtle.habitat,
                    turtle.namaLatin,
                    turtle.description,
                    turtle.latitude,
                    turtle.id,
                    turtle.longitude,
                    turtle.statusKonversi
                ))
                startActivity(intent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressCircular.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryFragment()
    }
}
