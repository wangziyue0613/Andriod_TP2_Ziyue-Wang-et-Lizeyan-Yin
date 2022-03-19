package com.tcpexample.maisondechat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


/*
* Affichage des détails
* */
class CatDetailMainActivity:  AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catmain)
        val image : ImageView = findViewById(R.id.iv_image_touxiang);
        val tvName : TextView = findViewById(R.id.tv_name);
        val tv_desc : TextView = findViewById(R.id.tv_desc);
        val btn_detail : Button = findViewById(R.id.btn_detail);

        tvName.text=intent.getStringExtra("name")
        tv_desc.text=intent.getStringExtra("desc")

        Glide.with(this).load(intent.getStringExtra("imaurl")).into(image);




        btn_detail.setOnClickListener {
startActivity(Intent(this,CatDetailQWebViewMainActivity::class.java)
    .putExtra("url",intent.getStringExtra("webUrl"))
)
        }



    }

}