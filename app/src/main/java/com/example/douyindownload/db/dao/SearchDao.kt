package com.example.douyindownload.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.douyindownload.db.bean.SearchBean
import com.example.douyindownload.task.DownloadTask
import kotlinx.coroutines.flow.Flow

/**
 * @author: xuzhiyong
 * @date: 20-11-2  上午9:17
 * @Email: 18971269648@163.com
 * @description:
 */
@Dao
interface SearchDao {

    @Query("SELECT * FROM SearchBean")
    fun getAllSearch():List<SearchBean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearch(vararg searchBean: SearchBean)

    @Query("SELECT * FROM SearchBean WHERE videoId = :videoId")
    fun findSearchBeanByVideoId(videoId:String): SearchBean?
}