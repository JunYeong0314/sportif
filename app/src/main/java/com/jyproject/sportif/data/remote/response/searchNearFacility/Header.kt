package com.jyproject.sportif.data.remote.response.searchNearFacility


import com.squareup.moshi.Json

data class Header(
    @Json(name = "resultCode")
    val resultCode: String?,
    @Json(name = "resultMsg")
    val resultMsg: String?
)