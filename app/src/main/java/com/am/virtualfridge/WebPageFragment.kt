package com.am.virtualfridge

import android.graphics.Bitmap
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
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
        val button = view.findViewById<FloatingActionButton>(R.id.addToFav)
        button.setOnClickListener{getLink(it)}
        val webSettings = mWebView.settings
        webSettings.javaScriptEnabled = true
        mWebView.webViewClient = WebViewClient()
        mWebView.loadUrl("https://www.przepisy.pl/")
        mWebView.webViewClient = MyWebViewClient(button)
        return view
    }


    private fun getLink(view: View) {
        AddReceiptDialog(context!!, mWebView.url.toString()).show()
        Log.i("haslo", "odebralem")
        Log.i("haslo", mWebView.url.toString())
    }

    /**
     * przycisk na poczatku nie jest widoczny,
     * staje sie widoczny dopiero gdy adres url jest inny od poczatkowego
     */

    class MyWebViewClient(val button: FloatingActionButton) : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            if (url != "https://www.przepisy.pl/") {
                button.visibility = View.VISIBLE
            }
        }
    }
}