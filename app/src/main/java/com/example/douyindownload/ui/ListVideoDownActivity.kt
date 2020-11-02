package com.example.douyindownload.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.douyindownload.R
import com.example.douyindownload.ui.ui.main.ListVideoDownFragment

class ListVideoDownActivity : AppCompatActivity() {

    companion object{
        fun start(context:Context,videoId:String){
            context.startActivity(Intent(context,ListVideoDownActivity::class.java)
                .putExtra("videoId",videoId)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_video_down_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ListVideoDownFragment.newInstance(intent.getStringExtra("videoId")))
                .commitNow()
        }
    }
}