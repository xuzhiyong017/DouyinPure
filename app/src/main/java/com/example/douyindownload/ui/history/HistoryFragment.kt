package com.example.douyindownload.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.douyindownload.App
import com.example.douyindownload.R
import com.example.douyindownload.db.bean.SearchBean
import com.example.douyindownload.task.DownloadTask
import com.example.douyindownload.ui.list.ListTaskActivity
import com.example.douyindownload.ui.video.VideoPlayerActivity
import kotlinx.android.synthetic.main.fragment_history.*
import java.io.File

class HistoryFragment : Fragment() {

    private lateinit var viewModel: HistoryViewModel
    private lateinit var adapter: BaseQuickAdapter<SearchBean, BaseViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_history, container, false)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        recycle_view.layoutManager = LinearLayoutManager(activity)
        recycle_view.adapter =  object : BaseQuickAdapter<SearchBean, BaseViewHolder>(R.layout.search_item_list_view) {
            override fun convert(helper: BaseViewHolder, item: SearchBean) {
                helper.getView<TextView>(R.id.taskName).text = item.searchContent
                helper.getView<TextView>(R.id.taskDesc).text = item.searchDesc
                helper.itemView.setOnClickListener {
                    activity?.let { it1 -> ListTaskActivity.start(it1,item.videoId) }
                }
            }
        }.apply {
            adapter = this
        }

        viewModel.mutableList.observe(viewLifecycleOwner, Observer {
            adapter.setNewData(it)
        })

        viewModel.loadAllTask()
    }
}