package com.pc.readjet.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pc.readjet.data.model.Article
import com.pc.readjet.di.NewsRepository
import com.pc.readjet.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel() {

    var breakingNews by mutableStateOf<List<Article>>(emptyList())

    var categoryNews by mutableStateOf(MainUiState())

    var searchNews by mutableStateOf(MainUiState())

    var searchJob: Job? = null

    fun onEvent(event: MainEvents) {
        when(event) {
            is MainEvents.onCategoryChanged -> {
                categoryNews = categoryNews.copy(category = event.category)
                getCategoryNews(category = categoryNews.category)
            }
            is MainEvents.onSearchQueryChanged -> {
                searchNews = searchNews.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(600)
                    getSearchNews(query = searchNews.searchQuery)
                }
            }
            is MainEvents.onArticleClicked -> {
                categoryNews = categoryNews.copy(selectedArticle = event.article)
            }
        }
    }

    init {
        getBreakingNews()
    }

    fun getBreakingNews() {
        viewModelScope.launch {
            val result = newsRepository.getTopHeadlines()
            when(result) {
                is Resource.Success -> {
                    breakingNews = result.data ?: emptyList()
                }

                is Resource.Error -> {
                    breakingNews = emptyList()
                }
            }
        }
    }
    fun getCategoryNews(category: String) {
        viewModelScope.launch {
            categoryNews = categoryNews.copy(
                isLoading = true
            )
            val result = newsRepository.getTopCategoryNews(category)
            when(result) {
                is Resource.Success -> {
                    categoryNews = categoryNews.copy(
                        isLoading = false,
                        articles = result.data ?: emptyList(),
                        error = null
                    )
                }
                is Resource.Error -> {
                    categoryNews = categoryNews.copy(
                        isLoading = false,
                        articles = emptyList(),
                        error = result.message
                    )
                }
            }
        }
    }
    fun getSearchNews(query: String) {
        viewModelScope.launch {
            searchNews = searchNews.copy(
                isLoading = true
            )
            val result = newsRepository.getSearchNews(query)
            when(result) {
                is Resource.Success -> {
                    searchNews = searchNews.copy(
                        isLoading = false,
                        articles = result.data ?: emptyList(),
                        error = null
                    )
                }
                is Resource.Error -> {
                    searchNews = searchNews.copy(
                        isLoading = false,
                        articles = emptyList(),
                        error = result.message
                    )
                }
            }
        }
    }
}