package com.example.douyindownload.db.bean

import androidx.room.Embedded
import androidx.room.Relation
import com.example.douyindownload.task.DownloadTask

/**
 * @author: xuzhiyong
 * @date: 20-11-2  下午2:16
 * @Email: 18971269648@163.com
 * @description:
 */
data class SearchModel(
    @Embedded val searchBean: SearchBean,
    @Relation(
        parentColumn = "videoId",
        entityColumn = "videoId"
    )
    val taskList:List<DownloadTask>) {
}