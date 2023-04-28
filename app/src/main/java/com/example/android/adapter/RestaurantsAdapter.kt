package com.example.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R

class RestaurantsAdapter:RecyclerView.Adapter<RestaurantsAdapter.HomeViewHolder>() {
    class HomeViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
       val layoutInflater=LayoutInflater.from(parent.context).inflate(R.layout.restaurants,parent,false)
        return HomeViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 5
    }
}