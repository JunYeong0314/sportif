package com.jyproject.sportif.data.remote.response.searchFacility


import com.squareup.moshi.Json

data class SearchFacilityResponse(
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
    @Json(name = "city_cd")
    val cityCd: String?,
    @Json(name = "city_nm")
    val cityNm: String?,
    @Json(name = "faci_daddr")
    val faciDaddr: String?,
    @Json(name = "facil_nm")
    val facilNm: String?,
    @Json(name = "local_cd")
    val localCd: String?,
    @Json(name = "local_nm")
    val localNm: String?,
    @Json(name = "main_event_nm")
    val mainEventNm: String?,
    @Json(name = "res_telno")
    val resTelno: String?,
    @Json(name = "road_addr")
    val roadAddr: String?,
    @Json(name = "row_num")
    val rowNum: Int?
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