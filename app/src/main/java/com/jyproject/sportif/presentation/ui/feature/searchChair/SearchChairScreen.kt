package com.jyproject.sportif.presentation.ui.feature.searchChair

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyproject.sportif.R
import com.jyproject.sportif.data.remote.response.searchChair.Item
import com.jyproject.sportif.presentation.ui.feature.common.composable.BaseTopBar
import com.jyproject.sportif.presentation.ui.feature.common.composable.SearchBox
import com.jyproject.sportif.presentation.ui.feature.common.define.ExtensionFunctionDefines.simpleVerticalScrollbar
import com.jyproject.sportif.presentation.ui.feature.searchFacility.SearchFacilityContract
import com.jyproject.sportif.presentation.ui.feature.searchFacility.SkeletonWidget
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchChairScreen(
    state: SearchChairContract.State,
    effectFlow: Flow<SearchChairContract.Effect>?,
    onEventSend: (event: SearchChairContract.Event) -> Unit,
    onEffectSend: (effect: SearchChairContract.Effect) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var searchText by remember {
        mutableStateOf("")
    }
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

    var showBottomSheet by remember {
        mutableStateOf(false)
    }
    var clickedChairCard by remember {
        mutableStateOf<Item?>(null)
    }

    LaunchedEffect(effectFlow) {
        effectFlow?.collect { effect ->
            when (effect) {
                is SearchChairContract.Effect.Navigation.ToBack -> onEffectSend(
                    SearchChairContract.Effect.Navigation.ToBack
                )
            }
        }
    }


    LaunchedEffect(state.searchChairState.isSuccess, state.searchChairState.errorMessage) {
        if (state.searchChairState.isSuccess == false && state.searchChairState.errorMessage != null) {
            snackbarHostState.showSnackbar(
                message = state.searchChairState.errorMessage,
                actionLabel = "닫기"
            )
        }
    }

    LaunchedEffect(state.searchChairState.searchResult) {
        val newItems =
            state.searchChairState.searchResult?.response?.body?.items?.item?.filterNotNull()
        newItems?.let {
            searchResultList.addAll(newItems)
            isLoadingMoreData = false
        }
    }

    if(showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "강좌 정보",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "강좌명: ${clickedChairCard?.courseNm} / 강좌 번호: ${clickedChairCard?.courseNum}",
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "사업자 번호: ${clickedChairCard?.busiRegNo}",
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "요일 정보: ${getDayFromChar("${clickedChairCard?.weekday}")}",
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(30.dp))
            }
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
            title = "강좌 찾기",
            onClickBackButton = {
                onEventSend(SearchChairContract.Event.NavigationToBack)
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
                value = searchText,
                hint = "종목명을 입력하세요. (ex. 태권도)",
                onValueChange = { searchText = it },
                onClickSearch = {
                    currentPage = 1
                    searchResultList.clear()
                    focusManager.clearFocus()
                    onEventSend(
                        SearchChairContract.Event.SearchChair(
                            contentName = searchText
                        )
                    )
                }
            )
            Spacer(modifier = Modifier.height(20.dp))

            if (state.searchChairState.isLoading && state.searchChairState.searchResult == null) {
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

            if(state.searchChairState.isSuccess == true && searchResultList.isNotEmpty()) {
                val totalCount =
                    state.searchChairState.searchResult?.response?.body?.totalCount ?: 0
                val listState = rememberLazyListState()
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Column {
                        Text(
                            text = "총 검색결과: ${totalCount}개",
                            fontSize = 12.sp,
                            color = colorResource(id = R.color.light_gray_hard1)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        LazyColumn(
                            state = listState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .simpleVerticalScrollbar(
                                    state = listState,
                                    cornerRadius = 8.dp
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(searchResultList.size) { index ->
                                val item = searchResultList[index]

                                SearchChairCardWidget(
                                    data = item,
                                    onClickCard = { clickItem ->
                                        clickedChairCard = clickItem
                                        showBottomSheet = true
                                    }
                                )

                                if (index == searchResultList.lastIndex && !isLoadingMoreData && index < totalCount - 1 &&
                                    currentPage * 10 <= totalCount && !state.searchChairState.isLoading
                                ) {
                                    isLoadingMoreData = true
                                    onEventSend(
                                        SearchChairContract.Event.SearchChair(
                                            contentName = searchText,
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
                    }

                }
            }
        }
    }
}

@Composable
private fun SearchChairCardWidget(
    data: Item,
    onClickCard: (Item) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable { onClickCard(data) },
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "강좌 코스명: ${data.courseNm}",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "시작시간: ${data.startTime} 종료시간: ${data.endTime}",
            fontSize = 12.sp,
            color = colorResource(id = R.color.light_gray_hard1),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

private fun getDayFromChar(dayMask: String): String {
    val days = listOf("월", "화", "수", "목", "금", "토", "일")

    val activeDays = days.filterIndexed { index, _ -> dayMask[index] == '1' }

    return if (activeDays.isNotEmpty()) {
        activeDays.joinToString(" ")
    } else {
        "해당 요일 없음"
    }
}