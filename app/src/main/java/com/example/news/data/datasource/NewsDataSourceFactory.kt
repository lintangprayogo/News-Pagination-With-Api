package com.example.news.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.news.data.datasource.NewsDataSource
import com.example.news.data.model.News
import com.example.news.service.NetworkService
import io.reactivex.disposables.CompositeDisposable

class NewsDataSourceFactory (
    private val  compositeDisposable: CompositeDisposable,
    private  val networkService: NetworkService):DataSource.Factory<Int , News>(){
     val newsDataSourceLiveData = MutableLiveData<NewsDataSource>()

    override fun create(): DataSource<Int, News> {
          val newsDataSource = NewsDataSource(
              networkService,
              compositeDisposable
          )
          newsDataSourceLiveData.postValue(newsDataSource);
         return  newsDataSource
    }
}