package com.am.virtualfridge

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var myRef: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var submit: Button
    private lateinit var listOfProducts:ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val firebase: FirebaseDatabase = FirebaseDatabase.getInstance("https://virtualfridge-47aca-default-rtdb.europe-west1.firebasedatabase.app/")
        myRef = firebase.getReference("ArrayDate")
        recyclerView = findViewById(R.id.recyclerView)
        submit = findViewById(R.id.submitDate)
        recyclerView.layoutManager= GridLayoutManager(applicationContext, 1)
        submit.setOnClickListener{
            AddProductDialog(this).show()
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



    }

    private fun setupAdapter(arrayData: ArrayList<Product>){
        recyclerView.adapter=Adapter(arrayData, this)
    }


    //cos jak static, smialo mogloby to byc w klasie firebase
    companion object {
        private val firebase: FirebaseDatabase = FirebaseDatabase.getInstance("https://virtualfridge-47aca-default-rtdb.europe-west1.firebasedatabase.app/")
        private var myRef = firebase.getReference("ArrayDate")
        fun addUpdateProduct(product: Product) {

            val query = myRef.orderByChild("name")
                .equalTo(product.name)

            query.addListenerForSingleValueEvent(object : ValueEventListener{
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

            query.addListenerForSingleValueEvent(object : ValueEventListener{
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

            query.addListenerForSingleValueEvent(object : ValueEventListener{
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
