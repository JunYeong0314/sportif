package com.jyproject.sportif.presentation.ui.feature.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.jyproject.sportif.R
import com.jyproject.sportif.presentation.anim.LottieBook
import com.jyproject.sportif.presentation.anim.LottieSearch
import com.jyproject.sportif.presentation.ui.feature.StaticStore
import com.jyproject.sportif.presentation.ui.feature.common.define.ExtensionFunctionDefines.shimmerEffect
import com.jyproject.sportif.presentation.ui.feature.home.composable.SearchButton
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.NaverMapConstants
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun HomeScreen(
    state: HomeContract.State,
    effectFlow: Flow<HomeContract.Effect>?,
    onEventSend: (event: HomeContract.Event) -> Unit,
    onEffectSend: (effect: HomeContract.Effect.Navigation) -> Unit
) {
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    var location by remember { mutableStateOf<Location?>(null) }
    val permissionGranted = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    var mapLoading by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(effectFlow) {
        effectFlow?.collect { effect ->
            when (effect) {
                is HomeContract.Effect.Navigation.ToSearchFacility -> {
                    onEffectSend(effect)
                }
                is HomeContract.Effect.Navigation.ToSearchChair -> {
                    onEffectSend(effect)
                }
                is HomeContract.Effect.Navigation.ToMapDetail -> {
                    onEffectSend(effect)
                }
            }
        }
    }

    LaunchedEffect(permissionGranted) {
        fusedLocationClient.lastLocation.addOnSuccessListener { locationResult ->
            locationResult?.let {
                StaticStore.currentLocation = LatLng(it.latitude, it.longitude)
                location = it
                onEventSend(
                    HomeContract.Event.GetGeocode(
                        latitude = it.latitude,
                        longitude = it.longitude
                    )
                )
            }
        }
    }

    LaunchedEffect(state.homeState) {
        if (state.homeState.nearSportFacilityData == null && state.homeState.geoCodeResponse != null) {
            onEventSend(
                HomeContract.Event.GetNearSportFacility(
                    city = state.homeState.geoCodeResponse.first,
                    district = state.homeState.geoCodeResponse.second
                )
            )
        }

        if(state.homeState.nearSportFacilityData != null) {
            mapLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 18.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            SearchButton(
                onEventSend = {
                    onEventSend(HomeContract.Event.NavigationToSearchFacility)
                }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LottieSearch(
                        modifier = Modifier
                            .height(100.dp)
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 8.dp),
                        text = "시설 찾아보기",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    )
                }
            }
            Spacer(modifier = Modifier.size(24.dp))
            SearchButton(
                onEventSend = {
                    onEventSend(HomeContract.Event.NavigationToSearchChair)
                }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LottieBook(modifier = Modifier.height(100.dp))
                    Text(
                        modifier = Modifier.padding(bottom = 8.dp),
                        text = "강좌 찾아보기",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "근처 스포츠 센터",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        if (!mapLoading) {
            NaverMap(
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                locationSource = rememberFusedLocationSource(),
                properties = MapProperties(
                    locationTrackingMode = LocationTrackingMode.NoFollow
                ),
                uiSettings = MapUiSettings(
                    pickTolerance = NaverMapConstants.DefaultPickTolerance,
                    isZoomControlEnabled = false,
                    isScaleBarEnabled = false
                ),
                cameraPositionState = CameraPositionState(
                    position = CameraPosition(
                        LatLng(
                            location?.latitude ?: StaticStore.currentLocation?.latitude ?: 0.0,
                            location?.longitude ?: StaticStore.currentLocation?.longitude ?: 0.0
                        ), 11.0
                    )
                ),
                onMapClick = { _, _ ->
                    onEventSend(HomeContract.Event.NavigationToMapDetail)
                }
            ) {
                state.homeState.nearSportFacilityData?.map {
                    Marker(
                        width = 24.dp,
                        height = 24.dp,
                        icon = OverlayImage.fromResource(R.drawable.ic_marker),
                        state = MarkerState(position = LatLng(it.latitude, it.longitude))
                    )
                }
            }
        }else {
            Box(
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect(8.dp),
            )
        }

    }

}

