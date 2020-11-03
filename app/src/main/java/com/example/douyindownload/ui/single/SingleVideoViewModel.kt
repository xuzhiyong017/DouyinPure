package com.example.douyindownload.ui.single

import androidx.lifecycle.*
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.douyindownload.api.API
import com.example.douyindownload.bean.DouBean
import com.example.douyindownload.db.DBCenter
import com.example.douyindownload.task.DownloadTask
import com.vecharm.lychee.http.config.defaults.getService
import com.vecharm.lychee.http.config.defaults.request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author: xuzhiyong
 * @date: 20-11-2  下午6:29
 * @Email: 18971269648@163.com
 * @description:
 */
class SingleVideoViewModel(val videoid:String):ViewModel() {

    class ViewModeFactory(private val id:String):ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SingleVideoViewModel(id) as T
        }
    }

    val singleDownloadTask = MutableLiveData<DownloadTask>()

    fun loadDownLoadTask(downUrl:String,videoName:String){
        viewModelScope.launch(Dispatchers.IO){
            var  temp= DBCenter.db.taskDao().findDownLoadTaskByDownUrl(downUrl)
            if(temp == null){
                temp = DownloadTask()
                temp.videoName = videoName.trim()
                temp.downUrl = downUrl
                temp.videoId = videoid
            }
            singleDownloadTask.postValue(temp)
        }
    }

    fun insertTask(task: DownloadTask) = viewModelScope.launch(Dispatchers.IO) {
        DBCenter.db.taskDao().insertUser(task)
    }

    fun requestData() {
        val url = "https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids=${videoid}"
        getService<API>().getParseUrl(url).enqueue(object :
            Callback<DouBean> {
            override fun onFailure(call: Call<DouBean>, t: Throwable) {
                ToastUtils.showShort(t.message)
            }

            override fun onResponse(
                call: Call<DouBean>,
                response: Response<DouBean>
            ) {
                response.body()?.item_list?.first()?.run {
                    var endIndex = this.desc.indexOfFirst { it.toString() == "@" || it.toString() == "#" }
                    if(endIndex < 0){
                        endIndex = this.desc.length
                    }
                    val videoName = this.desc.substring(0,endIndex)
                    loadDownLoadTask(RegexUtils.getReplaceFirst(video.play_addr.url_list[0],"playwm","play"),videoName)
                }

            }
        })
    }

}