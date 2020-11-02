package com.example.douyindownload.bean

data class MixInfo(
    val cover_url: CoverUrl,
    val create_time: Int, // 1568162720
    val desc: String, // 一样的三国故事，不一样的智慧点评！逐一点评三国风云人物，深度剖析三国重要事件；潜心研究三国军事谋略，全面解读三国博大智慧。
    val extra: String, // {"audit_locktime":1574143041,"ban_fields":[],"first_reviewed":1,"is_quality_mix":0,"next_info":{"cover":"tos-cn-i-0813/0d98a56f41a246dea56a4b871e9881be","desc":"一样的三国故事，不一样的智慧点评！逐一点评三国风云人物，深度剖析三国重要事件；潜心研究三国军事谋略，全面解读三国博大智慧。","name":"曾仕强论三国智慧"},"origin_challenge_content":""}
    val mix_id: String, // 6735207595855841294
    val mix_name: String, // 曾仕强论三国智慧
    val next_info: NextInfo,
    val statis: Statis,
    val status: Status
)