package com.jyproject.sportif.data.remote.response.searchNearFacility


import com.squareup.moshi.Json

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