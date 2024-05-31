package com.ingilizceevi.dataconnection

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.ingilizceevi.datacontroller.PanelController

class ConnectActivity : AppCompatActivity() {
    val brain : gameModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)

    }

    override fun onResume() {
        super.onResume()
        val x = supportFragmentManager.findFragmentById(R.id.container_for_panel) as PanelController
        x.handleOnPressToStartButton().setOnClickListener{
            val data = Intent()
            val chapter = brain.chosenChapter
            val id = brain.chosenStudent.studentID
            val name = brain.chosenStudent.studentFirstName+" "+brain.chosenStudent.studentLastName
            data.putExtra("chapter", chapter)
            data.putExtra("student_id", id)
            data.putExtra("student_name", name)
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }
}