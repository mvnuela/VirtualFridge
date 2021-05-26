package com.am.virtualfridge.fridge

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.am.virtualfridge.R
import kotlin.collections.ArrayList


/**
 * adapter do recyclerview w MyFridgeActivity
 * dla podstawowych produktow dodaje ich zdjecia
 */

class Adapter(private val productList:ArrayList<Product>, val context: Context): RecyclerView.Adapter<Adapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_row,parent,false)
        return ProductViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem=productList[position]
        holder.amountOfProduct.text= currentItem.amount.toString()
        holder.nameOfProduct.text=currentItem.name
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
        val nameOfProduct: TextView = itemView.findViewById(R.id.tvName)
        val amountOfProduct: TextView = itemView.findViewById(R.id.tvAmount)

    }
}