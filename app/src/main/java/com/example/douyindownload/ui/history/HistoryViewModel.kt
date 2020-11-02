package com.example.douyindownload.ui.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.douyindownload.db.DBCenter
import com.example.douyindownload.db.bean.SearchBean
import com.example.douyindownload.task.DownloadTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {
    fun insertTask(item: DownloadTask)  = viewModelScope.launch(Dispatchers.IO) {
        DBCenter.db.taskDao().insertUser(item)
    }

    val mutableList = MutableLiveData<List<SearchBean>>()
    fun loadAllTask(){
        viewModelScope.launch(Dispatchers.IO) {
            mutableList.postValue(DBCenter.db.searchDao().getAllSearch())
        }

    }
}