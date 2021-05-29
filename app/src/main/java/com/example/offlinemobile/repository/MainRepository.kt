package com.example.offlinemobile.repository

import androidx.lifecycle.LiveData
import com.example.offlinemobile.model.NewJsonResponse
import com.example.offlinemobile.room.News
import com.example.offlinemobile.api.service
import com.example.offlinemobile.room.NewsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val database: NewsDatabase) {

    val newsList: LiveData<MutableList<News>> = database.newsDao.getInfoNews()

    suspend fun fetchNews(){
        return withContext(Dispatchers.IO){
            val newsJsonResponse = service.getNews()
            val newsList = parseEqResult(newsJsonResponse)

            database.newsDao.insertAll(newsList)

        }
    }

    suspend fun deleteAll(){
        return withContext(Dispatchers.IO){
            database.newsDao.deleteAllNews()
        }
    }

    suspend fun deleteItemNew(news: News){
        return withContext(Dispatchers.IO){
            database.newsDao.deleteNew(news.objectID)
        }
    }

    private fun parseEqResult(newsJsonResponse: NewJsonResponse):MutableList<News> {
        val newsList = mutableListOf<News>()
        val hitList = newsJsonResponse.hits

        for (hit in hitList){
            val objectID = hit.objectID
            val created_at = hit.created_at
            val author = hit.author
            val story_title = if (hit.story_title == null) "" else hit.story_title
            val story_url = if (hit.story_url == null) "" else hit.story_url

            newsList.add(News(objectID,created_at,story_title,author, story_url))
        }

        return newsList
    }

}