package com.am.virtualfridge.fridge

import android.app.*
import android.content.Context.*
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
//    private lateinit var alarmManager: AlarmManager
//    private lateinit var alarmIntent: PendingIntent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val id = System.currentTimeMillis()
//        alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager
//        alarmIntent = PendingIntent.getBroadcast(context, id.toInt(), Intent(context, AlarmReceiver::class.java),PendingIntent.FLAG_ONE_SHOT)

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
                }
                setupAdapter(listOfProducts)
            }

        })

        myRef.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val childName = p0.getValue(Product::class.java)!!.name
                addnotification(childName)
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                val childName = p0.getValue(Product::class.java)!!.name
                deletenotification(childName)
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        return view
    }


     fun addnotification(name : String){
         if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
             val channel = NotificationChannel("n","n",NotificationManager.IMPORTANCE_HIGH)
             val manager = context?.getSystemService(NotificationManager::class.java)
             manager?.createNotificationChannel(channel)
         }
        val builder =  NotificationCompat.Builder(context!!,"n").setContentTitle("Zmiany w lodówce!").setContentText("Dodano produkt: " + name).
        setSmallIcon(R.drawable.ic_add).setAutoCancel(true)

         val managerCompat = NotificationManagerCompat.from(context!!)
         managerCompat.notify(999,builder.build())
     }

    fun deletenotification(name : String){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel("n","n",NotificationManager.IMPORTANCE_HIGH)
            val manager = context?.getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
        val builder =  NotificationCompat.Builder(context!!,"n").setContentTitle("Zmiany w lodówce!").setContentText("Usunięto produkt: " + name).
        setSmallIcon(R.drawable.ic_minus_foreground).setAutoCancel(true)

        val managerCompat = NotificationManagerCompat.from(context!!)
        managerCompat.notify(999,builder.build())
    }
    private fun setupAdapter(arrayData: ArrayList<Product>){
        try {
            recyclerView.adapter = AdapterFridge(arrayData, context!!)
        } catch (e: Exception) {
            Log.d("Adapter", "Error")
        }

    }
}