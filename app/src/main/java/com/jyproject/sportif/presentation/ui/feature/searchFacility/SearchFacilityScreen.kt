package com.jyproject.sportif.presentation.ui.feature.searchFacility

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.jyproject.sportif.R
import com.jyproject.sportif.presentation.ui.feature.common.composable.BaseTopBar
import com.jyproject.sportif.presentation.ui.feature.common.composable.SearchBox
import com.jyproject.sportif.presentation.ui.feature.common.define.ExtensionFunctionDefines.shimmerEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun SearchFacilityScreen(
    city: String,
    state: SearchFacilityContract.State,
    effectFlow: Flow<SearchFacilityContract.Effect>?,
    onEventSend: (event: SearchFacilityContract.Event) -> Unit,
    onEffectSend: (event: SearchFacilityContract.Effect) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var searchRegionText by remember {
        mutableStateOf("")
    }
    var searchFacilityText by remember {
        mutableStateOf("")
    }
    // Snackbar 상태와 CoroutineScope 선언
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.searchFacilityState.isSuccess, state.searchFacilityState.errorMessage) {
        if (state.searchFacilityState.isSuccess == false && state.searchFacilityState.errorMessage != null) {
            snackbarHostState.showSnackbar(
                message = state.searchFacilityState.errorMessage,
                actionLabel = "닫기"
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            BaseTopBar(
                title = "시설 찾기",
                onClickBackButton = {
                    onEventSend(SearchFacilityContract.Event.NavigationToBack)
                }
            )
            SearchBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp)
                    .background(
                        color = colorResource(id = R.color.light_gray_weak1),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .height(50.dp),
                focusManager = focusManager,
                value = searchRegionText,
                hint = "시군구 명을 입력하세요. (ex. 강남구)",
                onValueChange = { searchRegionText = it },
                onClickSearch = {
                    onEventSend(SearchFacilityContract.Event.SearchFacility(city = city, region = searchRegionText, facilityName = searchFacilityText))
                }
            )
            Spacer(modifier = Modifier.size(10.dp))
            SearchBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp)
                    .background(
                        color = colorResource(id = R.color.light_gray_weak1),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .height(50.dp),
                focusManager = focusManager,
                value = searchFacilityText,
                hint = "시설명을 입력하세요. (ex. 한국태권도)",
                onValueChange = { searchFacilityText = it },
                onClickSearch = {
                    onEventSend(SearchFacilityContract.Event.SearchFacility(city = city, region = searchRegionText, facilityName = searchFacilityText))
                }
            )
        }
        Spacer(modifier = Modifier.size(20.dp))

        if(state.searchFacilityState.isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                repeat(15) { index ->
                    SkeletonWidget()
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
        if (state.searchFacilityState.isSuccess == true && state.searchFacilityState.searchResult != null) {
            val searchResultList = state.searchFacilityState.searchResult.response?.body?.items?.item

            if (!searchResultList.isNullOrEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(searchResultList.size) { index ->
                        Text(text = searchResultList[index]?.facilNm ?: "시설명 없음")
                    }
                }
            } else {
                Text(text = "검색 결과가 없습니다.")
            }
        }
    }
}

@Composable
fun SkeletonWidget() {
    Row {
        Box(
            modifier = Modifier
                .height(40.dp)
                .weight(0.2f)
                .shimmerEffect(8.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Box(
            modifier = Modifier
                .height(40.dp)
                .weight(0.8f)
                .shimmerEffect(8.dp)
        )
    }
}
