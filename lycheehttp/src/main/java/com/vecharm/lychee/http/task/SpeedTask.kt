package com.vecharm.lychee.http.task

import androidx.room.Ignore


open class SpeedTask : Task() {

    var speed = 0L


    @Ignore
    open var updateUI: (() -> Unit)? = null
        set(value) {
            field = value
            value?.invoke()
        }

    @Ignore
    open var downLoadSuccess: (() -> Unit)? = null
        set(value) {
            field = value
        }

    open fun getFormatSpeed(): String {
        if (speed < 1024) return "${speed}B/s"
        val kb = speed / 1024
        if (kb < 1024) return "${kb}KB/s"
        return "${kb / 1024}MB/s"
    }


}