package com.example.news.ui.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.api.ApiManager
import com.example.news.home.Constants
import com.example.news.home.NewsAdapter
import com.example.news.model.Category
import com.example.news.model.SourcedResponse
import com.example.news.model.SourcesItem
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsFragment : Fragment() {
    companion object{
        fun getInstance(category: Category):NewsFragment{
            val fragment = NewsFragment()
            fragment.category = category
            return  fragment
        }
    }
    lateinit var category:Category

    private lateinit var tabLayout: TabLayout
    lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        getNewsSources()
    }
    private val adapter = NewsAdapter(null)
    private fun initViews(){
        tabLayout =requireView().findViewById(R.id.tab_layout)
        progressBar = requireView().findViewById(R.id.progress)
        recyclerView = requireView().findViewById(R.id.recyclerView)
        recyclerView.adapter=adapter
    }
    private fun getNewsSources(){
        ApiManager.getApis()
            .getSources(Constants.apiKey,category.id)
            .enqueue(object : Callback<SourcedResponse> {
                override fun onFailure(call: Call<SourcedResponse>,
                                       t: Throwable) {
                    t.localizedMessage?.let { Log.e("error", it) }


                }

                override fun onResponse(
                    call: Call<SourcedResponse>,
                    response: Response<SourcedResponse>
                ) {
                    progressBar.isVisible = false
                    addSourcesToTabLayout(response.body()?.sources)
                }

            })


    }
    fun addSourcesToTabLayout(sources: List<SourcesItem?>?){
        sources?.forEach {sources->
            val tab = tabLayout.newTab()
            tab.tag=sources
            tab.text = sources?.name
            tabLayout.addTab(tab)
        }
        tabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val source= tab?.tag as SourcesItem
                    getNewsBySource(source)

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val source= tab?.tag as SourcesItem
                    getNewsBySource(source)
                }

            }
        )
        tabLayout.getTabAt(0)?.select()
    }
    fun getNewsBySource(source: SourcesItem){
        progressBar.isVisible = true
        ApiManager.getApis()
            .getNews(Constants.apiKey,source.id?:"")
            .enqueue(object : Callback<com.example.news.model.Response> {
                override fun onResponse(
                    call: Call<com.example.news.model.Response>,
                    response: Response<com.example.news.model.Response>
                ) {
                    progressBar.isVisible=false
                    adapter.changeData(response.body()?.articles)


                }

                override fun onFailure(call: Call<com.example.news.model.Response>, t: Throwable) {
                    progressBar.isVisible=false


                }

            })


    }


}