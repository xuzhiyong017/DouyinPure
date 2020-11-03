package com.example.douyindownload.bean

data class DouBean(
    val aweme_list: List<Aweme>,
    val item_list: List<Aweme>,
    val cursor: Int, // 11
    val extra: Extra,
    val has_more: Boolean, // true
    val status_code: Int // 0
)