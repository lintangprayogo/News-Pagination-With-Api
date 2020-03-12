package com.example.news.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.data.model.News
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_item_layout.view.*

class NewsHolder(view:View):RecyclerView.ViewHolder(view) {
     fun bindData(data:News?){
         Picasso.get().load(data?.image).into(itemView.banner)
         itemView.title.text=data?.title
     }

    companion object {
        fun create(parent: ViewGroup): NewsHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.news_item_layout, parent, false)
            return NewsHolder(view)
        }
    }
}