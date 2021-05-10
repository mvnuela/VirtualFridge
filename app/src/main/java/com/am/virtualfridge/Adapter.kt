package com.am.virtualfridge

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList


class Adapter(private val productList:ArrayList<Product>, val context: Context): RecyclerView.Adapter<Adapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_row,parent,false)
        return ProductViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem=productList[position]
        holder.amountfProduct.text= currentItem.amount.toString()
        holder.nameofProduct.text=currentItem.name
        //podstawowe produkty
        when(currentItem.name) {
            "marchewka" -> holder.imageView.setImageResource(R.drawable.carrot)
            "mleko" -> holder.imageView.setImageResource(R.drawable.milk)
            "jajko" -> holder.imageView.setImageResource(R.drawable.eggs)
            "jajka" -> holder.imageView.setImageResource(R.drawable.eggs)
            "jabłko" -> holder.imageView.setImageResource(R.drawable.apple)
            "jablko" -> holder.imageView.setImageResource(R.drawable.apple)
            "ser" -> holder.imageView.setImageResource(R.drawable.cheese)
            "banany" -> holder.imageView.setImageResource(R.drawable.banana)
            "ogorek" -> holder.imageView.setImageResource(R.drawable.cucumber)
            "ogórek" -> holder.imageView.setImageResource(R.drawable.cucumber)
            else -> holder.imageView.setImageResource(R.drawable.other)
        }

        holder.itemView.setOnClickListener {
            EditProductDialog(context, currentItem.name, currentItem.amount).show()
        }
    }
    override fun getItemCount()=productList.size


    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val imageView: ImageView = itemView.findViewById(R.id.ivProduct)
        val nameofProduct: TextView = itemView.findViewById(R.id.tvName)
        val amountfProduct: TextView = itemView.findViewById(R.id.tvAmount)

    }
}