package com.pc.readjet.presentation.main

import com.pc.readjet.data.model.Article

sealed class MainEvents {
    data class onArticleClicked(var article: Article): MainEvents()
    data class onCategoryChanged(var category: String): MainEvents()
    data class onSearchQueryChanged(var query: String): MainEvents()
}