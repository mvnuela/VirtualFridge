package com.am.virtualfridge.receipts

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.am.virtualfridge.R
import com.am.virtualfridge.db.FirebaseReceipts

class FavReceiptsAdapter (private val receiptList:ArrayList<Receipt>, val context: Context) : RecyclerView.Adapter<FavReceiptsAdapter.ViewHolder> (){

   class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        var rootView: View = itemView
        val nameOfReceipt: TextView = itemView.findViewById(R.id.receiptName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.receipt_row,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return receiptList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem=receiptList[position]
        holder.nameOfReceipt.text=currentItem.name
        holder.rootView.findViewById<TextView>(R.id.receiptName).setOnClickListener {
            val intent = Intent(context, WebReceiptActivity::class.java)
            intent.putExtra("Url",currentItem.link)
            context.startActivity(intent)
        }
        holder.rootView.findViewById<ImageView>(R.id.rm).setOnClickListener{
            FirebaseReceipts.deleteReceipt(currentItem)
        }

    }
}