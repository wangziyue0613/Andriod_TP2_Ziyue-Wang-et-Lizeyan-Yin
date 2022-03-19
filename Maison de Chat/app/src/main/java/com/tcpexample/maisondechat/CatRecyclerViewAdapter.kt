package com.tcpexample.maisondechat

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/*
* item données d'affichage
* */
class CatRecyclerViewAdapter(private val categories : MutableList<CatModel>,
                              var mcontext:Activity
) : RecyclerView.Adapter<CatRecyclerViewAdapter.ViewHolder>() {



    class ViewHolder (itemView: View,var mcontext:Activity): RecyclerView.ViewHolder(itemView) {
        var itemText : TextView
        var itemImage : ImageView
        var itemId : String
        var imaurl : String
        var desc : String
        var webUrl : String

        init {
            itemText = itemView.findViewById(R.id.textView1)
            itemImage = itemView.findViewById(R.id.imageView1)
            itemId = ""
            imaurl = ""
            desc = ""
            webUrl = ""

            itemView.setOnClickListener {
                val toast = Toast.makeText(itemView.context, itemText.text, Toast.LENGTH_LONG)
                toast.show()

                mcontext!!.startActivity(Intent(mcontext, CatDetailMainActivity::class.java)
                    .putExtra("name",itemText.text)
                    .putExtra("desc",desc)
                    .putExtra("webUrl",webUrl)
                    .putExtra("imaurl",imaurl)
                )
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CatRecyclerViewAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item_2, parent, false)
        return ViewHolder(v,mcontext)
    }

    override fun onBindViewHolder(holder: CatRecyclerViewAdapter.ViewHolder, position: Int) {
        holder.itemText.text = categories[position].name
        var imageInt= categories[position].imageLink

        Glide.with(mcontext).load(imageInt).into(holder.itemImage);
        holder.itemId = categories[position].id
        holder.desc = categories[position].description
        holder.webUrl = categories[position].vetstreet_url
        holder.imaurl = categories[position].imageLink

    }

    override fun getItemCount(): Int {
        return categories.size
    }
}