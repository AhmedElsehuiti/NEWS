package com.example.news.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.databinding.ItemNewsBinding
import com.example.news.model.ArticlesItem
import org.w3c.dom.Text

class  NewsAdapter(var items:List<ArticlesItem?>?) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    fun changeData(data:List<ArticlesItem?>?){
        items = data
        notifyDataSetChanged()

    }
    class ViewHolder(val itemNewsBinding: ItemNewsBinding):RecyclerView.ViewHolder(itemNewsBinding.root){
        fun bind(item: ArticlesItem?){
            itemNewsBinding.item = item
            itemNewsBinding.invalidateAll()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val viewBinding :ItemNewsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_news,parent,false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
      return   items?.size ?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item =items?.get(position)
       holder.itemNewsBinding.item = item
        holder.itemNewsBinding.executePendingBindings()
        holder.bind(item)


    }
}