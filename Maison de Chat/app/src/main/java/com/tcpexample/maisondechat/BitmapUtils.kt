package com.tcpexample.maisondechat

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.lang.Exception

object BitmapUtils {

    /*
    * Obtenir un objet bitmap à partir du fichier
    * */
    fun  getBitmapByFile(file:File): Bitmap? {
        var fis: FileInputStream? = null
        var bitmap: Bitmap? = null
        try {
            fis = FileInputStream(file)
            bitmap = BitmapFactory.decodeStream(fis)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
        } finally {
            try {
                fis!!.close()
            } catch (e: Exception) {
            }
        }
        return bitmap
    }
    /*
      * objet bitmap compressé
      * */
    fun  changeBitmapSize(bitmap:Bitmap): Bitmap?{
        var bitmap12:Bitmap=bitmap;
        val width: Int = bitmap12.getWidth()
        val height: Int = bitmap12.getHeight()
        val newWidth = 320
        val newHeight = 320
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        bitmap12 = Bitmap.createBitmap(bitmap12, 0, 0, width, height, matrix, true)

        bitmap12.getWidth()

        bitmap12.getHeight()

        return bitmap
    }
}