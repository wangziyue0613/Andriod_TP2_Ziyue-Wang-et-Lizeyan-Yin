package com.tcpexample.maisondechat.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class ImagePhotoBean {
    @PrimaryKey(autoGenerate = true)
    private var id = 0//ID d'image
    private var name: String? = null//nom de l'image
    private var path: String? = null//chemin de l'image
    private var jd: Double? = null//Longitude de l'image
    private var wd: Double? = null//Dimensions de l'image


    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getPath(): String? {
        return path
    }

    fun setPath(path: String?) {
        this.path = path
    }


    fun getJd(): Double? {
        return jd
    }

    fun setJd(jd: Double?) {
        this.jd = jd
    }

    fun getWd(): Double? {
        return wd
    }

    fun setWd(wd: Double?) {
        this.wd = wd
    }
}