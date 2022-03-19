package com.tcpexample.maisondechat.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ImagePhotoDao {
    @Insert
    fun insertData(vararg bean: ImagePhotoBean?);//insérer des données
    @Delete
    fun DeleteData(vararg bean: ImagePhotoBean?): Int//Suprimmer les données
    @Query("SELECT * FROM IMAGEPHOTOBEAN")//interroger toutes les données
    fun queryAll(): List<ImagePhotoBean?>?

    @Query("SELECT * FROM IMAGEPHOTOBEAN WHERE name= :name")//Interroger les données par nom
    fun getImageBeanByName(name: String?): List<ImagePhotoBean?>?
}