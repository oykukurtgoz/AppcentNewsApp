package com.example.appcentnews.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcentnews.core.data.model.ArticleItem
import com.example.appcentnews.databinding.FragmentFavoritesBinding
import com.example.appcentnews.ui.adapter.NewsAdapter
import com.example.appcentnews.ui.adapter.RecyclerViewItemClickListener
import com.example.appcentnews.ui.detail.DetailActivity
import com.google.gson.Gson

class FavoritesFragment : Fragment() {
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var newsListRecyclerViewAdapter: NewsAdapter
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerAdapter()
        viewModel.getLocalNews()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLocalNews()
    }

    private fun observeViewModel() {
        viewModel.likedNewsList.observe(viewLifecycleOwner) { newsList ->
            newsListRecyclerViewAdapter.setNewsList(newsList)
        }
    }

    private val recyclerViewItemClickListener = object :
        RecyclerViewItemClickListener<ArticleItem> {
        override fun onClick(item: ArticleItem?) {
            item?.let {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra("clickedItemJson", Gson().toJson(it))
                startActivity(intent)
            }
        }
    }

    private fun initializeRecyclerAdapter() {
        newsListRecyclerViewAdapter = NewsAdapter(recyclerViewItemClickListener)
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsListRecyclerViewAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
