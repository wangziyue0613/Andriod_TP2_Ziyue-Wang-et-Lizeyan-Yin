package com.tcpexample.maisondechat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.tcpexample.maisondechat.db.RecordBean

class MyLocationResultModel(application: Application) : AndroidViewModel(application) {

    private var applicationl: Application? = application
    var edtString: MutableLiveData<List<RecordBean>> = MutableLiveData<List<RecordBean>>()

    fun getAccountData() {
        edtString.postValue(MyRepository.getAllRecordBean(applicationl) as List<RecordBean>?)
    }
}