package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.ui.MainActivity
import com.example.newsapp.ui.viewmodel.NewsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ArticleFragment : Fragment(R.layout.fragment_article) {


    lateinit var viewModel:NewsViewModel
    lateinit var fab:FloatingActionButton
    val args:ArticleFragmentArgs by navArgs()
    lateinit var webView:WebView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        fab = view.findViewById(R.id.fab)

        webView = view.findViewById(R.id.webView)
        val article =args.article
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }
        fab.setOnClickListener {
            viewModel.saveArticle(article)
        }


    }
}