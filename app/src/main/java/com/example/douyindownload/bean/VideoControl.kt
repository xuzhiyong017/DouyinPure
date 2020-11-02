package com.example.douyindownload.bean

data class VideoControl(
    val allow_download: Boolean, // false
    val allow_duet: Boolean, // false
    val allow_react: Boolean, // false
    val draft_progress_bar: Int, // 1
    val prevent_download_type: Int, // 3
    val share_type: Int, // 0
    val show_progress_bar: Int // 1
)