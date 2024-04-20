package com.example.news

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.news.model.Category
import com.example.news.ui.categries.CategoriesFragment
import com.example.news.ui.SettingsFragment
import com.example.news.ui.news.NewsFragment


class HomeActivity : AppCompatActivity() {
   private lateinit var drawerLayout: DrawerLayout
  private lateinit var drawerButton : ImageView
  private lateinit var categories : View
  private  lateinit var settings : View
  private  val categoriesFragment = CategoriesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initViews()
        pushFragment(categoriesFragment)
        categoriesFragment.onCategoryClickListener = object :CategoriesFragment.OnCategoryClickListener{
            override fun onCategoryClick(category: Category) {
                pushFragment(NewsFragment.getInstance(category),true)
            }

        }

    }
   private fun initViews(){
        drawerLayout = findViewById(R.id.drawer_layout)
        drawerButton = findViewById(R.id.menu)
       categories = findViewById(R.id.categories)
       settings = findViewById(R.id.Settings)
        drawerButton.setOnClickListener{
            drawerLayout.open()
        }
       categories.setOnClickListener {
           pushFragment(categoriesFragment)
       }
       settings.setOnClickListener {
           pushFragment(SettingsFragment())
       }
    }
   private fun pushFragment(fragment: Fragment, addToBackStack:Boolean= false){
    val fragmentTransction =    supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,fragment)
       if (addToBackStack)
           fragmentTransction.addToBackStack("")
       fragmentTransction.commit()
       drawerLayout.close()


    }


}