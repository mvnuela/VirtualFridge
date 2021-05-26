package com.am.virtualfridge

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList


class MyFridgeFragment : Fragment() {
    private lateinit var myRef: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var submit: Button
    private lateinit var listOfProducts:ArrayList<Product>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_my_fridge, container, false)

        val firebase: FirebaseDatabase = FirebaseDatabase.getInstance("https://virtualfridge-47aca-default-rtdb.europe-west1.firebasedatabase.app/")
        myRef = firebase.getReference("ArrayDate")
        recyclerView = view.findViewById(R.id.recyclerView)
        submit = view.findViewById(R.id.submitDate)
        recyclerView.layoutManager= GridLayoutManager(context, 1)
        submit.setOnClickListener{
            AddProductDialog(context!!).show()
        }


        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Firebase", "Small error")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listOfProducts = ArrayList()
                for (i in dataSnapshot.children) {
                    val newRow = i.getValue(Product::class.java)
                    listOfProducts.add(newRow!!)
                }
                setupAdapter(listOfProducts)
            }

        })



        return view
    }
    private fun setupAdapter(arrayData: ArrayList<Product>){
        recyclerView.adapter=Adapter(arrayData, context!!)
    }
    companion object {
        private val firebase: FirebaseDatabase = FirebaseDatabase.getInstance("https://virtualfridge-47aca-default-rtdb.europe-west1.firebasedatabase.app/")
        private var myRef = firebase.getReference("ArrayDate")
        fun addUpdateProduct(product: Product) {

            val query = myRef.orderByChild("name")
                    .equalTo(product.name)

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (i in dataSnapshot.children) {
                        val item = i.getValue(Product::class.java)
                        if (item != null) {
                            i.key?.let { myRef.child(it).setValue(Product(product.name, product.amount + item.amount)) }
                            return
                        }
                    }
                    myRef.child("${Date().time}").setValue(product)
                }

                override fun onCancelled(p0: DatabaseError) {
                    Log.e("Firebase", "Small error")
                }
            })
        }


        fun editProduct(product: Product) {
            val query = myRef.orderByChild("name")
                    .equalTo(product.name)

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (i in dataSnapshot.children) {
                        val item = i.getValue(Product::class.java)
                        if (item != null) {
                            i.key?.let { myRef.child(it).setValue(Product(product.name, product.amount)) }
                            return
                        }
                    }
                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("Firebase", "Small error")
                }
            })

        }

        fun deleteProduct(product: Product) {
            val query = myRef.orderByChild("name")
                    .equalTo(product.name)

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (i in dataSnapshot.children) {
                        val item = i.getValue(Product::class.java)
                        if (item != null) {
                            i.key?.let { myRef.child(it).removeValue() }
                            return
                        }
                    }
                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("Firebase", "Small error")
                }
            })
        }
    }
}