package com.tcpexample.maisondechat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tcpexample.maisondechat.db.RecordBean
/*données de la liste d'enregistrements
* */
class RecordListModel(application: Application) : AndroidViewModel(application) {
     var application12:Application? = application
    var edtString: MutableLiveData<List<RecordBean>> = MutableLiveData<List<RecordBean>>()

    fun getAccountData(): LiveData<List<RecordBean>>? {
        edtString.postValue(MyRepository.getAllRecordBean(application12) as List<RecordBean>?)
        return edtString
    }
}