package com.ingilizceevi.picturepicker

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import java.io.File

class ConceptLoader (location:String) {
    val location = location
    val theSounds : MutableMap<String,Uri> = mutableMapOf()
    val theImages : MutableMap<String,Drawable> = mutableMapOf()

    fun loadAudio(){
        val myPath = Environment.getExternalStorageDirectory().path + "/Music/" + location + "/"
        //val myPath = Environment.getExternalStorageDirectory().path + "/Music/MySounds/" + myChapter + "/"
        File(myPath).walkBottomUp().forEach {
            if (it.isFile) {
                val u = Uri.parse(it.toString())
                //val s = it.toString()
                val s = it.toString().substringAfterLast("/").dropLast(4)
                theSounds[s]=u
            }
        }
    }
    fun loadDrawables(){
        val myPath = Environment.getExternalStorageDirectory().path + "/Pictures/" + location + "/"
        //val myPath = Environment.getExternalStorageDirectory().path + "/Pictures/MyImages/" + myChapter + "/"
        File(myPath).walkBottomUp().forEach {
            if (it.isFile) {
                val d = Drawable.createFromPath(it.absolutePath)!!
                //val s = it.toString()
                val s = it.toString().substringAfterLast("/").dropLast(4)
                theImages[s]=d
            }
        }
    }



}