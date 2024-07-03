package com.pc.readjet.data.network

import com.pc.readjet.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun topHeadlines(
        @Query("country") country: String = "in",
        @Query("apiKey") apiKey: String = "Enter_Your_Api Key"
    ): NewsResponse

    @GET("top-headlines")
    suspend fun topCategoryNews(
        @Query("country") country: String = "in",
        @Query("category") category: String,
        @Query("apiKey") apiKey: String = "Enter_Your_Api Key"
    ):NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String = "Enter_Your_Api Key"
    ):NewsResponse
}