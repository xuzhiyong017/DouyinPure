package com.example.douyindownload.utils

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/**
 * @author: xuzhiyong
 * @date: 20-11-2  上午11:34
 * @Email: 18971269648@163.com
 * @description:
 */
class UrlUtils {

    companion object{
        /**
         * 获取重定向地址
         *
         * @param path
         * @return
         * @throws Exception
         */
        fun getRedirectUrl(path: String): String? {
            var url: String? = null
            try {
                val conn: HttpURLConnection = URL(path).openConnection() as HttpURLConnection
                conn.setInstanceFollowRedirects(false)
                conn.setConnectTimeout(5000)
                url = conn.getHeaderField("Location")
                conn.disconnect()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return url
        }
    }
}