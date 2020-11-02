package com.example.douyindownload.ui.ui.main

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
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val mutableList = MutableLiveData<List<Aweme>>()

    fun requestData(videoId: String) {

        getService<API>().getParseUrl("https://www.iesdouyin.com/web/api/mix/item/list/?mix_id=${videoId}&count=5&cursor=1").enqueue(object :
            Callback<DouBean> {
            override fun onFailure(call: Call<DouBean>, t: Throwable) {
                ToastUtils.showShort(t.message)
            }

            override fun onResponse(
                call: Call<DouBean>,
                response: Response<DouBean>
            ) {
                mutableList.value = response.body()?.aweme_list
                ToastUtils.showShort("成功")
            }
        })
    }

    fun insertTask(item: DownloadTask)  = viewModelScope.launch(Dispatchers.IO) {
        DBCenter.db.taskDao().insertUser(item)
    }
}