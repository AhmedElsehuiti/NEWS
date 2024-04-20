package com.example.news.ui.categries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.model.Category


class CategoriesFragment : Fragment() {

   private val categoriesList = listOf(
        Category("sports",R.drawable.sports,
            R.string.sport,
            R.color.red),
        Category("technology",R.drawable.politics,
            R.string.politics,
            R.color.blue),
        Category("health",R.drawable.health,
            R.string.health,
            R.color.pink),
        Category("business",R.drawable.bussines,
            R.string.business,
            R.color.brown),
        Category("general",R.drawable.environment,
            R.string.general,
            R.color.light_blue),
        Category("science",R.drawable.science,
            R.string.science,
            R.color.yellow)
        )
    val adapter = CategoriesAdapter(categoriesList)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }
    lateinit var recyclerView :RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
        adapter.onItemClickListener= object :CategoriesAdapter.OnItemClickListener{
            override fun onItemClicked(position: Int, category: Category) {
                onCategoryClickListener?.onCategoryClick(category)
            }

        }
    }
    var onCategoryClickListener :OnCategoryClickListener?=null
    interface OnCategoryClickListener{
        fun onCategoryClick(category: Category)
    }

}