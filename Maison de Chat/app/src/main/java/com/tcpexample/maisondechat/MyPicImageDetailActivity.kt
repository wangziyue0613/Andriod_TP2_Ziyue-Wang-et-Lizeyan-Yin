package com.tcpexample.maisondechat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tcpexample.maisondechat.databinding.ActivityPicDetailBinding
import java.io.File
/*
* Affichage de l'aperçu de la carte
* */
class MyPicImageDetailActivity: AppCompatActivity() {
    var myBinding: ActivityPicDetailBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myBinding = DataBindingUtil.setContentView(this, R.layout.activity_pic_detail)
        myBinding!!.image.setImageBitmap(BitmapUtils.getBitmapByFile(File(intent.getStringExtra("imagePath"))))
    }
}