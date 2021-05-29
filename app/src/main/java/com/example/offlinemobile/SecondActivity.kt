package com.example.offlinemobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_second2.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second2)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val story = intent.getStringExtra("story")

        web_view.webChromeClient = object: WebChromeClient(){}

        web_view.webViewClient = object: WebViewClient(){}


        val settings = web_view.settings
        settings.javaScriptEnabled = true

        web_view.loadUrl(story!!)

    }
}