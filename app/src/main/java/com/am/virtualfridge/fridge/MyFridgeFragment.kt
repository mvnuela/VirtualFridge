package com.am.virtualfridge.fridge

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.*
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.virtualfridge.AlarmReceiver
import com.am.virtualfridge.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList


class MyFridgeFragment : Fragment() {
    private lateinit var myRef: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var submit: FloatingActionButton
    private lateinit var listOfProducts:ArrayList<Product>
    private lateinit var alarmManager: AlarmManager
    private lateinit var alarmIntent: PendingIntent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager
        alarmIntent = PendingIntent.getBroadcast(context,0, Intent(context,AlarmReceiver::class.java),0)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_my_fridge, container, false)
        val firebase: FirebaseDatabase = FirebaseDatabase.getInstance("https://virtualfridge-47aca-default-rtdb.europe-west1.firebasedatabase.app/")
        val user = FirebaseAuth.getInstance().currentUser!!.uid
        myRef = firebase.getReference("productsInFridge").child(user)
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
                    if(!newRow.alarm){

                       val  date = Calendar.Builder().setDate(newRow.year,newRow.month,newRow.dayOfMonth).setTimeOfDay(1,8,0).build()
                        alarmManager.set(AlarmManager.RTC_WAKEUP,date.time.time,alarmIntent)
                        newRow.alarm=true
                    }
                }
                setupAdapter(listOfProducts)
            }

        })


        return view
    }

    private fun setupAdapter(arrayData: ArrayList<Product>){
        try {
            recyclerView.adapter = AdapterFridge(arrayData, context!!)
        } catch (e: Exception) {
            Log.d("Adapter", "Error")
        }

    }
}