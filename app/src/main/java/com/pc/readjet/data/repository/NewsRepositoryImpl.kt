package com.pc.readjet.data.repository

import com.pc.readjet.data.model.Article
import com.pc.readjet.data.network.NewsApi
import com.pc.readjet.di.NewsRepository
import com.pc.readjet.domain.util.Resource

class NewsRepositoryImpl(
    private val newsApi: NewsApi
): NewsRepository {
    override suspend fun getTopHeadlines(): Resource<List<Article>> {
        return try {
            val response = newsApi.topHeadlines()
            Resource.Success(
                data = response.articles
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getTopCategoryNews(category: String): Resource<List<Article>> {
        return try {
            val response = newsApi.topCategoryNews(category = category)
            Resource.Success(
                data = response.articles
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getSearchNews(query: String): Resource<List<Article>> {
        return try {
            val response = newsApi.searchNews(query)
            Resource.Success(
                data = response.articles
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}