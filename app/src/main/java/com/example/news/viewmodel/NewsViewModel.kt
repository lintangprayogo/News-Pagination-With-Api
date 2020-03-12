package com.example.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.news.data.datasource.NewsDataSource
import com.example.news.data.datasource.NewsDataSourceFactory
import com.example.news.data.model.News
import com.example.news.service.NetworkService
import com.example.news.service.State
import io.reactivex.disposables.CompositeDisposable

class NewsViewModel:ViewModel() {
    private  val  networkService:NetworkService= NetworkService.getService()
    var  newsList:LiveData<PagedList<News>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize=5;
    private val newsDataSourceFactory:NewsDataSourceFactory

    init {
            newsDataSourceFactory = NewsDataSourceFactory(compositeDisposable,networkService)
            val config = PagedList.Config.Builder()
                        .setPageSize(pageSize)
                        .setInitialLoadSizeHint(5*2)
                        .setEnablePlaceholders( false)
                        .build()
               newsList = LivePagedListBuilder(newsDataSourceFactory, config).build()

    }


    fun getState(): LiveData<State> = Transformations.switchMap(newsDataSourceFactory.newsDataSourceLiveData, NewsDataSource::state)

    fun retry() {
        newsDataSourceFactory.newsDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return newsList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}