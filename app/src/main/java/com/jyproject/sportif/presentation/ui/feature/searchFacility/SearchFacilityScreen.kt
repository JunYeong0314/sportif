package com.jyproject.sportif.presentation.ui.feature.searchFacility

import android.util.Log
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyproject.sportif.R
import com.jyproject.sportif.data.remote.response.searchFacility.Item
import com.jyproject.sportif.presentation.ui.feature.common.composable.BaseTopBar
import com.jyproject.sportif.presentation.ui.feature.common.composable.SearchBox
import com.jyproject.sportif.presentation.ui.feature.common.define.ExtensionFunctionDefines.shimmerEffect
import com.jyproject.sportif.presentation.ui.feature.common.define.ExtensionFunctionDefines.simpleVerticalScrollbar
import com.jyproject.sportif.presentation.ui.theme.CustomTheme
import kotlinx.coroutines.coroutineScope
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

    var currentPage by remember {
        mutableIntStateOf(1)
    }
    var isLoadingMoreData by remember {
        mutableStateOf(false)
    }
    val searchResultList = remember {
        mutableStateListOf<Item>()
    }

    LaunchedEffect(state.searchFacilityState.isSuccess, state.searchFacilityState.errorMessage) {
        if (state.searchFacilityState.isSuccess == false && state.searchFacilityState.errorMessage != null) {
            snackbarHostState.showSnackbar(
                message = state.searchFacilityState.errorMessage,
                actionLabel = "닫기"
            )
        }
    }

    LaunchedEffect(state.searchFacilityState.searchResult) {
        val newItems =
            state.searchFacilityState.searchResult?.response?.body?.items?.item?.filterNotNull()
        newItems?.let {
            searchResultList.addAll(newItems)
            isLoadingMoreData = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus()
            }
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BaseTopBar(
            title = "시설 찾기",
            onClickBackButton = {
                onEventSend(SearchFacilityContract.Event.NavigationToBack)
            }
        )
        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            SearchBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 5.dp)
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
                    currentPage = 1
                    searchResultList.clear()
                    focusManager.clearFocus()
                    onEventSend(
                        SearchFacilityContract.Event.SearchFacility(
                            city = city,
                            region = searchRegionText,
                            facilityName = searchFacilityText
                        )
                    )
                }
            )
            Spacer(modifier = Modifier.size(10.dp))
            SearchBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 5.dp)
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
                    currentPage = 1
                    searchResultList.clear()
                    focusManager.clearFocus()
                    onEventSend(
                        SearchFacilityContract.Event.SearchFacility(
                            city = city,
                            region = searchRegionText,
                            facilityName = searchFacilityText
                        )
                    )
                }
            )
            Spacer(modifier = Modifier.height(20.dp))

            if (state.searchFacilityState.isLoading && state.searchFacilityState.searchResult == null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    repeat(15) {
                        SkeletonWidget()
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
            if (state.searchFacilityState.isSuccess == true && searchResultList.isNotEmpty()) {
                val totalCount = state.searchFacilityState.searchResult?.response?.body?.totalCount ?: 0
                val listState = rememberLazyListState()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "총 검색결과: ${totalCount}개",
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.light_gray_hard1)
                    )
                }

                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .simpleVerticalScrollbar(
                            state = listState,
                            cornerRadius = 8.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(searchResultList.size) { index ->
                        val item = searchResultList[index]

                        SearchResultCardWidget(
                            resultData = item,
                            onClickCard = {

                            }
                        )

                        if (index == searchResultList.lastIndex && !isLoadingMoreData && index < totalCount - 1 &&
                            currentPage*10 <= totalCount && !state.searchFacilityState.isLoading) {
                            isLoadingMoreData = true
                            onEventSend(
                                SearchFacilityContract.Event.SearchFacility(
                                    city = city,
                                    region = searchRegionText,
                                    facilityName = searchFacilityText,
                                    pageNo = currentPage + 1
                                )
                            )
                            currentPage += 1
                        }

                        if (index == searchResultList.lastIndex && isLoadingMoreData) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(28.dp)
                                    .padding(bottom = 12.dp),
                                color = colorResource(id = R.color.light_gray_hard1)
                            )
                        }

                        if (index == searchResultList.lastIndex && searchResultList.size == totalCount) {
                            Text(
                                modifier = Modifier.padding(bottom = 12.dp),
                                text = "검색결과가 더 이상 없습니다.",
                                fontSize = 12.sp,
                                color = colorResource(id = R.color.light_gray_hard1)
                            )
                        }
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
                .height(75.dp)
                .weight(0.2f)
                .shimmerEffect(8.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Box(
            modifier = Modifier
                .height(75.dp)
                .weight(0.8f)
                .shimmerEffect(8.dp)
        )
    }
}

@Composable
fun SearchResultCardWidget(
    resultData: Item,
    onClickCard: (Item) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(Color.White)
            .clickable { onClickCard(resultData) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(0.7f)
                        .padding(end = 4.dp),
                    text = resultData.facilNm ?: "",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier
                        .background(
                            color = Color.Black,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp),
                    text = resultData.mainEventNm ?: "",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier.padding(end = 40.dp),
                text = "${resultData.roadAddr} ${resultData.faciDaddr}",
                fontSize = 12.sp,
                color = colorResource(id = R.color.light_gray_hard1),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}