package com.am.virtualfridge

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showFridge(view: View){
        val myintent = Intent(this,MyFridgeActivity::class.java)
        startActivity(myintent)
    }

    fun showRecepits(){
        TODO()
        /* tu bedzie uruchamianie aktywnosci z zapisanymi przepisami */
    }

    fun searchRecepits(view : View){
        Log.i("haslo" , "szukam")
        val myintent = Intent(this,SearchReceiptsActivity::class.java)
        startActivity(myintent)
    }
}
