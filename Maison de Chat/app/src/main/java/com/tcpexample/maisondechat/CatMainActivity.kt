package com.tcpexample.maisondechat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/*
* Affichage d'accueil
* */
class CatMainActivity:  AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catmaindetail)
        val recyclerView : RecyclerView = findViewById(R.id.rv);
        val btn_gm : Button = findViewById(R.id.btn_gm);
        btn_gm.setOnClickListener {
            startActivity(
                Intent(this,MainActivity::class.java)
            )
        }

        recyclerView.layoutManager=GridLayoutManager(this,2)
        var viewModel:CatMainViewModel= CatMainViewModel(this.application);
        viewModel.fetch();//demander des données d'interface

        viewModel.liveData.observe(this, { data: MutableList<CatModel>? ->//Obtenir des données selon liveData
            var adapter= data?.let { CatRecyclerViewAdapter(it,this) };
            recyclerView.adapter=adapter;

        })
    }

}