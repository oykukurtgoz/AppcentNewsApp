package com.example.appcentnews.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcentnews.core.data.model.ArticleItem
import com.example.appcentnews.databinding.FragmentHomeBinding
import com.example.appcentnews.ui.adapter.NewsAdapter
import com.example.appcentnews.ui.adapter.RecyclerViewItemClickListener
import com.example.appcentnews.ui.detail.DetailActivity
import com.example.appcentnews.utils.makeToast
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var newsListRecyclerViewAdapter: NewsAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val searchDelay: Long = 1500
    private val handler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerAdapter()
        setEditText()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setEditText() {
        val searchEditText: TextInputEditText = binding.etSearch
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchRunnable?.let { handler.removeCallbacks(it) }
            }
            override fun afterTextChanged(s: Editable?) {
                searchRunnable = Runnable {
                    val query = s.toString()
                    if (query.isNotBlank()){
                        viewModel.callSearchQuery(query)
                    }
                }
                handler.postDelayed(searchRunnable!!, searchDelay)
            }
        })
    }

    private fun observeViewModel() {
        viewModel.newsArticles.observe(viewLifecycleOwner) { newsArticles ->
            newsListRecyclerViewAdapter.setNewsList(newsArticles)
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            checkResultIsEmpty(errorMessage)
        }
    }

    private fun checkResultIsEmpty(errorMessage:String?) {
        errorMessage?.let {
            context?.makeToast(it)
        }
    }

    private val recyclerViewItemClickListener = object : RecyclerViewItemClickListener<ArticleItem> {
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
}
