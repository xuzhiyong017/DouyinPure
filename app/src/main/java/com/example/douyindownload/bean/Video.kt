package com.example.douyindownload.bean

data class Video(
    val bit_rate: Any?, // null
    val cover: Cover,
    val download_addr: DownloadAddr,
    val duration: Int, // 132470
    val dynamic_cover: DynamicCover,
    val has_watermark: Boolean, // true
    val height: Int, // 1280
    val is_long_video: Int, // 1
    val origin_cover: OriginCover,
    val play_addr: PlayAddr,
    val play_addr_lowbr: PlayAddrLowbr,
    val ratio: String, // 540p
    val vid: String, // v0200f6a0000blb725dahtm8novfem2g
    val width: Int // 720
)