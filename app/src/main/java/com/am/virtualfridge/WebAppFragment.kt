package com.am.virtualfridge

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

class WebAppFragment : Fragment() {

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_web_app, container, false)
      //  view.findViewById<Button>(R.id.AddtoFav).setOnClickListener{getLink(it)}
        return view
    }
    fun getLink(view: View){
        val frag = fragmentManager!!.findFragmentById(R.id.fragmentWebPage) as WebPageFragment
        Log.i("haslo", "wysylam")
       // frag.getLink();
    }
}