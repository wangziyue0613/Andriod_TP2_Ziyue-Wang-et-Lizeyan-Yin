package com.tcpexample.maisondechat

import android.content.Intent
import com.tcpexample.maisondechat.adapter.MyAdapter

import com.tcpexample.maisondechat.databinding.ActivityRecordBinding

import com.tcpexample.maisondechat.db.RecordBean

import android.widget.AdapterView.OnItemClickListener
import java.util.ArrayList
/*
* liste d'enregistrements obtenir des données
* */
class RecordListViewModel constructor(activity12: RecordListActivity, binding: ActivityRecordBinding?) {
    private var binding: ActivityRecordBinding? = binding
    private var mvvmModel: RecordListModel? = RecordListModel(activity12.application)
    private val list: MutableList<RecordBean> = ArrayList<RecordBean>()
    private var activity: RecordListActivity? = activity12
    private var adapter: MyAdapter? = null



    open fun setListData() {
        adapter = MyAdapter(activity, list)
        binding!!.lv.setAdapter(adapter)
        binding!!.lv.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            activity!!.startActivity(
                Intent(activity, MyLocationResultActivity::class.java)
                    .putExtra("id", list[position].getId())
                    .putExtra("path", list[position].getPath())
                    .putExtra("startTime", list[position].getStartTime())
                    .putExtra("endTime", list[position].getEndTime())
                    .putExtra("duration", list[position].getDuration())
                    .putExtra("durationKm", list[position].getDisKM())
                    .putExtra("Speed", list[position].getSpeed())
            )
        })
    }

    fun getHttpData() {
        mvvmModel!!.getAccountData()!!.observe(activity!!, { data: List<RecordBean>? ->
            list.clear()
            list.addAll(data!!)
            if (adapter != null) {
                adapter!!.setList(list)
                adapter!!.notifyDataSetChanged()
            }
        })
    }
}