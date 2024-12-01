package com.jyproject.sportif.data.remote.response.naverMapGeocode


import com.squareup.moshi.Json

data class Code(
    @Json(name = "id")
    val id: String?,
    @Json(name = "mappingId")
    val mappingId: String?,
    @Json(name = "type")
    val type: String?
)