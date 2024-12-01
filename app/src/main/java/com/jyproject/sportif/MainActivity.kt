package com.jyproject.sportif

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import com.jyproject.sportif.presentation.navigation.AppNavigation
import com.jyproject.sportif.presentation.ui.feature.MainScreen
import com.jyproject.sportif.presentation.ui.theme.CustomTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var isGoSettingPermission = false
    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = android.net.Uri.fromParts("package", packageName, null)
        }
        isGoSettingPermission = true
        startActivity(intent)
    }

    // 권한 상태 확인 메서드
    private fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isLocationPermissionGranted()) {
            setContent {
                CustomTheme {
                    AppNavigation()
                }
            }
        } else {
            setContent {
                CustomTheme {
                    MainScreen(
                        context = this@MainActivity,
                        onRequestPermission = {
                            openAppSettings()
                        }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if(isLocationPermissionGranted() && isGoSettingPermission) {
            setContent {
                CustomTheme {
                    AppNavigation()
                }
            }
            isGoSettingPermission = false
        }
    }
}