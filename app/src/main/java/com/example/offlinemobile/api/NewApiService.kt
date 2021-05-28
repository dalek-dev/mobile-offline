package com.example.offlinemobile.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


interface NewApiService {
    @GET("search_by_date?query=mobile")
    suspend fun getNews(): NewJsonResponse
}

private var retrofit = Retrofit.Builder()
    .baseUrl("https://hn.algolia.com/api/v1/")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

var service: NewApiService = retrofit.create(NewApiService::class.java)