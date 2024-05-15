package com.example.appcentnews.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appcentnews.core.data.model.ArticleItem
import com.example.appcentnews.databinding.ItemNewsBinding
import com.example.appcentnews.utils.loadImage

class NewsAdapter(
    private val recyclerViewItemClickListener: RecyclerViewItemClickListener<ArticleItem>?
) : RecyclerView.Adapter<NewsViewHolder>() {

    private var newsList: List<ArticleItem>? = null

    fun setNewsList(newsList: List<ArticleItem>?) {
        this.newsList = newsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding, recyclerViewItemClickListener)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = newsList?.getOrNull(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return newsList?.size ?: 0
    }
}

class NewsViewHolder(
    private val binding: ItemNewsBinding,
    private val recyclerViewItemClickListener: RecyclerViewItemClickListener<ArticleItem>?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ArticleItem?) {
        binding.tvNewsTitle.text = item?.title
        binding.tvNewsDescription.text = item?.description
        binding.ivNews.loadImage(item?.urlToImage)
        binding.root.setOnClickListener {
            recyclerViewItemClickListener?.onClick(item)
        }
    }
}

interface RecyclerViewItemClickListener<T> {
    fun onClick(item: T?)
}
