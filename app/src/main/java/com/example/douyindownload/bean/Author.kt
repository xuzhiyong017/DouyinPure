package com.example.douyindownload.bean

import com.example.douyindownload.bean.AvatarLarger
import com.example.douyindownload.bean.AvatarMedium
import com.example.douyindownload.bean.AvatarThumb
import com.example.douyindownload.bean.VideoIcon

data class Author(
    val avatar_larger: AvatarLarger,
    val avatar_medium: AvatarMedium,
    val avatar_thumb: AvatarThumb,
    val aweme_count: Int, // 0
    val custom_verify: String, // 文化学者
    val enterprise_verify_reason: String,
    val favoriting_count: Int, // 0
    val follow_status: Int, // 0
    val follower_count: Int, // 0
    val followers_detail: Any?, // null
    val following_count: Int, // 0
    val geofencing: Any?, // null
    val has_orders: Boolean, // false
    val is_ad_fake: Boolean, // false
    val is_enterprise_vip: Boolean, // false
    val is_gov_media_vip: Boolean, // false
    val nickname: String, // 曾仕强留声机
    val platform_sync_info: Any?, // null
    val policy_version: Any?, // null
    val rate: Int, // 3
    val region: String, // CN
    val sec_uid: String, // MS4wLjABAAAAubmogGOSPFRq1G_WiWh-9w1qrprK9jXOzj9Whgp-hh8
    val secret: Int, // 0
    val short_id: String, // 589707629
    val signature: String, // 官方认证抖音号。围脖: 曾仕强学堂
    val story_open: Boolean, // false
    val total_favorited: String, // 0
    val type_label: List<String>,
    val uid: String, // 84494473311
    val unique_id: String, // zengshiqiang
    val user_canceled: Boolean, // false
    val verification_type: Int, // 1
    val video_icon: VideoIcon,
    val with_commerce_entry: Boolean, // false
    val with_fusion_shop_entry: Boolean, // true
    val with_shop_entry: Boolean // false
)