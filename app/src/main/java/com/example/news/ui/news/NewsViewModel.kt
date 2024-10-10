package com.example.news.ui.news

import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.api.ApiManager
import com.example.news.home.Constants
import com.example.news.model.ArticlesItem
import com.example.news.model.Category
import com.example.news.model.SourcedResponse
import com.example.news.model.SourcesItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {
    val sourceLiveData = MutableLiveData<List<SourcesItem?>?>()
    val newsLiveData = MutableLiveData<List<ArticlesItem?>?>()
    val progressVisible = MutableLiveData<Boolean>()
    val messageLiveData = MutableLiveData<String>()
     fun getNewsSources(category: Category){
         progressVisible.value = true
        ApiManager.getApis()
            .getSources(Constants.apiKey,category.id)
            .enqueue(object : Callback<SourcedResponse> {
                override fun onFailure(call: Call<SourcedResponse>,
                                       t: Throwable) {
                    progressVisible.value = false
                    messageLiveData.value = t.localizedMessage
               //     t.localizedMessage?.let { Log.e("error", it) }


                }

                override fun onResponse(
                    call: Call<SourcedResponse>,
                    response: Response<SourcedResponse>
                ) {
                    progressVisible.value = false
                   // addSourcesToTabLayout(response.body()?.sources)
                    sourceLiveData.value = response.body()?.sources
                }

            })


    }
    fun getNewsBySource(source: SourcesItem){
        progressVisible.value = true
        //progressBar.isVisible = true
        ApiManager.getApis()
            .getNews(Constants.apiKey,source.id?:"")
            .enqueue(object : Callback<com.example.news.model.Response> {
                override fun onResponse(
                    call: Call<com.example.news.model.Response>,
                    response: Response<com.example.news.model.Response>
                ) {
                    progressVisible.value=false
                    newsLiveData.value = response.body()?.articles
                    //  progressBar.isVisible=false
               //     adapter.changeData(response.body()?.articles)


                }

                override fun onFailure(call: Call<com.example.news.model.Response>, t: Throwable) {
               //     progressBar.isVisible=false


                }


            })


    }

}