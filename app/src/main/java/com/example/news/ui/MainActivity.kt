package com.example.news.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.R
import com.example.news.service.State
import com.example.news.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
  private  lateinit var  newsViewModel: NewsViewModel
  private lateinit var  newsListAdapter: NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newsViewModel =  ViewModelProviders.of(this).get(NewsViewModel::class.java)
        setUpAdapter()
        setUpState()
    }
    private  fun setUpAdapter(){
        newsListAdapter = NewsListAdapter {
            newsViewModel.retry()
        }
        recycler_view.layoutManager =LinearLayoutManager(this)
        recycler_view.adapter=newsListAdapter
        newsViewModel.newsList.observe(this, Observer {
              newsListAdapter.submitList(it)
        })


    }

    private fun setUpState() {
        txt_error.setOnClickListener { newsViewModel.retry() }
        newsViewModel.getState().observe(this, Observer { state ->
            progress_bar.visibility = if (newsViewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (newsViewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (!newsViewModel.listIsEmpty()) {
                newsListAdapter.setState(state ?: State.DONE)
            }
        })
    }
}
