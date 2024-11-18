package com.jyproject.sportif.data.remote.response.searchChair


import com.squareup.moshi.Json

data class SearchChairResponse(
    @Json(name = "response")
    val response: Response?
)

data class Response(
    @Json(name = "body")
    val body: Body?,
    @Json(name = "header")
    val header: Header?
)

data class Items(
    @Json(name = "item")
    val item: List<Item?>?
)

data class Item(
    @Json(name = "busi_reg_no")
    val busiRegNo: String?,
    @Json(name = "cntnt_fst")
    val cntntFst: String?,
    @Json(name = "course_nm")
    val courseNm: String?,
    @Json(name = "course_num")
    val courseNum: String?,
    @Json(name = "end_time")
    val endTime: String?,
    @Json(name = "row_num")
    val rowNum: Int?,
    @Json(name = "settl_amt")
    val settlAmt: String?,
    @Json(name = "start_time")
    val startTime: String?,
    @Json(name = "weekday")
    val weekday: String?
)

data class Header(
    @Json(name = "resultCode")
    val resultCode: String?,
    @Json(name = "resultMsg")
    val resultMsg: String?
)

data class Body(
    @Json(name = "items")
    val items: Items?,
    @Json(name = "numOfRows")
    val numOfRows: Int?,
    @Json(name = "pageNo")
    val pageNo: Int?,
    @Json(name = "totalCount")
    val totalCount: Int?
)