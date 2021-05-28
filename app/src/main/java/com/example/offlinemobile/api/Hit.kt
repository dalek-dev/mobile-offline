package com.example.offlinemobile.api

import com.squareup.moshi.Json

class Hit(
    val created_at: String,
    val story_title: String,
    val author: String,
    val objectID: String,
    val story_url: String
)