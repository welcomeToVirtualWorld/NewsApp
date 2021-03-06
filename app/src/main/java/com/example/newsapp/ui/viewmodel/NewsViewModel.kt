package com.example.newsapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.ui.models.Article
import com.example.newsapp.ui.models.News
import com.example.newsapp.ui.repository.NewsRepository
import com.example.newsapp.ui.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<News>> = MutableLiveData()
    var breakingNewsPage = 1

    val searchNews : MutableLiveData<Resource<News>> = MutableLiveData()
    var searchNewsPage = 1


    init {
        getBreakingNews("IN")
    }


    fun getBreakingNews(countryCode:String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response : Response<News> = newsRepository.getBreakingNews(countryCode,breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun searchNews(searchQuery:String) = viewModelScope.launch{
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery,searchNewsPage)
        searchNews.postValue(handleSearchResponse(response))
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    fun getSavedNews()=newsRepository.getSavedNews()

    private fun handleBreakingNewsResponse(response: Response<News>) : Resource<News>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handleSearchResponse(response: Response<News>) : Resource<News>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}
