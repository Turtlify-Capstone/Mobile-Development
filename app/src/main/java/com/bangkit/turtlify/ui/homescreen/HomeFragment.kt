package com.bangkit.turtlify.ui.homescreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.turtlify.R
import com.bangkit.turtlify.data.model.news.NewsData
import com.bangkit.turtlify.data.network.model.FetchTurtlesResponseItem
import com.bangkit.turtlify.databinding.FragmentHomeBinding
import com.bangkit.turtlify.ui.encyclopedia.EncyclopediaActivity
import com.bangkit.turtlify.ui.encyclopediadetail.EncyclopediaDetailActivity
import com.bangkit.turtlify.ui.instruction.InstructionActivity
import com.bangkit.turtlify.ui.news.NewsDetailActivity
import com.bangkit.turtlify.ui.settings.SettingsActivity
import com.bangkit.turtlify.utils.checkProtection
import com.bumptech.glide.Glide

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
        addNewsCard()

        viewModel.getTurtlesEncyclopedia(
            onError = {errorMsg ->
                Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
            }
        )
        viewModel.encyclopedia.observe(viewLifecycleOwner){turtles ->
            populateEncyclopediaCard(turtles)
        }
    }

    private fun setupAction() {
        binding.logoSettings.setOnClickListener {
            val intent = Intent(activity, SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.actionLearnBtn.setOnClickListener {
            val intent = Intent(activity, InstructionActivity::class.java)
            startActivity(intent)
        }

        binding.tvEncyclopediaTitle.setOnClickListener {
            val intent = Intent(activity, EncyclopediaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setEncyclopediaCardValues(
        turtle: FetchTurtlesResponseItem,
        nameTextView: TextView,
        statusTextView: TextView,
        imageView: ImageView,
        cardView: View
    ) {
        nameTextView.text = turtle.namaLokal!!.split(", ").first()
        statusTextView.text = turtle.statusKonversi?.let { checkProtection(it) }
        statusTextView.setTextColor(resources.getColor(
            if (checkProtection(turtle.statusKonversi!!) == "dilindungi") R.color.red_text else R.color.green_text
        ))
        Glide.with(requireContext())
            .load(turtle.image!!.split(",").first())
            .fitCenter()
            .into(imageView)
        cardView.setOnClickListener {
            val intent = Intent(requireContext(), EncyclopediaDetailActivity::class.java)
            intent.putExtra("turtleData", turtle)
            startActivity(intent)
        }
    }

    private fun populateEncyclopediaCard(turtles: List<FetchTurtlesResponseItem>) {
        with(binding) {
            setEncyclopediaCardValues(turtles[0], encyclopediaName1, encyclopediaStatus1, encyclopediaImage1, encyclopediaCard1)
            setEncyclopediaCardValues(turtles[1], encyclopediaName2, encyclopediaStatus2, encyclopediaImage2, encyclopediaCard2)
        }
    }

    private fun addNewsCard() {
        val cardView1 =  binding.cardNews
        val imageView1 = cardView1.findViewById<ImageView>(R.id.newsImage)
        val titleTextView1 = cardView1.findViewById<TextView>(R.id.newsTitle)
        val sourceTextView1 = cardView1.findViewById<TextView>(R.id.newsSource)

        val news1 = NewsData.newsList[0] // Ambil data berita pertama dari objek NewsData

        Glide.with(this)
            .load(news1.imageUrl)
            .into(imageView1)

        titleTextView1.text = news1.title
        sourceTextView1.text = news1.source

        cardView1.setOnClickListener {
            val intent = Intent(activity, NewsDetailActivity::class.java)
            intent.putExtra("NEWS_URL", news1.url)
            startActivity(intent)
        }

        val cardView2 = binding.cardNewsId2
        val imageView2 = cardView2.findViewById<ImageView>(R.id.newsImage2)
        val titleTextView2 = cardView2.findViewById<TextView>(R.id.newsTitle2)
        val sourceTextView2 = cardView2.findViewById<TextView>(R.id.newsSource2)

        val news2 = NewsData.newsList[1] // Ambil data berita pertama dari objek NewsData

        Glide.with(this)
            .load(news2.imageUrl)
            .into(imageView2)

        titleTextView2.text = news2.title
        sourceTextView2.text = news2.source

        cardView2.setOnClickListener {
            val intent = Intent(activity, NewsDetailActivity::class.java)
            intent.putExtra("NEWS_URL", news2.url)
            startActivity(intent)
        }

        val cardView3 = binding.cardNewsId3
        val imageView3 = cardView3.findViewById<ImageView>(R.id.newsImage3)
        val titleTextView3 = cardView3.findViewById<TextView>(R.id.newsTitle3)
        val sourceTextView3 = cardView3.findViewById<TextView>(R.id.newsSource3)

        val news3 = NewsData.newsList[2] // Ambil data berita pertama dari objek NewsData

        Glide.with(this)
            .load(news3.imageUrl)
            .into(imageView3)

        titleTextView3.text = news3.title
        sourceTextView3.text = news3.source

        cardView3.setOnClickListener {
            val intent = Intent(activity, NewsDetailActivity::class.java)
            intent.putExtra("NEWS_URL", news3.url)
            startActivity(intent)
        }

        val cardView4 = binding.cardNewsId4
        val imageView4 = cardView4.findViewById<ImageView>(R.id.newsImage4)
        val titleTextView4 = cardView4.findViewById<TextView>(R.id.newsTitle4)
        val sourceTextView4 = cardView4.findViewById<TextView>(R.id.newsSource4)

        val news4 = NewsData.newsList[3] // Ambil data berita pertama dari objek NewsData

        Glide.with(this)
            .load(news4.imageUrl)
            .into(imageView4)

        titleTextView4.text = news4.title
        sourceTextView4.text = news4.source

        cardView4.setOnClickListener {
            val intent = Intent(activity, NewsDetailActivity::class.java)
            intent.putExtra("NEWS_URL", news4.url)
            startActivity(intent)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}