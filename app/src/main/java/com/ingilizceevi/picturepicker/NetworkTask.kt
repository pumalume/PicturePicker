package com.ingilizceevi.picturepicker

import android.util.Log
import android.widget.LinearLayout
import com.castle.testforwordpress.getUnsafeOkHttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import java.io.IOException

class NetworkTask(private val view: LinearLayout) {
    private val endpointUrl = "https://spencerrhein.com/index.php/wp-json/custom/v1/pick_a_pic"
    private val endpointUrl2 = "https://spencerrhein.com/index.php/wp-json/custom/v1/student_list"

    suspend fun postNetworkData(
        studentId: String,
        chapterId: String,
        chapterClicks: String,
        chapterSeconds: String
    ): String? {
        return withContext(Dispatchers.IO) {
            val client = getUnsafeOkHttpClient()
            val json = """
                {
                    "student_id": "$studentId",
                    "chapter_id": "$chapterId",
                    "chapter_clicks": "$chapterClicks",
                    "chapter_seconds": "$chapterSeconds"
                }
            """.trimIndent()
            val requestBody = RequestBody.create(MediaType.get("application/json; charset=utf-8"), json)
            val request = Request.Builder()
                .url(endpointUrl)
                .addHeader("Authorization", Credentials.basic("gulbeyaz.rhein", "Maylee08!"))
                .post(requestBody)
                .build()

            try {
                val response: Response = client!!.newCall(request).execute()
                if (response.isSuccessful) {
                    "It was Successful by spencer"
                } else {
                    Log.e("WordPressApiClient", "Failed to post data by Spencer")
                    null
                }
            } catch (e: IOException) {
                Log.e("WordPressApiClient", "Failed to post data by Spencer: ${e.message}")
                null
            }
        }
    }
}
