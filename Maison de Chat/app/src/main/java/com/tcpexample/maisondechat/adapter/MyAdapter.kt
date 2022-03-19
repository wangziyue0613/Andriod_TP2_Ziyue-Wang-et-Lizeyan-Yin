package com.tcpexample.maisondechat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.tcpexample.maisondechat.R
import com.tcpexample.maisondechat.db.RecordBean

/*
* Adaptateur pour parcourir les enregistrements
* */
class MyAdapter constructor(context: Context?, list: List<RecordBean>?): BaseAdapter() {
    private var mInflater: LayoutInflater? =  LayoutInflater.from(context)
    private var recordBeanList: List<RecordBean>? = list


    fun setList(list: List<RecordBean>?) {
        recordBeanList = list
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return recordBeanList!!.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        val holder: ViewHolder
        if (convertView == null) {
            convertView = mInflater?.inflate(R.layout.item_record_layout, null)
            holder = ViewHolder()
            holder.title =
                convertView!!.findViewById<View>(R.id.tv_name) as TextView? // to ItemButton
            convertView.setTag(holder)
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.title?.setText(recordBeanList!![position].getData())
        return convertView
    }

    override fun getItem(position: Int): Any? {
        // TODO Auto-generated method stub
        return null
    }

    override fun getItemId(position: Int): Long {
        // TODO Auto-generated method stub
        return 0
    }

    class ViewHolder {
        var title: TextView? = null
    }
}