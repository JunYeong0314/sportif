package com.jyproject.sportif.data.remote.response.naverMapGeocode


import com.squareup.moshi.Json

data class Status(
    @Json(name = "code")
    val code: Int?,
    @Json(name = "message")
    val message: String?,
    @Json(name = "name")
    val name: String?
)