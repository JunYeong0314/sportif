package com.jyproject.sportif.data.remote.service.searchFacility

import com.jyproject.sportif.data.Defines
import com.jyproject.sportif.data.remote.response.searchFacility.SearchFacilityResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchFacilityService {
    @GET("/TODZ_API_MNG_DVOUCHER_I?serviceKey=${Defines.SERVICE_KEY}&resultType=json")
    suspend fun getSearchFacility(
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int,
        @Query("city_nm") cityNm: String?,
        @Query("local_nm") localNm: String?,
        @Query("main_event_nm") mainEventNm: String?
    ): Result<SearchFacilityResponse>

}