package com.example.douyindownload.bean

data class Aweme(
    val anchor_info: AnchorInfo,
    val author: Author,
    val author_user_id: Long, // 84494473311
    val aweme_id: String, // 6725688444846083339
    val aweme_type: Int, // 4
    val category: Int, // 22
    val cha_list: List<Cha>?,
    val comment_list: Any?, // null
    val create_time: Int, // 1566036005
    val desc: String, //  当干部的人不能想当然 @抖音小助手 #曾仕强教授 #三国 #袁绍 
    val duration: Int, // 132470
    val forward_id: String, // 0
    val from_xigua: Boolean, // true
    val geofencing: Any?, // null
    val group_id: Long, // 6725688444846083000
    val image_infos: Any?, // null
    val is_live_replay: Boolean, // false
    val is_preview: Int, // 0
    val label_top_text: Any?, // null
    val long_video: Any?, // null
    val mix_info: MixInfo,
    val music: Music,
    val promotions: Any?, // null
    val rate: Int, // 10
    val risk_infos: RiskInfos,
    val share_info: ShareInfo,
    val share_url: String, // https://www.iesdouyin.com/share/video/6725688444846083339/?region=&mid=6725665872314272520&u_code=1&titleType=title&did=0&iid=0
    val statistics: Statistics,
    val status: StatusX,
    val status_value: Int, // 102
    val text_extra: List<TextExtra>,
    val timer: Timer,
    val video: Video,
    val video_control: VideoControl,
    val video_labels: Any?, // null
    val video_text: Any? // null
)