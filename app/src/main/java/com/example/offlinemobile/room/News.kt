package com.example.offlinemobile.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class News(@PrimaryKey val objectID: String, val created_at: String, val story_title: String, val author: String, val story_url: String)