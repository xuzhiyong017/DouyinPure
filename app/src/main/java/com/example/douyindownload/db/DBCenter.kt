package com.example.douyindownload.db

import androidx.room.Room
import com.example.douyindownload.App

/**
 * @author: xuzhiyong
 * @date: 20-10-29  下午6:10
 * @Email: 18971269648@163.com
 * @description:
 */
object DBCenter {
    val db = Room.databaseBuilder(App.app,AppDatabase::class.java,"db-center").build()
}