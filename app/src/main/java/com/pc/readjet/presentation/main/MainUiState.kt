package com.pc.readjet.presentation.main

import com.pc.readjet.data.model.Article

data class MainUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val articles: List<Article> = emptyList(),
    val category: String = "",
    val searchQuery: String = "",
    val selectedArticle: Article? = null
)
