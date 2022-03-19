package com.tcpexample.maisondechat

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
/*
* Affichage des détails de la vue Web
* */

class CatDetailQWebViewMainActivity:  AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catmainweb)
        val webview : WebView = findViewById(R.id.webview);


        webview.loadUrl(intent.getStringExtra("url")!!);



    }

}