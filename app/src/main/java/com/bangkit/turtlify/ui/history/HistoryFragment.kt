package com.bangkit.turtlify.ui.history

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.turtlify.data.database.TurtlifyDatabase
import com.bangkit.turtlify.data.database.entity.Turtle
import com.bangkit.turtlify.data.database.repository.TurtlifyRepository
import com.bangkit.turtlify.data.network.model.FetchTurtlesResponseItem
import com.bangkit.turtlify.databinding.FragmentHistoryBinding
import com.bangkit.turtlify.ui.encyclopediadetail.EncyclopediaDetailActivity
import com.bangkit.turtlify.ui.identifier.IdentifierViewModel
import com.bangkit.turtlify.ui.settings.SettingsActivity
import com.bangkit.turtlify.utils.ViewModelFactory

class HistoryFragment : Fragment() {
    private val viewModel by viewModels<HistoryViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
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
        binding.logoSettings.setOnClickListener{
            val intent = Intent(activity, SettingsActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun setupViewModel() {
        viewModel.getAllTurtles(
            onError = {errorMsg ->
                Toast.makeText(activity, errorMsg, Toast.LENGTH_LONG).show()
            }
        )
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
