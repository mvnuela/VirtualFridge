package com.am.virtualfridge

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.virtualfridge.fridge.AdapterFridge
import com.am.virtualfridge.fridge.Product
import com.am.virtualfridge.receipts.Receipt


class ReceiptsFragment : Fragment() {
    lateinit var recycler: RecyclerView
    private lateinit var listOfReceipts:ArrayList<Receipt>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_receipts, container, false)
        recycler = view.findViewById<RecyclerView>(R.id.recyclerViewReceipts)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        return view
    }

    private fun setupAdapter(arrayData: ArrayList<Receipt>){
        try {
            recycler.adapter = FavReceiptsAdapter(arrayData, context!!)
        } catch (e: Exception) {
            Log.d("Adapter", "Error")
        }

    }
}