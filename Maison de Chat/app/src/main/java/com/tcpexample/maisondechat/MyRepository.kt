package com.tcpexample.maisondechat

import android.app.Activity
import android.content.Context
import com.tcpexample.maisondechat.db.AbstractAppDataBase
import com.tcpexample.maisondechat.db.ImagePhotoBean
import com.tcpexample.maisondechat.db.RecordBean

/*
* Classe de collection d'opérations de base de données
* */
object MyRepository {


    fun getAllImagePhotoBean(activity: Activity?): List<ImagePhotoBean?>? {
        return AbstractAppDataBase.getInstance(activity!!)?.getImagePhotoDao()?.queryAll()
    }

    fun getAllImagePhotoBean(activity: Activity?, name: String?): List<ImagePhotoBean?>? {
        return AbstractAppDataBase.getInstance(activity!!)?.getImagePhotoDao()?.getImageBeanByName(name)
    }

    fun getAllRecordBean(activity: Context?): List<RecordBean?>? {
        return AbstractAppDataBase.getInstance(activity!!)?.getRecordDao()?.queryAll()
    }

    fun getRecordBean(activity: Activity?, id: Int): RecordBean? {
        return AbstractAppDataBase.getInstance(activity!!)?.getRecordDao()?.getRecordBeanByName(id)
    }

    fun insertData(activity: Activity?, recordBean: RecordBean?) {
        AbstractAppDataBase.getInstance(activity!!)?.getRecordDao()?.insertData(recordBean)
    }

    fun insertImageData(activity: Activity?, recordBean: ImagePhotoBean?) {
        AbstractAppDataBase.getInstance(activity!!)?.getImagePhotoDao()?.insertData(recordBean)
    }

    fun delRecordBean(activity: Activity?, recordBean: RecordBean?) {
        AbstractAppDataBase.getInstance(activity!!)?.getRecordDao()?.DeleteData(recordBean)
    }
}