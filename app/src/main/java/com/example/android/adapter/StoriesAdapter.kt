package com.example.android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.R
import com.example.android.model.Stories

class StoriesAdapter(val context: Context, val arrayList: ArrayList<Stories>):RecyclerView.Adapter<StoriesAdapter.HomeViewHolder>() {
    class HomeViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val image=itemView.findViewById<ImageView>(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
       val layoutInflater=LayoutInflater.from(parent.context).inflate(R.layout.stories_view,parent,false)
        return HomeViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val i=arrayList[position]
        Glide.with(context)
            .load(i.image)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}