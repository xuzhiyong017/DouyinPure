package com.example.douyindownload.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.douyindownload.db.bean.SearchModel

/**
 * @author: xuzhiyong
 * @date: 20-11-2  下午2:22
 * @Email: 18971269648@163.com
 * @description: 查询关联表数据
 */
@Dao
interface SearchModelDao {

    @Transaction
    @Query("SELECT * FROM SearchBean WHERE videoId = :videoId")
    fun getSearchModelBy(videoId:String):SearchModel?

    @Transaction
    @Query("SELECT * FROM SearchBean")
    fun getAllSearchModel():List<SearchModel>?
}