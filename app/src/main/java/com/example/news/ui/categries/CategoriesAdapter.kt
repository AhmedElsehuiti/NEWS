package com.example.news.ui.categries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.model.Category
import com.google.android.material.card.MaterialCardView

class CategoriesAdapter(val categoriesList:List<Category>) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title:TextView = itemView.findViewById(R.id.title_sport)
        val image:ImageView = itemView.findViewById(R.id.image_sport)
        val materialCard:MaterialCardView = itemView.findViewById(R.id.material_card)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate( if (viewType==LIFT_SIDED_VIEW_TYPE)
                R.layout.category_ship
                 else R.layout.category_ship_right,parent,false)
        return ViewHolder(view)
    }
    val LIFT_SIDED_VIEW_TYPE = 10
    val RIGHT_SIDED_VIEW_TYPE = 20
    var onItemClickListener : OnItemClickListener? = null
    interface OnItemClickListener{
        fun onItemClicked(position: Int , category:Category)
    }

    override fun getItemViewType(position: Int): Int {
        return   if (position%2==0) LIFT_SIDED_VIEW_TYPE else RIGHT_SIDED_VIEW_TYPE
    }
    override fun getItemCount(): Int = categoriesList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = categoriesList[position]
        holder.title.setText(item.titleId)
        holder.image.setImageResource(item.imageResId)
        holder.materialCard.setCardBackgroundColor(holder.itemView.context.getColor(item.backgroundColorId))
        onItemClickListener?.let {
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClicked(position, item)
            }
        }
    }
}