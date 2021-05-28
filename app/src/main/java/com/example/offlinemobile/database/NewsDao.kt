package com.example.offlinemobile.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.offlinemobile.api.News

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(newList: MutableList<News>)

    @Query("SELECT * FROM news")
    fun getInfoNews():LiveData<MutableList<News>>

    @Update
    fun updateNew(vararg news: News)

    @Delete
    fun deleteNew(vararg news: News)
}