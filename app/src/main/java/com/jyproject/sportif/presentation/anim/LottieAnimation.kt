package com.jyproject.sportif.presentation.anim

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jyproject.sportif.R

@Composable
fun LottieSearch(
    modifier: Modifier
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.lottie_search))

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        isPlaying = true,
        iterations = LottieConstants.IterateForever
    )
}

@Composable
fun LottieBook(
    modifier: Modifier
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.lottie_book))

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        isPlaying = true,
        iterations = LottieConstants.IterateForever
    )
}