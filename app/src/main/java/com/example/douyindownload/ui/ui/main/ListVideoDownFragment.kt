package com.example.douyindownload.ui.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.ToggleButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.douyindownload.App
import com.example.douyindownload.R
import com.example.douyindownload.bean.Aweme
import com.example.douyindownload.db.DBCenter
import com.example.douyindownload.task.DownloadTask
import com.example.douyindownload.ui.video.VideoPlayerActivity
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class ListVideoDownFragment : Fragment() {

    companion object {
        fun newInstance(videoId: String?) : ListVideoDownFragment{
            val fragment = ListVideoDownFragment()
            val  bundle = Bundle()
            bundle.putString("videoId",videoId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: BaseQuickAdapter<DownloadTask, BaseViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.requestData(arguments?.getString("videoId")!!)

        viewModel.mutableList.observe(viewLifecycleOwner,object : Observer<List<Aweme>>{
            override fun onChanged(t: List<Aweme>?) {
               t?.run {
                   forEach {
                       addTask(it.desc.substring(0,it.desc.indexOfFirst { it.toString().equals("@") }-1),it.video.play_addr.url_list[0])
                   }
               }
            }
        })


        listView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        listView.adapter =  object : BaseQuickAdapter<DownloadTask, BaseViewHolder>(R.layout.main_item_progress) {
            override fun convert(helper: BaseViewHolder, item: DownloadTask) {
                when(item.downState){
                    0,1 -> {
                        helper.getView<ToggleButton>(R.id.taskState).visibility = View.INVISIBLE
                        helper.getView<Button>(R.id.taskDown).also {
                            it.visibility = View.VISIBLE
                            it.setOnClickListener {
                                item.download(item.downUrl!!,File(App.app.externalCacheDir,item.videoName+".mp4"))
                                item.downState = 2
                                viewModel.insertTask(item)
                                helper.getView<ToggleButton>(R.id.taskState).visibility = View.VISIBLE
                                it.visibility = View.INVISIBLE
                            }
                        }
                    }
                    2-> {
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
                            it.setOnClickListener {
                                VideoPlayerActivity.start(activity,item.videoName,item.filePath)
                            }
                        }
                    }
                }

                helper.getView<ToggleButton>(R.id.taskState).setOnCheckedChangeListener { _, _ -> item.toggle { } }
                item.updateUI = {
                    helper.getView<TextView>(R.id.taskName).text = "任务:${item.videoName}"
                    helper.getView<TextView>(R.id.speed).text = "${item.getFormatSpeed()}"
                    helper.getView<TextView>(R.id.progress).text = "${item.progress}%"
                    helper.getView<ProgressBar>(R.id.progressBar).also {
                        it.max = 100
                        it.progress = item.progress
                    }
                    viewModel.insertTask(item)
                }

                item.downLoadSuccess = {
                    viewModel.insertTask(item)
                    notifyDataSetChanged()
                }
            }
        }.apply {
            adapter = this
        }

    }

    private fun addTask(videoName:String,downUrl:String) {
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            var downloadTask = DBCenter.db.taskDao().findDownLoadTaskByDownUrl(downUrl)
            if(downloadTask == null){
                downloadTask = DownloadTask()
                downloadTask.videoName = videoName
                downloadTask.downUrl = downUrl
            }
            withContext(Dispatchers.Main){
                adapter.addData(downloadTask)
            }

        }

    }

}

