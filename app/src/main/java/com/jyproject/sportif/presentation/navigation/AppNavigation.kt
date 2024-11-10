package com.jyproject.sportif.presentation.navigation

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jyproject.sportif.presentation.anim.noAnimatedComposable
import com.jyproject.sportif.presentation.navigation.destination.HomeScreenDestination
import com.jyproject.sportif.presentation.ui.feature.common.util.BottomBar
import com.jyproject.sportif.presentation.ui.feature.common.util.TopBar

@Composable
fun AppNavigation(
    context: Context
) {
    val navController = rememberNavController()
    val items = listOf(
        Screen.Home, Screen.MyPage
    )

    Scaffold(
        bottomBar = { BottomBar(navController = navController, items = items) },
        topBar = { TopBar() }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Navigation.Routes.HOME
        ) {
            noAnimatedComposable(
                route = Navigation.Routes.HOME,
            ) {
                HomeScreenDestination(navController = navController)
            }
        }
    }
}

sealed class Screen(val route: String, val icon: ImageVector) {
    data object Home : Screen("home", icon = Icons.Filled.Home)
    data object MyPage : Screen("mypage", icon = Icons.Filled.Person)
}