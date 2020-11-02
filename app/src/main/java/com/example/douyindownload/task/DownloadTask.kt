package com.example.douyindownload.task

import android.widget.Toast
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.douyindownload.App
import com.example.douyindownload.api.API
import com.vecharm.lychee.http.config.defaults.getService
import com.vecharm.lychee.http.config.defaults.request
import com.vecharm.lychee.http.core.bytesRange
import com.vecharm.lychee.http.core.setRange
import com.vecharm.lychee.http.task.DefaultTask
import com.vecharm.lychee.sample.api.DownloadBean
import retrofit2.Call
import java.io.File

@Entity
class DownloadTask : DefaultTask() {

    @PrimaryKey(autoGenerate = true)
    var _id = 0
    var videoId:String? = null
    var videoName:String? = null
    var downUrl:String? = null
    var downState:Int = 0 //0：初始化，1：开始下载，2：下载中，3下载完成

    override fun onCancel() {
        service?.cancel()
    }

    override fun onResume(url: String, filePath: String) {
        download(url, File(filePath))
    }

    @Ignore
    var onDownLoadSuccess = {bean:DownloadBean ->
        downState = 3
        downLoadSuccess?.invoke() ?: Unit

    }

    @Ignore
    var service: Call<*>? = null

    fun download(url: String, saveFile: File) {
        if(!saveFile.exists()){
            saveFile.createNewFile()
        }
        setPathInfo(url, saveFile.absolutePath)

        service = getService<API>().download(url, range.bytesRange()).request(saveFile.setRange(range)) {
            onUpdateProgress = onUpdate
            onSuccess = onDownLoadSuccess
        }
    }
}