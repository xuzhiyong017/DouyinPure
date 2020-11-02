package com.example.douyindownload.db.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author: xuzhiyong
 * @date: 20-11-2  上午9:15
 * @Email: 18971269648@163.com
 * @description: 搜索实体
 */
@Entity
data class SearchBean(val searchContent:String,val searchDesc:String,val searchUrl:String, @PrimaryKey val videoId:String) {
}