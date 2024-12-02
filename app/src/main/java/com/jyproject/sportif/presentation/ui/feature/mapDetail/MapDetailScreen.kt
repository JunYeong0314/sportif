package com.jyproject.sportif.presentation.ui.feature.mapDetail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyproject.sportif.R
import com.jyproject.sportif.data.remote.response.searchNearFacility.Item
import com.jyproject.sportif.presentation.ui.feature.StaticStore
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalNaverMapApi::class)
@Composable
fun MapDetailScreen(
    state: MapDetailContract.State,
    effectFlow: Flow<MapDetailContract.Effect>?,
    onEventSend: (event: MapDetailContract.Event) -> Unit,
    onEffectSend: (effect: MapDetailContract.Effect) -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var facilityInfo by remember {
        mutableStateOf<Item?>(null)
    }

    LaunchedEffect(effectFlow) {
        effectFlow?.collect { collect ->
            when(collect) {
                is MapDetailContract.Effect.Navigation.ToBack ->
                    onEffectSend(MapDetailContract.Effect.Navigation.ToBack)
            }
        }
    }

    if(showBottomSheet){
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                Text(
                    text = "시설 정보",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
                Spacer(modifier = Modifier.size(12.dp))
                Text(text = "${facilityInfo?.faciNm}")
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = "주소: ${facilityInfo?.faciRoadAddr}")
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = "시설 분류: ${facilityInfo?.ftypeNm}")
                Spacer(modifier = Modifier.size(62.dp))
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NaverMap(
            locationSource = rememberFusedLocationSource(),
            modifier = Modifier.fillMaxSize(),
            properties = MapProperties(
                locationTrackingMode = LocationTrackingMode.NoFollow
            ),
            uiSettings = MapUiSettings(
                pickTolerance = NaverMapConstants.DefaultPickTolerance,
                isZoomControlEnabled = false,
                isScaleBarEnabled = false,
                isLocationButtonEnabled = true,
            ),
            cameraPositionState = CameraPositionState(
                CameraPosition(StaticStore.currentLocation ?: LatLng(0.0, 0.0), 12.5)
            ),
        ) {
            StaticStore.facilityData.let { facilityList->
                facilityList.map { data->
                    data?.let {
                        Marker(
                            width = 24.dp,
                            height = 24.dp,
                            icon = OverlayImage.fromResource(R.drawable.ic_marker),
                            state = MarkerState(position = LatLng(it.faciLat?.toDouble() ?: 0.0, it.faciLot?.toDouble() ?: 0.0)),
                            onClick = { _->
                                facilityInfo = it
                                showBottomSheet = true
                                true
                            }
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 8.dp, top = 8.dp)
                .shadow(elevation = 24.dp, shape = RoundedCornerShape(1.dp))
                .size(40.dp)
                .background(Color.White, shape = CircleShape)
                .clickable {
                    onEventSend(MapDetailContract.Event.NavigationToBack)
                }
        ){
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center),
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back",
                tint = Color.DarkGray
            )
        }
    }
}