package com.example.offlinemobile

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.offlinemobile.api.News
import com.example.offlinemobile.databinding.NewsItemBinding

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context))
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {
        val new = getItem(position)
        holder.bind(new)
    }

    inner class NewsViewHolder(private val binding: NewsItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(news:News) {
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