package com.example.douyindownload.ui.list

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.example.douyindownload.api.API
import com.example.douyindownload.bean.Aweme
import com.example.douyindownload.bean.DouBean
import com.example.douyindownload.db.DBCenter
import com.example.douyindownload.task.DownloadTask
import com.vecharm.lychee.http.config.defaults.getService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.function.Function
import java.util.stream.Collectors

/**
 * @author: xuzhiyong
 * @date: 20-11-2  下午2:48
 * @Email: 18971269648@163.com
 * @description:
 */
class ListTaskViewModel:ViewModel() {

    fun insertTask(item: DownloadTask)  = viewModelScope.launch(Dispatchers.IO) {
        DBCenter.db.taskDao().insertUser(item)
    }

    val mutableList = MutableLiveData<List<DownloadTask>>()
    private var sourceList = mutableListOf<DownloadTask>()
    private var cursor:Int = 0
    private val count:Int = 20
    var pageState = MutableLiveData<Int>()
    private val TAG = "ListTaskViewModel"

    fun loadAllTask(){
        viewModelScope.launch(Dispatchers.IO) {
            sourceList = DBCenter.db.taskDao().getAll().toMutableList()
            mutableList.postValue(sourceList)
        }
    }

    fun requestNetData(videoId:String){
        getService<API>().getParseUrl("https://www.iesdouyin.com/web/api/mix/item/list/?mix_id=${videoId}&count=${count}&cursor=${cursor}").enqueue(object :
            Callback<DouBean> {
            override fun onFailure(call: Call<DouBean>, t: Throwable) {
                ToastUtils.showShort(t.message)
            }

            override fun onResponse(
                call: Call<DouBean>,
                response: Response<DouBean>
            ) {
                viewModelScope.launch {
                    withContext(Dispatchers.IO){
                        cursor = response.body()?.cursor ?:1
                       val newList =  response.body()?.aweme_list?.stream()?.map(object : Function<Aweme,DownloadTask>{
                            override fun apply(p0: Aweme): DownloadTask {
                                Log.d(TAG, "apply: ${p0.desc} \n index=${p0.desc.indexOfFirst { it.toString().equals("@") }}")
                                var endIndex = p0.desc.indexOfFirst { it.toString().equals("@") }
                                if(endIndex < 0){
                                    endIndex = p0.desc.length
                                }
                                val videoName = p0.desc.substring(0,endIndex)
                                val downUrl = p0.video.play_addr.url_list[0]
                                var downloadTask = DBCenter.db.taskDao().findDownLoadTaskByDownUrl(downUrl)
                                if(downloadTask == null){
                                    downloadTask = DownloadTask()
                                    downloadTask.videoName = videoName.trim()
                                    downloadTask.downUrl = downUrl
                                }
                                return downloadTask
                            }
                        })?.collect(Collectors.toList())

                        if (newList != null) {
                            sourceList.addAll(newList)
                        }
                        hideLoading()
                        mutableList.postValue(sourceList)
                    }
                }

            }
        })
    }

    fun showLoading() {
        pageState.postValue(0)
    }

    fun hideLoading(){
        pageState.postValue(1)
    }
}