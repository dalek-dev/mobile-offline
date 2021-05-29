package com.example.offlinemobile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.offlinemobile.api.ApiResponseStatus
import com.example.offlinemobile.databinding.ActivityMainBinding
import com.example.offlinemobile.room.News
import com.example.offlinemobile.view.adapter.NewsAdapter
import com.example.offlinemobile.view.holder.MainViewModeFactory
import com.example.offlinemobile.view.holder.MainViewModel


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newRecycler.layoutManager = LinearLayoutManager(this)

        val handler = Handler()
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
            // Start another activity with position
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("story", it.story_url);
            startActivity(intent)
        }

        binding.swipeContainer.setOnRefreshListener {
            viewModel.deleteAllItems()
            val runnable = Runnable {
                viewModel.refreshNews()
                binding.swipeContainer.isRefreshing = false
            }
            handler.postDelayed(runnable, 3000.toLong())
        }

        binding.swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )


        binding.apply {
            ItemTouchHelper(object:ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val task = adapter.currentList[viewHolder.adapterPosition]
                    viewModel.deleteItem(task)
                }

            }).attachToRecyclerView(binding.newRecycler)
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