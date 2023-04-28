package com.example.android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.R
import com.example.android.model.Stories

class CategoryAdapter(val context: Context, val arrayList: ArrayList<Stories>):RecyclerView.Adapter<CategoryAdapter.HomeViewHolder>() {
    class HomeViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val image=itemView.findViewById<ImageView>(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
       val layoutInflater=LayoutInflater.from(parent.context).inflate(R.layout.category,parent,false)
        return HomeViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val i=arrayList[position]
        Glide.with(context)
            .load(i.image)
            .into(holder.image)
        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}