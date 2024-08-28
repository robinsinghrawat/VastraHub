package com.oms.fashionhub

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.VideoView

class MainActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences("session", AppCompatActivity.MODE_PRIVATE)
        var userid = sharedPreferences.getString("id", "0")

        val videoView: VideoView = findViewById(R.id.videoView)
        val videoPath = "android.resource://" + packageName + "/" + R.raw.vastra

        videoView.setVideoURI(Uri.parse(videoPath))
        videoView.start()

        // Set a listener to detect when the video playback is complete
        videoView.setOnCompletionListener { navigateToNextScreen() }
        Handler().postDelayed({
            navigateToNextScreen()
        }, 4300)
    }
    private fun navigateToNextScreen() {

        sharedPreferences = getSharedPreferences("session", AppCompatActivity.MODE_PRIVATE)
        var userid = sharedPreferences.getString("id", "0")

        if(userid=="0") {
            startActivity(Intent(this, LoginPage::class.java))

            finish()
        }else{
            startActivity(Intent(this, HomePage::class.java))
            finish()
        }

    }
}