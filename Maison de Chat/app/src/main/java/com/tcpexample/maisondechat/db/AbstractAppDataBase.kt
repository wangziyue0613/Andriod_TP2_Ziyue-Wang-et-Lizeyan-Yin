package com.tcpexample.maisondechat.db

import android.content.Context
import androidx.room.Database

import androidx.room.Room

import androidx.room.RoomDatabase


/*
* La classe qui crée la base de données
* */
@Database(entities = [RecordBean::class, ImagePhotoBean::class], version = 32, exportSchema = false)
abstract class  AbstractAppDataBase: RoomDatabase() {

    abstract fun getRecordDao(): RecordDao?//base de données d'enregistrement
    abstract fun getImagePhotoDao(): ImagePhotoDao?//base de données d'images

    companion object {
        @Volatile
        private var instance //Créer un singleton
                : AbstractAppDataBase? = null

        @Synchronized
        @JvmStatic
        open fun getInstance(context: Context): AbstractAppDataBase? {
            if (instance == null) {
                instance = create(context)
            }
            return instance
        }

        /**
         * créer une base de données */
        open fun create(context: Context): AbstractAppDataBase {
            return Room.databaseBuilder(context, AbstractAppDataBase::class.java, "test12.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }



}