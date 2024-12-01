package com.jyproject.sportif.data.remote.response.searchNearFacility


import com.squareup.moshi.Json

data class Items(
    @Json(name = "item")
    val item: List<Item?>?
)