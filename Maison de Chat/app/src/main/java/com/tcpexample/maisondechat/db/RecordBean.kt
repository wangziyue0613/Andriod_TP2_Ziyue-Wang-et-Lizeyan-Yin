package com.tcpexample.maisondechat.db

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
class RecordBean  {
    @PrimaryKey(autoGenerate = true)
    private var id = 0
    private var name: String? = null//enregistrer le nom
    private var path: String? = null//chemin d'enregistrement
    private var startTime: String? = null//enregistrer l'heure de début
    private var endTime: String? = null//enregistrer l'heure de fin
    private var data: String? = null//date d'enregistrement
    private var speed: String? = null//vitesse d'enregistrement
    private var duration: String? = null//durée d'enregistrement
    private var disKM: String? = null//distance d'enregistrement

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

    fun getStartTime(): String? {
        return startTime
    }

    fun setStartTime(startTime: String?) {
        this.startTime = startTime
    }

    fun getEndTime(): String? {
        return endTime
    }

    fun setEndTime(endTime: String?) {
        this.endTime = endTime
    }

    fun getData(): String? {
        return data
    }

    fun setData(data: String?) {
        this.data = data
    }

    fun getSpeed(): String? {
        return speed
    }

    fun setSpeed(speed: String?) {
        this.speed = speed
    }

    fun getDuration(): String? {
        return duration
    }

    fun setDuration(duration: String?) {
        this.duration = duration
    }

    fun getDisKM(): String? {
        return disKM
    }

    fun setDisKM(disKM: String?) {
        this.disKM = disKM
    }
}