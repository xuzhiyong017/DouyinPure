package com.example.douyindownload.db.dao

import androidx.room.*
import com.example.douyindownload.task.DownloadTask
import kotlinx.coroutines.flow.Flow

/**
 * @author: xuzhiyong
 * @date: 20-10-29  下午5:59
 * @Email: 18971269648@163.com
 * @description:
 */
@Dao
interface TaskDao {
    @Query("SELECT * FROM DownloadTask ORDER BY _id ASC")
    fun getAll():List<DownloadTask>

    @Query("SELECT * FROM DownloadTask WHERE downUrl = :path")
    fun findDownLoadTaskByDownUrl(path:String):DownloadTask?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(vararg tasks:DownloadTask)


}