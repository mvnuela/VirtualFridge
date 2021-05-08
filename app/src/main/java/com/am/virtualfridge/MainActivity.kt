package com.am.virtualfridge

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
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
    private lateinit var productInp: EditText
    private lateinit var amonutInp: EditText
    private lateinit var listOfProducts:ArrayList<Product>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val firebase: FirebaseDatabase = FirebaseDatabase.getInstance("https://virtualfridge-47aca-default-rtdb.europe-west1.firebasedatabase.app/")
        myRef = firebase.getReference("ArrayDate")
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        submit = findViewById<Button>(R.id.submitData)
        productInp = findViewById<EditText>(R.id.prodcutInput)
        amonutInp = findViewById<EditText>(R.id.amountInput)
        recyclerView.layoutManager= GridLayoutManager(applicationContext, 1)

 // listenery, po kliknieciu enter po wpisaniu danych do EditTextow klawiatura znika,
        // trzeba pamietac zeby pozniej usunac entera, bo wywali blad jak bedziemy chcieli dodac do bazy
        productInp.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode === KeyEvent.KEYCODE_ENTER) {
                val `in` = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                `in`.hideSoftInputFromWindow(
                    productInp.getApplicationWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
            false
        })

        amonutInp.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode === KeyEvent.KEYCODE_ENTER) {
                val `in` = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                `in`.hideSoftInputFromWindow(
                    amonutInp.getApplicationWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
            false
        })

        //listener buttona, którym dodajemy dane
        submit.setOnClickListener{

           val produkt: String = productInp.text.toString()
           val am: Int = amonutInp.text.toString().toInt()
           val firebaseInput = Product(produkt, am)
            myRef.child("${Date().time}").setValue(firebaseInput)
            productInp.text=null
            amonutInp.text=null

        }

       myRef.addValueEventListener(object : ValueEventListener {
           override fun onCancelled(databaseError: DatabaseError) {
               TODO("Not yet implemented")
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
        recyclerView.adapter=Adapter(arrayData)
    }

    }
