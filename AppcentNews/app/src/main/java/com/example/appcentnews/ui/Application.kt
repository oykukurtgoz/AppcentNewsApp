package com.example.appcentnews.ui

import android.app.Application
import android.content.Context
import com.example.appcentnews.utils.SharedPreferencesManager

class Application : Application(){

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesManager.initialize(applicationContext)
    }
}