package com.am.virtualfridge.receipts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import com.am.virtualfridge.R
import android.webkit.WebView


class WebReceiptActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url :String? = intent.getStringExtra("Url")
        setContentView(R.layout.activity_web_receipt)
        val webView : WebView = findViewById(R.id.webView)
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        if(url !=null) {
              webView.loadUrl(url)
        }
    }

    fun Back(view: View) {
        finish()
    }
}