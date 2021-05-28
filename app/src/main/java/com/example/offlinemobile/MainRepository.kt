package com.example.offlinemobile

import androidx.lifecycle.LiveData
import com.example.offlinemobile.api.NewJsonResponse
import com.example.offlinemobile.api.News
import com.example.offlinemobile.api.service
import com.example.offlinemobile.database.NewsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val database:NewsDatabase) {

    val newsList: LiveData<MutableList<News>> = database.newsDao.getInfoNews()

    suspend fun fetchNews(){
        return withContext(Dispatchers.IO){
            val newsJsonResponse = service.getNews()
            val newsList = parseEqResult(newsJsonResponse)

            database.newsDao.insertAll(newsList)

        }
    }


    private fun parseEqResult(newsJsonResponse: NewJsonResponse):MutableList<News> {
        val newsList = mutableListOf<News>()
        val hitList = newsJsonResponse.hits
        val currentTimestamp = System.currentTimeMillis()

        for (hit in hitList){
            val objectID = hit.objectID
            val created_at = hit.created_at
            val author = hit.author
            val story_title = hit.story_title
            val story_url = hit.story_url

            newsList.add(News(objectID,created_at,story_title,author, story_url))
        }

        return newsList
    }

}