package com.am.virtualfridge

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.fragment.app.Fragment


class WebPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_web_page, container, false)
        val mWebView : WebView = view.findViewById(R.id.webview)
        view.findViewById<Button>(R.id.addtoFav).setOnClickListener{getLink(it)}
        val webSettings = mWebView.settings
        webSettings.javaScriptEnabled = true
        mWebView.webViewClient = WebViewClient()
        mWebView.loadUrl("https://www.przepisy.pl");
        return view
    }
    fun getLink(view: View){
        //val mWebView : WebView = view.findViewById(R.id.webview)
        Log.i("haslo", "odebralem")
      //  Log.i("haslo", mWebView.url.toString())
    }
}