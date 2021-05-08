package com.am.virtualfridge

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val productList:ArrayList<Product>): RecyclerView.Adapter<Adapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_row,parent,false)
        return ProductViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem=productList[position]
       // holder.imageView.setImageResource(currentItem.imgRes)
        holder.amountfProduct.text= currentItem.amount.toString()
        holder.nameofProduct.text=currentItem.name
    }
    override fun getItemCount()=productList.size


    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        //val imageView: ImageView = itemView.findViewById(R.id.image_view)
        val nameofProduct: TextView = itemView.findViewById(R.id.name)
        val amountfProduct: TextView = itemView.findViewById(R.id.amount)

    }
}