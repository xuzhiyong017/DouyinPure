package com.example.douyindownload.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.douyindownload.R
import com.example.douyindownload.db.DBCenter
import com.example.douyindownload.ui.list.ListTaskActivity
import com.example.douyindownload.utils.UrlUtils
import com.vecharm.lychee.http.core.runOnUI
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*
import kotlin.concurrent.thread

class HomeFragment : Fragment(),CoroutineScope by MainScope() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addDownloadTaskButton.setOnClickListener {
            bindData()
        }


    }

    override fun onResume() {
        super.onResume()

    }

    private var videoId: String? = null

    private fun bindData(){
        val url = txt_input.text.toString().trim()
        if(txt_input.text.toString().trim().isNullOrBlank()){
            ToastUtils.showShort("请输入内容")
            return
        }

        parse_url.setText(url)
        if(!url.isNullOrBlank()){
            thread {
                val list = RegexUtils.getMatches("((http[s]{0,1}|ftp)://[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#\$%^&*+?:_/=<>]*)?)|((www.)|[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#\$%^&*+?:_/=<>]*)?)",url)
                if(!list.isNullOrEmpty()){
                    val text = UrlUtils.getRedirectUrl(list[0])
                    videoId = RegexUtils.getReplaceAll(text,"/^\\/|\\/\$/g","").split("/")[6]
                    val requestUrl = "https://www.iesdouyin.com/web/api/mix/item/list/?mix_id=${videoId}&count=10&cursor=1"
                    if(videoId != null){
                        if(DBCenter.db.searchDao().findSearchBeanByVideoId(videoId!!) != null){
                            //已经存在数据了

                        }else{
                            homeViewModel.updateSqlDesc(videoId!!,list[0])

                        }
                        activity?.let { ListTaskActivity.start(it,videoId!!) }
                    }
                   runOnUI {
                       parse_txt.text = requestUrl
                   }
                }
            }

        }
    }

}