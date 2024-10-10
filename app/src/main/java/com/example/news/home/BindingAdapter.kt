package com.example.news.home

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
@BindingAdapter("imageUrl")
fun loadImageFromURL(imageView:ImageView,url:String){
    Glide.with(imageView)
        .load(url)
        .into(imageView)
}