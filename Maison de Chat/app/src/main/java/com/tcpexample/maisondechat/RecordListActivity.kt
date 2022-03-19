package com.tcpexample.maisondechat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tcpexample.maisondechat.databinding.ActivityRecordBinding
/*
* liste d'enregistrements
* */
class RecordListActivity : AppCompatActivity() {
    private var myBinding: ActivityRecordBinding? = null
    var mvvmViewModel: RecordListViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myBinding = DataBindingUtil.setContentView(this, R.layout.activity_record)
        //        setContentView(R.layout.activity_record);
        mvvmViewModel = RecordListViewModel(this, myBinding)
        mvvmViewModel!!.setListData()
        myBinding!!.setViewModel(mvvmViewModel) //initialiser viewModel
    }

    override fun onResume() {
        super.onResume()
        mvvmViewModel!!.getHttpData()
    }
}