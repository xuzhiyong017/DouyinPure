package com.example.douyindownload.bean

data class Music(
    val album: String,
    val author: String, // 曾仕强留声机
    val collect_stat: Int, // 0
    val cover_hd: CoverHd,
    val cover_large: CoverLarge,
    val cover_medium: CoverMedium,
    val cover_thumb: CoverThumb,
    val duration: Int, // 132
    val end_time: Int, // 0
    val extra: String, // {"hotsoon_review_time":-1,"aggregate_exempt_conf":[],"has_edited":0,"douyin_beats_info":{},"beats":{},"schedule_search_time":0,"music_label_id":null,"reviewed":1,"review_unshelve_reason":0}
    val id: Long, // 6725665872314273000
    val id_str: String, // 6725665872314272520
    val is_original: Boolean, // false
    val is_pgc: Boolean, // false
    val mid: String, // 6725665872314272520
    val offline_desc: String,
    val owner_nickname: String, // 曾仕强留声机
    val play_url: PlayUrl,
    val position: Any?, // null
    val schema_url: String,
    val source_platform: Int, // 23
    val start_time: Int, // 0
    val status: Int, // 1
    val title: String, // @曾仕强留声机创作的原声
    val user_count: Int // 0
)