package com.example.appcentnews.utils

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.appcentnews.R

fun ImageView.loadImage(urlString: String?) {
    urlString.let {
        Glide.with(this)
            .load(urlString)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(this)
    }
}

fun Context.makeToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}