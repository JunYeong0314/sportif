package com.jyproject.sportif.data.remote.response.searchNearFacility


import com.squareup.moshi.Json

data class Response(
    @Json(name = "body")
    val body: Body?,
    @Json(name = "header")
    val header: Header?
)