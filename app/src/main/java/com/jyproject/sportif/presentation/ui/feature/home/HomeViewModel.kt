package com.jyproject.sportif.presentation.ui.feature.home

import BaseViewModel
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jyproject.sportif.data.features.naverMapGeocode.NaverMapRepository
import com.jyproject.sportif.data.features.searchFacility.SearchFacilityRepository
import com.jyproject.sportif.presentation.ui.feature.StaticStore
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.URLEncoder
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val naverMapRepository: NaverMapRepository,
    private val searchFacilityRepository: SearchFacilityRepository
) :
    BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {
    override fun setInitialState() = HomeContract.State(
        homeState = HomeState()
    )

    override fun handleEvents(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.NavigationToSearchFacility -> setEffect { HomeContract.Effect.Navigation.ToSearchFacility }
            is HomeContract.Event.NavigationToSearchChair -> setEffect { HomeContract.Effect.Navigation.ToSearchChair }
            is HomeContract.Event.GetGeocode -> {
                getGeocode(longitude = event.longitude, latitude = event.latitude)
            }
            is HomeContract.Event.GetNearSportFacility -> {
                getNearbySportCenterData(city = event.city, district = event.district)
            }
            is HomeContract.Event.NavigationToMapDetail -> setEffect { HomeContract.Effect.Navigation.ToMapDetail }
        }
    }

    private fun getGeocode(latitude: Double, longitude: Double) {
        setState { copy(homeState = homeState.copy(isLoading = true)) }

        viewModelScope.launch {
            val coords = "$longitude,$latitude"

            naverMapRepository.getGeocode(
                coords = coords
            ).onSuccess {
                val result = it?.results?.firstOrNull()

                result?.let { data ->
                    val city = data.region?.area1?.name ?: ""
                    val district = data.region?.area2?.name ?: ""

                    setState {
                        copy(homeState = homeState.copy(isLoading = false, geoCodeResponse = Pair(city, district)))
                    }
                }
            }.onFailure {
                setState {
                    copy(homeState = homeState.copy(isLoading = false, errorMessage = it.message))
                }
            }
        }
    }

    private fun getNearbySportCenterData(city: String, district: String) {
        viewModelScope.launch {
            val trimmedDistrict = district.split(" ").firstOrNull() ?: ""

            searchFacilityRepository.searchNearSportFacility(
                pageNo = 1,
                numOfRows = 10000,
                city = city,
                district = trimmedDistrict
            ).onSuccess {
                it?.response?.body?.items?.item?.let { list ->
                    val mappingData = list.map { data->
                        LatLng(data?.faciLat?.toDouble() ?: 0.0, data?.faciLot?.toDouble() ?: 0.0)
                    }
                    setState {
                        copy(homeState = homeState.copy(isLoading = false, nearSportFacilityData = mappingData))
                    }
                    StaticStore.facilityData = list
                }
            }.onFailure {
                setState {
                    copy(homeState = homeState.copy(isLoading = false, errorMessage = it.message))
                }
            }
        }
    }
}