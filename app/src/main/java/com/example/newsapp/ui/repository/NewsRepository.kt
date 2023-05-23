package com.example.newsapp.ui.repository

import com.example.newsapp.ui.api.RetrofitInstance
import com.example.newsapp.ui.db.ArticleDatabse
import com.example.newsapp.ui.models.Article
import com.example.newsapp.ui.models.News
import retrofit2.Response

class NewsRepository(
    val db:ArticleDatabse
) {

    suspend fun getBreakingNews(countryCode:String,pageNumber:Int) : Response<News> {
        return RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)
        
    }

    suspend fun searchNews(searchQuery: String,pageNumber:Int): Response<News> {
        return RetrofitInstance.api.searchForNews(searchQuery,pageNumber)
    }

    suspend fun upsert(article: Article){
        db.getArticleDao().upsert(article)
    }

    fun getSavedNews() =  db.getArticleDao().getAllArticles()



    suspend fun deleteArticle(article:Article){
        db.getArticleDao().deleteArticle(article)
    }

}
