package com.example.appcentnews.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appcentnews.R
import com.example.appcentnews.core.data.model.ArticleItem
import com.example.appcentnews.databinding.ActivityDetailBinding
import com.example.appcentnews.utils.SharedPreferencesManager
import com.example.appcentnews.utils.loadImage
import com.google.gson.Gson

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var articlesItem: ArticleItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val itemJson = intent.getStringExtra("clickedItemJson")
        articlesItem = Gson().fromJson(itemJson, ArticleItem::class.java)
        setupViews()
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun checkLiked(): Boolean {
        return if (SharedPreferencesManager.isArticleSaved(articlesItem?.url)) {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorites)
            true
        } else {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border)
            false
        }
    }

    private fun setupViews() {
        checkLiked()
        binding.tvNewsAuthor.text = articlesItem?.author
        binding.tvNewsDescription.text = articlesItem?.description
        binding.tvDate.text = articlesItem?.publishedAt
        binding.tvNewsTitle.text = articlesItem?.title
        binding.ivNews.loadImage(articlesItem?.urlToImage)

        binding.btnSource.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(articlesItem?.url)
            startActivity(intent)
        }
        binding.btnFavorite.setOnClickListener {
            if (checkLiked()) {
                SharedPreferencesManager.removeArticle(articlesItem?.url)
            } else {
                SharedPreferencesManager.putArticle(articlesItem?.url, articlesItem)
            }
            checkLiked()
        }

        binding.btnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, articlesItem?.url)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, getString(R.string.share_news))
            startActivity(shareIntent)
        }
    }
}
