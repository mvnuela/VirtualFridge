package com.am.virtualfridge

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SearchReceiptsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_receipt)
        goToWebpage()

    }
    fun goToWebpage(){
        val webpage = Uri.parse("https://www.przepisy.pl")
        val myintent = Intent(Intent.ACTION_VIEW , webpage)
        startActivity(myintent)
    }
}