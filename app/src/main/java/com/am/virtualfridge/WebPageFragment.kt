package com.am.virtualfridge

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class WebPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        goToWebpage()
        return inflater.inflate(R.layout.fragment_web_page, container, false)
    }
    fun goToWebpage(){
        val webpage = Uri.parse("https://www.przepisy.pl")
        val myintent = Intent(Intent.ACTION_VIEW , webpage)
        startActivity(myintent)
    }



}