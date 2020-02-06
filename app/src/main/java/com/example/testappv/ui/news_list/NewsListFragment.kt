package com.example.testappv.ui.news_list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.testappv.R
import com.example.testappv.adapters.news_list_adapter.NewsListAdapter
import com.example.testappv.data.db.getDatabase
import com.example.testappv.data.remote.ApiService
import com.example.testappv.data.repositories.NewsRepository
import com.example.testappv.databinding.FragmentNewsListBinding
import com.example.testappv.listeners.NewsClickListener
import com.google.android.material.snackbar.Snackbar

class NewsListFragment : Fragment() {

    private lateinit var viewModel: NewsListViewModel
    private lateinit var binding: FragmentNewsListBinding
    private lateinit var adapter: NewsListAdapter

    companion object {
        const val ALL_NEWS = 0
        const val POLITICS_NEWS = 1
        const val SPORT_NEWS = 2
        const val ECONOMICS_NEWS = 3
        const val INCIDENTS_NEWS = 4
        const val IN_THE_WORLD_NEWS = 5
        const val SOCIETY_NEWS = 6
        const val HI_TECH_NEWS = 7
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsListBinding.inflate(inflater, container, false)

        val application = (activity as AppCompatActivity).application
        val database = getDatabase(application)
        val network = ApiService.retrofitApi
        val newsRepository = NewsRepository.getRepository(database, network, application)

        val viewModelFactory = NewsListViewModelFactory(repository = newsRepository)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[NewsListViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.newsList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.isRefreshing.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.swipeRefreshLayout.isRefreshing = it
            }
        })

        viewModel.isInternetConnected.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (!it)
                    Snackbar.make(
                        (activity as AppCompatActivity).findViewById(android.R.id.content),
                        getString(R.string.no_internet_connection),
                        Snackbar.LENGTH_LONG
                    ).show()
            }
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.updateNews()
        }

        setupRecycler()
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setupRecycler() {

        binding.newsRecycler.layoutManager = LinearLayoutManager(context)
        binding.newsRecycler.setHasFixedSize(true)
        adapter = NewsListAdapter(listener = NewsClickListener { newsId, newsTitle ->
            newsTitle?.let {
                findNavController().navigate(
                    NewsListFragmentDirections.actionNewsListFragmentToNewsDetailFragment(
                        newsId,
                        newsTitle
                    )
                )
            }
        })
        binding.newsRecycler.adapter = adapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.news)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.news_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_filter_category) {
            showFilterPopupMenu()
            return true
        }

        return false
    }

    private fun showFilterPopupMenu() {
        val view = activity?.findViewById<View>(R.id.menu_filter_category) ?: return
        val popupMenu = PopupMenu(context!!, view)

        popupMenu.run {
            inflate(R.menu.menu_categories)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.all -> {
                        viewModel.categoryMenuChange(ALL_NEWS)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.politics -> {
                        viewModel.categoryMenuChange(POLITICS_NEWS)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.sport -> {
                        viewModel.categoryMenuChange(SPORT_NEWS)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.economics -> {
                        viewModel.categoryMenuChange(ECONOMICS_NEWS)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.incidents -> {
                        viewModel.categoryMenuChange(INCIDENTS_NEWS)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.in_the_world -> {
                        viewModel.categoryMenuChange(IN_THE_WORLD_NEWS)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.society -> {
                        viewModel.categoryMenuChange(SOCIETY_NEWS)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.hi_tech -> {
                        viewModel.categoryMenuChange(HI_TECH_NEWS)
                        return@setOnMenuItemClickListener true
                    }
                }
                return@setOnMenuItemClickListener false
            }
            show()
        }
    }
}
