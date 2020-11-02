package com.example.douyindownload

import android.app.Application
import com.example.douyindownload.config.MyCoreConfig
import com.vecharm.lychee.http.core.LycheeHttp

/**
 * @author: xuzhiyong
 * @date: 20-10-28  上午11:09
 * @Email: 18971269648@163.com
 * @description:
 */
class App :Application() {

    companion object{
        lateinit var app:App
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        LycheeHttp.init(MyCoreConfig(this))
    }

}