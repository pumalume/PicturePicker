package com.ingilizceevi.picturepicker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.gms.security.ProviderInstaller
import com.ingilizceevi.dataconnection.ConnectActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var networkTask: NetworkTask
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ProviderInstaller.installIfNeeded(this)
        //startConnectionActivity()
        networkTask = NetworkTask(LinearLayout(this))
        CoroutineScope(Dispatchers.Main).launch {
            val result = networkTask.postNetworkData(
                studentId = "123",
                chapterId = "456",
                chapterClicks = "789",
                chapterSeconds = "120"
            )
            if (result != null) {
                Log.d("WordPressApiClient", "Response: $result")
                // Handle successful response
            }
        }
        startPictureActivity("02")

    }
    fun startConnectionActivity(){
        val intent = Intent(this, ConnectActivity::class.java)
        startActivityForResult(intent, 0)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                // Get the result from intent
                val chapter_result = data?.getStringExtra("chapter") as String
                val id_result = data?.getStringExtra("student_id")
                val name_result = data?.getStringExtra("student_name")
                findViewById<TextView>(R.id.main_text).text = name_result
                startPictureActivity(chapter_result)
            }
        }
        if (requestCode == 1) {
            startConnectionActivity()
        }
    }

    fun startPictureActivity(chapter:String){
        val game = Intent(this, PictureActivity::class.java)
        val bundle = Bundle()
        game.putExtra("chapter", chapter)
        startActivityForResult(game, 1)
    }
}