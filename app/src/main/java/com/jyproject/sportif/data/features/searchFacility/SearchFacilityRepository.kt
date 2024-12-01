package com.jyproject.sportif.data.features.searchFacility
import com.jyproject.sportif.data.Defines
import com.jyproject.sportif.data.remote.response.searchFacility.SearchFacilityResponse
import com.jyproject.sportif.data.remote.response.searchNearFacility.GetSearchNearSportFacilityResponse
import com.jyproject.sportif.data.remote.service.searchFacility.SearchFacilityService
import com.jyproject.sportif.data.remote.service.searchNearSportFacility.SearchNearSportFacilityService
import javax.inject.Inject

class SearchFacilityRepository @Inject constructor(
    private val getSearchFacilityService: SearchFacilityService,
    private val searchNearSportFacilityService: SearchNearSportFacilityService
) {
    suspend fun searchFacility(
        pageNo: Int,
        numOfRows: Int,
        cityNm: String?,
        localNm: String?,
        facilityNm: String?
    ): Result<SearchFacilityResponse?> {

        return runCatching {
            getSearchFacilityService.getSearchFacility(
                serviceKey = Defines.SERVICE_KEY,
                resultType = "json",
                pageNo = pageNo,
                numOfRows = numOfRows,
                cityNm = cityNm,
                localNm = localNm,
                facilityNm = facilityNm
            ).body()
        }
    }

    suspend fun searchNearSportFacility(
        pageNo: Int,
        numOfRows: Int,
        city: String,
        district: String
    ): Result<GetSearchNearSportFacilityResponse?> {
        return runCatching {
            searchNearSportFacilityService.getSearchNearSportFacility(
                serviceKey = Defines.SERVICE_KEY,
                resultType = "json",
                pageNo = pageNo,
                numOfRows = numOfRows,
                city = city,
                district = district
            ).body()
        }
    }
}