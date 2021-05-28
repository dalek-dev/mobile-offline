package com.example.offlinemobile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.offlinemobile.api.ApiResponseStatus
import com.example.offlinemobile.api.News
import com.example.offlinemobile.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newRecycler.layoutManager = LinearLayoutManager(this)

        val adapter = NewsAdapter()
        binding.newRecycler.adapter = adapter
        val viewModel = ViewModelProvider(this, MainViewModeFactory(application)).get(MainViewModel::class.java)
        viewModel.newsList.observe(this, Observer {
            newsList ->
            adapter.submitList(newsList)

            handleEmptyView(newsList, binding)
        })

        viewModel.status.observe(this, Observer {
            apiResponseStatus ->
            if(apiResponseStatus == ApiResponseStatus.LOADING){
                binding.loadingWheel.visibility = View.VISIBLE
            }else if(apiResponseStatus == ApiResponseStatus.DONE){
                binding.loadingWheel.visibility = View.GONE
            }else if(apiResponseStatus == ApiResponseStatus.ERROR){
                binding.loadingWheel.visibility = View.GONE
            }
        })

        adapter.onItemClickListener = {
            Toast.makeText(this,it.story_url, Toast.LENGTH_SHORT).show()
            val browserIntent = Intent("android.intent.ACTION_VIEW", Uri.parse(it.story_url))
            startActivity(browserIntent)
        }


    }

    private fun handleEmptyView(
        newsList: MutableList<News>,
        binding:  ActivityMainBinding
    ) {
        if (newsList.isEmpty()) {
            binding.newsEmptyView.visibility = View.VISIBLE
        } else {
            binding.newsEmptyView.visibility = View.GONE
        }
    }
}