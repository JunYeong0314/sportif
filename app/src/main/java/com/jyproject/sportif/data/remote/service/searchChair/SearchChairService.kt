package com.jyproject.sportif.data.remote.service.searchChair

import com.jyproject.sportif.data.remote.response.searchChair.SearchChairResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchChairService {
    @GET("SRVC_DVOUCHER_FACI_COURSE/TODZ_DVOUCHER_FACI_COURSE")
    suspend fun getSearchChair(
        @Query("serviceKey") serviceKey: String,
        @Query("resultType") resultType: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int,
        @Query("cntnt_fst") contentName: String,
    ): Response<SearchChairResponse>
}