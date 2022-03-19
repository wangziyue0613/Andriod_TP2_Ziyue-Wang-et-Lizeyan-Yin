package com.tcpexample.maisondechat.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecordDao {
    @Insert
    fun insertData(vararg bean: RecordBean?)//insérer des données
    @Delete
    fun DeleteData(vararg bean: RecordBean?): Int//effacer
    @Query("SELECT * FROM RECORDBEAN")
    fun queryAll(): List<RecordBean?>?//interroger tout

    @Query("SELECT * FROM RECORDBEAN WHERE id= :id")//Requête par ID
    fun getRecordBeanByName(id: Int): RecordBean?
}