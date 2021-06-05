package com.am.virtualfridge.fridge

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
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
import com.am.virtualfridge.db.FirebaseFridge.Companion.myRef
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class MyFridgeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var submit: FloatingActionButton
    private lateinit var listOfProducts:ArrayList<Product>
    private lateinit var sharedPref : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor

    /**
     * uzylem sharedPreferences, zeby aplikacja pamietala o produtkach ktore byly w niej ostatnio
     * ustawiam, zeby powiadomienie o nowym produkcie pojawily sie przy ponownym otwarciu aplikacji
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //ustawiam sharedPref
        sharedPref = this.activity?.getSharedPreferences("products", Context.MODE_PRIVATE)!!
        editor = sharedPref.edit()
        //tworze liste, ktora przy tworzeniu posluzy do porownania, ktore produkty ostatnio sie znajdowaly w lodowce
        val sharedProducts = mutableListOf<String>()
        for (i in 0 until sharedPref.all.size) {
            sharedPref.getString("name$i", null)?.let { sharedProducts.add(it) }
        }

       myRef.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val childName = p0.getValue(Product::class.java)!!.name
                if (childName !in sharedProducts) {
                    addNotification(childName)
                }
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                val childName = p0.getValue(Product::class.java)!!.name
                deleteNotification(childName)
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onCancelled(p0: DatabaseError) {
            }

        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_my_fridge, container, false)
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

    /**
     * zapamietuje produkty, zeby sprawdzic co sie zmienilo od ostatniego razu
     * przy onPause usuwam produkty i zapamietuje nowe
     **/
    override fun onPause() {
        editor.clear().commit()
        editor.apply {
            for (item in listOfProducts.indices) {
                putString("name$item", listOfProducts[item].name)
                Log.d("sharedPref", "Added: ${listOfProducts[item].name}")
            }
        }.commit()
        super.onPause()
    }


     fun addNotification(name : String){
         val channel = NotificationChannel("n","n",NotificationManager.IMPORTANCE_HIGH)
         val manager = context?.getSystemService(NotificationManager::class.java)
         manager?.createNotificationChannel(channel)
         val builder =  NotificationCompat.Builder(context!!,"n").setContentTitle("Zmiany w lodówce!").setContentText(
            "Dodano produkt: $name"
        ).
        setSmallIcon(R.drawable.ic_add).setAutoCancel(true)

         val managerCompat = NotificationManagerCompat.from(context!!)
         managerCompat.notify(999,builder.build())
     }

    fun deleteNotification(name : String){
        val channel = NotificationChannel("n","n",NotificationManager.IMPORTANCE_HIGH)
        val manager = context?.getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(channel)
        val builder =  NotificationCompat.Builder(context!!,"n").setContentTitle("Zmiany w lodówce!").setContentText(
            "Usunięto produkt: $name"
        ).
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