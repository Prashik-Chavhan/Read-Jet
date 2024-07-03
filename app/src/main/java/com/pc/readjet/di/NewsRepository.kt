package com.pc.readjet.di

import com.pc.readjet.data.model.Article
import com.pc.readjet.domain.util.Resource

interface NewsRepository {

    suspend fun getTopHeadlines(): Resource<List<Article>>

    suspend fun getTopCategoryNews(
        category: String
    ): Resource<List<Article>>

    suspend fun getSearchNews(
        query: String
    ): Resource<List<Article>>
}