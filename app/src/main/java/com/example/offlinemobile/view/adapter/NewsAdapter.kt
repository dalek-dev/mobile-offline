package com.example.offlinemobile.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.offlinemobile.room.News
import com.example.offlinemobile.databinding.NewsItemBinding
import com.example.offlinemobile.repository.MainRepository
import com.example.offlinemobile.view.holder.MainViewModel

class NewsAdapter:ListAdapter<News, NewsAdapter.NewsViewHolder>(DiffCallback) {

    companion object DiffCallback: DiffUtil.ItemCallback<News>(){
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.objectID == newItem.objectID
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }
    }

    lateinit var onItemClickListener:(News) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context))
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val new = getItem(position)
        holder.bind(new)
    }

    fun deleteItem(pos:Int){

    }

    inner class NewsViewHolder(private val binding: NewsItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(news: News) {
            binding.author.text = news.author
            binding.createdAt.text = news.created_at
            binding.storyTitle.text = news.story_title
            binding.root.setOnClickListener {
                if(::onItemClickListener.isInitialized){
                    onItemClickListener(news)
                }else{
                   Log.e("News", "onitemClickListener is not initialized")
                }
            }
        }
    }

}