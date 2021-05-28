package com.example.offlinemobile.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.offlinemobile.api.News

@Database(entities = [News::class], version = 1)
abstract class NewsDatabase: RoomDatabase() {
    abstract val newsDao : NewsDao
}

private lateinit var INSTANCE:NewsDatabase

fun getDatabase(context: Context): NewsDatabase{
    synchronized(NewsDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE= Room.databaseBuilder(
                context.applicationContext,
                NewsDatabase::class.java,
                "news"
            ).build()
        }
        return INSTANCE
    }
}