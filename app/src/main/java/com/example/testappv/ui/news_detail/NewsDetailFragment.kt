package com.example.testappv.ui.news_detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.example.testappv.data.db.getDatabase
import com.example.testappv.data.remote.ApiService
import com.example.testappv.data.repositories.NewsRepository
import com.example.testappv.databinding.FragmentNewsDetailBinding
import com.example.testappv.utils.Util

class NewsDetailFragment : Fragment() {

    private lateinit var viewModel: NewsDetailViewModel
    private lateinit var binding: FragmentNewsDetailBinding
    private var newsId: Long = 0
    private var newsTitle: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewsDetailBinding.inflate(inflater, container, false)

        newsId = NewsDetailFragmentArgs.fromBundle(arguments!!).newsId
        newsTitle = NewsDetailFragmentArgs.fromBundle(arguments!!).newsTitle

        val application = (activity as AppCompatActivity).application
        val database = getDatabase(application)
        val network = ApiService.retrofitApi
        val newsRepository = NewsRepository.getRepository(database, network, application)

        val viewModelFactory = NewsDetailViewModelFactory(newsRepository, newsId, newsTitle)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[NewsDetailViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = Util.htmlTextFrom(newsTitle)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
