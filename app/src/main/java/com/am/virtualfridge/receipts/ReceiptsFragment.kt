package com.am.virtualfridge.receipts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.virtualfridge.R
import com.am.virtualfridge.db.FirebaseReceipts
import com.am.virtualfridge.db.FirebaseReceipts.Companion.myRef
import com.am.virtualfridge.db.FirebaseReceipts.Companion.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class ReceiptsFragment : Fragment() {
    lateinit var recycler: RecyclerView
    private lateinit var listOfReceipts:ArrayList<Receipt>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_receipts, container, false)
        user = FirebaseAuth.getInstance().currentUser!!.uid
        myRef = FirebaseReceipts.firebase.getReference("receipts").child(user)
        recycler = view.findViewById<RecyclerView>(R.id.recyclerViewReceipts)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Firebase", "Small error")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listOfReceipts = ArrayList()
                for (i in dataSnapshot.children) {
                    val newRow = i.getValue(Receipt::class.java)
                    listOfReceipts.add(newRow!!)
                }
                setupAdapter(listOfReceipts)
            }

        })

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