package com.example.douyindownload.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.example.douyindownload.api.API
import com.example.douyindownload.bean.DouBean
import com.example.douyindownload.db.DBCenter
import com.example.douyindownload.db.bean.SearchBean
import com.vecharm.lychee.http.config.defaults.getService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    fun updateSqlDesc(videoId: String, url: String){
        getService<API>().getParseUrl("https://www.iesdouyin.com/web/api/mix/item/list/?mix_id=${videoId}&count=1&cursor=${1}").enqueue(object :
            Callback<DouBean> {
            override fun onFailure(call: Call<DouBean>, t: Throwable) {
                ToastUtils.showShort(t.message)
            }

            override fun onResponse(
                call: Call<DouBean>,
                response: Response<DouBean>
            ) {
                viewModelScope.launch(Dispatchers.IO) {
                    response.body()?.aweme_list?.first()?.run {
                        DBCenter.db.searchDao().insertSearch(SearchBean(mix_info.next_info.mix_name,mix_info.next_info.desc,url,videoId!!))
                    }

                }

            }
        })
    }
}