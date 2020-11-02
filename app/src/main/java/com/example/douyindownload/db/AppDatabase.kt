package com.example.douyindownload.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.douyindownload.db.bean.SearchBean
import com.example.douyindownload.db.dao.SearchDao
import com.example.douyindownload.db.dao.SearchModelDao
import com.example.douyindownload.db.dao.TaskDao
import com.example.douyindownload.task.DownloadTask

/**
 * @author: xuzhiyong
 * @date: 20-10-29  下午5:46
 * @Email: 18971269648@163.com
 * @description:
 */

@Database(entities = arrayOf(DownloadTask::class,SearchBean::class),version = 1)
abstract class AppDatabase : RoomDatabase(){
     abstract fun taskDao(): TaskDao
     abstract fun searchDao(): SearchDao
     abstract fun searchModelDao(): SearchModelDao
}