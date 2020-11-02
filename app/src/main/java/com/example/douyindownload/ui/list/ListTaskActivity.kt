package com.example.douyindownload.ui.list

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_MEDIA_SCANNER_SCAN_FILE
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.media.MediaBrowserCompatUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ScreenUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.douyindownload.App
import com.example.douyindownload.R
import com.example.douyindownload.task.DownloadTask
import com.example.douyindownload.ui.video.VideoPlayerActivity
import kotlinx.android.synthetic.main.activity_list_task.*
import kotlinx.android.synthetic.main.main_fragment.*
import java.io.File

class ListTaskActivity : AppCompatActivity() {

    private val TAG = "ListTaskActivity"
    private var cursor: Int = 1
    val videoId: String by lazy {
        intent.getStringExtra("videoId") ?:""
    }

    private lateinit var viewModel: ListTaskViewModel
    private lateinit var adapter: BaseQuickAdapter<DownloadTask, BaseViewHolder>

    companion object{
        fun start(context: Context,videoId:String){
            context.startActivity(Intent(context,ListTaskActivity::class.java)
                .putExtra("videoId",videoId)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_task)
        initData()
        initListenter()
        initRecycleView()
        loadData()
    }

    private fun initRecycleView() {
        recycle_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycle_view.adapter =  object : BaseQuickAdapter<DownloadTask, BaseViewHolder>(R.layout.main_item_progress) {
            override fun convert(helper: BaseViewHolder, item: DownloadTask) {
                Log.d(TAG, "convert: downState=${item.downState}")
                item.videoId = videoId
                helper.getView<TextView>(R.id.taskName).text = "第${helper.adapterPosition + 1}集:${item.videoName}"

                when(item.downState){
                    0,1 -> {
                        helper.getView<ToggleButton>(R.id.taskState).visibility = View.INVISIBLE
                        helper.getView<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
                        helper.getView<TextView>(R.id.progress).visibility = View.VISIBLE
                        helper.getView<TextView>(R.id.speed).visibility = View.VISIBLE
                        helper.getView<Button>(R.id.taskDown).text = "下载"
                        helper.getView<Button>(R.id.taskDown).also {
                            it.visibility = View.VISIBLE
                            it.setOnClickListener {
                                val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"/douyin2/${item.videoId}/${item.videoName}.mp4")
                                val success = FileUtils.createOrExistsFile(file)
                                 item.download(item.downUrl!!,if(success) file else File(App.app.externalCacheDir,item.videoName+".mp4")
                                 )
                                item.downState = 2
                               notifyDataSetChanged()
                            }
                        }
                    }
                    2-> {
                        helper.getView<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
                        helper.getView<TextView>(R.id.progress).visibility = View.VISIBLE
                        helper.getView<TextView>(R.id.speed).visibility = View.VISIBLE
                        helper.getView<ToggleButton>(R.id.taskState).also {
                            it.visibility = View.VISIBLE
                            it.isChecked = !item.isCancel
                            it.setOnCheckedChangeListener { _, _ -> item.toggle { } }
                            helper.getView<Button>(R.id.taskDown).visibility = View.INVISIBLE
                        }
                    }
                    3 -> {
                        helper.getView<ToggleButton>(R.id.taskState).visibility = View.INVISIBLE
                        helper.getView<Button>(R.id.taskDown).also {
                            it.text = "播放"
                            it.visibility = View.VISIBLE
                            helper.getView<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE
                            helper.getView<TextView>(R.id.progress).visibility = View.INVISIBLE
                            helper.getView<TextView>(R.id.speed).visibility = View.INVISIBLE
                            it.setOnClickListener {
                                VideoPlayerActivity.start(this@ListTaskActivity,item.videoName,item.filePath)
                            }
                        }
                    }
                }

                helper.getView<ToggleButton>(R.id.taskState).setOnCheckedChangeListener { _, _ -> item.toggle { } }

                item.updateUI = {
                    when(item.downState){
                        2 ->{
                            helper.getView<TextView>(R.id.speed).text = "${item.getFormatSpeed()}"
                            helper.getView<TextView>(R.id.progress).text = "${item.progress}%"
                            helper.getView<ProgressBar>(R.id.progressBar).also {
                                it.max = 100
                                it.progress = item.progress
                            }
                            viewModel.insertTask(item)
                        }
                        else -> {
                             helper.getView<TextView>(R.id.speed).text = "0kb/s"
                             helper.getView<TextView>(R.id.progress).text = "0%"
                             helper.getView<ProgressBar>(R.id.progressBar).also {
                                it.max = 100
                                it.progress = 0
                            }
                        }
                    }
                }
                item.downLoadSuccess = {
                    viewModel.insertTask(item)
                    FileUtils.notifySystemToScan(item.filePath)
                    notifyDataSetChanged()
                }
            }
        }.apply {
            adapter = this
            setOnLoadMoreListener({
                viewModel.requestNetData(videoId)
            },recycle_view)
        }
    }

    private fun initData() {
        viewModel = ViewModelProvider(this).get(ListTaskViewModel::class.java)
        viewModel.mutableList.observe(this, Observer {
            adapter.setNewData(it)
        })
        viewModel.pageState.observe(this, Observer {
            when(it){
                0 -> { swipe_refresh.isRefreshing = true }
                1 -> { swipe_refresh.isRefreshing = false }
            }
        })
    }

    private fun initListenter() {
        swipe_refresh.setOnRefreshListener {
            loadData()
        }

    }

    private fun loadData() {
        viewModel.showLoading()
        viewModel.requestNetData(videoId)
    }




}