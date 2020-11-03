package com.example.douyindownload.ui.single

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ClipboardUtils
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.UriUtils
import com.example.douyindownload.App
import com.example.douyindownload.R
import com.example.douyindownload.ui.ListVideoDownActivity
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.android.synthetic.main.activity_single_video.*
import java.io.File

class SingleVideoActivity : AppCompatActivity() {

    lateinit var viewModel: SingleVideoViewModel
    val videoId by lazy {
        intent.getStringExtra("videoId")
    }

    companion object{
        fun start(context: Context, videoId:String){
            context.startActivity(
                Intent(context, SingleVideoActivity::class.java)
                .putExtra("videoId",videoId)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar { fullScreen(true) }
        setContentView(R.layout.activity_single_video)
        viewModel = ViewModelProvider(this,SingleVideoViewModel.ViewModeFactory(videoId!!))[SingleVideoViewModel::class.java]
        viewModel.singleDownloadTask.observe(this, Observer { task ->
            tv_title.text = task.videoName
            video_view.setVideoURI(Uri.parse(task.downUrl))
            video_view.start()

            task.updateUI = {
                progressBar.also {
                    it.max = 100
                    it.progress = task.progress
                }
                viewModel.insertTask(task)
            }

            task.downLoadSuccess = {
                viewModel.insertTask(task)
                FileUtils.notifySystemToScan(task.filePath)
                txt_down.text = "播放"
                txt_down.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                txt_down.setOnClickListener {
                    video_view.setVideoPath(task.filePath)
                    video_view.start()
                }
            }

            when(task.downState){
                0,1 -> {
                    txt_down.text = "下载"
                    txt_down.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    txt_down.setOnClickListener { _ ->
                        task.downState = 2
                        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"/douyin2/${task.videoId}/${task.videoName?.trim()}.mp4")
                        val success = FileUtils.createOrExistsFile(file)
                        task.download(task.downUrl!!,if(success) file else File(App.app.externalCacheDir,task.videoName?.trim()+".mp4")
                        )
                        txt_down.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }
                }
                2 -> {
                    txt_down.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
                3 -> {
                    txt_down.text = "播放"
                    txt_down.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    txt_down.setOnClickListener {
                        video_view.setVideoPath(task.filePath)
                        video_view.start()
                    }
                }

            }
        })

        loadData()

        tv_title.setOnClickListener {
            ClipboardUtils.copyText(tv_title.text)
            ToastUtils.showShort("复制成功")
        }
    }

    private fun loadData() {
        viewModel.requestData()
    }
}