package com.am.virtualfridge

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.am.virtualfridge.fridge.AdapterFridge

class FavReceiptsAdapter : RecyclerView.Adapter<FavReceiptsAdapter.ViewHolder> (){

   class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.receipt_row,parent,false)
        return FavReceiptsAdapter.ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}