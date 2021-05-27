package com.am.virtualfridge.fridge

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.virtualfridge.R
import com.google.firebase.auth.FirebaseAuth
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
        val user = FirebaseAuth.getInstance().currentUser!!.uid
        myRef = firebase.getReference("ArrayDate").child(user)
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
        try {
            recyclerView.adapter = Adapter(arrayData, context!!)
        } catch (e: Exception) {
            Log.d("Adapter", "Error")
        }

    }
    /**
     * wykorzysutje metode companion object, ktora dziala podobnie jak static w incie, zeby miec łatwy dostep do firebasu z innych klas
     * kazdy uzytkownik posiada inna lodowke
     */
    companion object {
        private val firebase: FirebaseDatabase = FirebaseDatabase.getInstance("https://virtualfridge-47aca-default-rtdb.europe-west1.firebasedatabase.app/")
        private val user = FirebaseAuth.getInstance().currentUser!!.uid
        private var myRef = firebase.getReference("ArrayDate").child(user)
        fun addUpdateProduct(product: Product) {
            /**
             * sprawdzam czy produkt o pdanej nazwie i dacie waznosci znajduje sie w fireabasie
             */
            val query = myRef.orderByChild("name").equalTo(product.name)
            /**
             * jezeli dany produkt sie pojawia, to zwiekszam jego illosc w bazie danych w product.amount + item.amount
             * a jezeli nie to dadaje go do bazy danych
             */
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (i in dataSnapshot.children) {
                        val item = i.getValue(Product::class.java)
                        if (item != null) {
                            //robie, zeby produkty o tym samych parametrach sie zsumowaly, niestety w zjeba... realtime firebasie byl z tym problem i nie mozna bylo uzyc w kwerendzie, firestore > realtime
                            if (item.dayOfMonth == product.dayOfMonth && item.month == product.month && item.year == product.year) {
                                i.key?.let { myRef.child(it).setValue(Product(product.name, product.amount + item.amount, product.dayOfMonth, product.month, product.year)) }
                            } else {
                                myRef.child("${Date().time}").setValue(product)
                            }
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

        /**
         * mozliwosc zmiany parametrow produkty, jak ilosci czy daty waznosci
         */
        fun editProduct(newProduct: Product, oldProduct: Product) {
            val query = myRef.orderByChild("name")
                .equalTo(oldProduct.name)

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (i in dataSnapshot.children) {
                        val item = i.getValue(Product::class.java)
                        if (item != null) {
                            if (item.dayOfMonth == oldProduct.dayOfMonth && item.month == oldProduct.month && item.year == oldProduct.year) {
                                i.key?.let { myRef.child(it).setValue(Product(newProduct.name, newProduct.amount, newProduct.dayOfMonth, newProduct.month, newProduct.year)) }
                                return
                            }
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