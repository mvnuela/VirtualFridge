package com.am.virtualfridge

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.fragment.app.Fragment
import com.am.virtualfridge.receipts.AddReceiptDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton


class WebPageFragment : Fragment() {
    private lateinit var  mWebView : WebView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_web_page, container, false)
        mWebView  = view.findViewById(R.id.webview)
        view.findViewById<Button>(R.id.addtoFav).setOnClickListener{getLink(it)}
        val webSettings = mWebView.settings
        webSettings.javaScriptEnabled = true
        mWebView.webViewClient = WebViewClient()
        mWebView.loadUrl("https://www.przepisy.pl")
        return view
    }
    private fun getLink(view: View) {
        AddReceiptDialog(context!!, mWebView.url.toString()).show()
        Log.i("haslo", "odebralem")
        Log.i("haslo", mWebView.url.toString())
    }
}