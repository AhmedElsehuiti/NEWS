package com.example.news.ui.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.api.ApiManager
import com.example.news.databinding.FragmentNewsBinding
import com.example.news.home.Constants
import com.example.news.home.NewsAdapter
import com.example.news.model.ArticlesItem
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
    lateinit var viewDataBinding:FragmentNewsBinding
    lateinit var category:Category
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
    }

     fun subscribeToLiveData() {
         viewModel.sourceLiveData.observe(viewLifecycleOwner
         ) {
             addSourcesToTabLayout(it)
         }
         viewModel.newsLiveData.observe(viewLifecycleOwner){
             showNews(it)
         }
         viewModel.progressVisible.observe(viewLifecycleOwner ){isVisible->
             viewDataBinding.progress.isVisible = isVisible
         }
         viewModel.messageLiveData.observe(viewLifecycleOwner){message->
             Toast.makeText(activity,message ,Toast.LENGTH_LONG).show()
         }

     }

    private fun showNews(newsList: List<ArticlesItem?>?) {
        adapter.changeData(newsList)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       viewDataBinding =  DataBindingUtil.inflate(inflater,R.layout.fragment_news, container, false)
        return viewDataBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        subscribeToLiveData()
        viewModel.getNewsSources(category)
    }
    private val adapter = NewsAdapter(null)
    private fun initViews(){
        viewDataBinding.recyclerView.adapter = adapter
    }

    fun addSourcesToTabLayout(sources: List<SourcesItem?>?){
        sources?.forEach {sources->
            val tab = viewDataBinding.tabLayout.newTab()
            tab.tag=sources
            tab.text = sources?.name
            viewDataBinding.tabLayout.addTab(tab)
        }
        viewDataBinding.tabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val source= tab?.tag as SourcesItem
                    viewModel.getNewsBySource(source)

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val source= tab?.tag as SourcesItem
                  viewModel.getNewsBySource(source)
                }

            }
        )
        viewDataBinding.tabLayout.getTabAt(0)?.select()
    }


}