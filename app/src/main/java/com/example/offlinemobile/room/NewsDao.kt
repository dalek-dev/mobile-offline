package com.example.offlinemobile.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(newList: MutableList<News>)

    @Query("SELECT * FROM news")
    fun getInfoNews():LiveData<MutableList<News>>

    @Update
    fun updateNew(vararg news: News)

    @Query("DELETE FROM news WHERE objectID = :id")
    fun deleteNew(vararg id: String)

    @Query("DELETE from news")
    fun deleteAllNews()


}