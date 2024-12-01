package com.jyproject.sportif.presentation.ui.feature

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.jyproject.sportif.presentation.navigation.AppNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    context: Context,
    onRequestPermission: () -> Unit
) {
    val initPermissionCheck = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    if(initPermissionCheck) {
        AppNavigation()
    }else {
       Column(
           modifier = Modifier
               .fillMaxSize()
               .background(Color.White),
       ) {
           Column(
               modifier = Modifier
                   .padding(vertical = 50.dp, horizontal = 30.dp)
                   .weight(1f)
           ) {
               Text(
                   text = "Sportif의 앱 권한 정보",
                   style = TextStyle(
                       color = Color.Black,
                       fontWeight = FontWeight.ExtraBold,
                       fontSize = 28.sp
                   ))
               Spacer(modifier = Modifier.height(12.dp))
               Row {
                   Icon(
                       imageVector = Icons.Default.LocationOn,
                       contentDescription = "location",
                       tint = Color.LightGray)
                   Spacer(modifier = Modifier.width(2.dp))
                   Text(
                       text = "위치 정보",
                       style =  TextStyle(
                           color = Color.LightGray,
                           fontSize = 14.sp
                       )
                   )
               }
               Text(
                   text = "권한 활용: 사용자 위치기반으로 스포츠센터 추천",
                   style =  TextStyle(
                       color = Color.LightGray,
                       fontSize = 14.sp
                   )
               )
           }
           Box(
               modifier = Modifier
                   .fillMaxWidth(),
               contentAlignment = Alignment.BottomCenter
           ) {
               Column(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(vertical = 28.dp),
                   horizontalAlignment = Alignment.CenterHorizontally
               ) {
                   Text(
                       text = "지금 권한 설정을 하고 Sportif 서비스를 이용해보세요!",
                       style = TextStyle(
                           color = Color.LightGray,
                           fontSize = 12.sp
                       )
                   )
                   Spacer(modifier = Modifier.height(8.dp))
                   Button(
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(horizontal = 20.dp),
                       contentPadding = PaddingValues(vertical = 16.dp),
                       colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                       onClick = {
                           onRequestPermission()
                       }
                   ) {
                       Text(
                           text = "권한 설정하기",
                           style = TextStyle(
                               color = Color.White,
                               fontSize = 16.sp,
                               fontWeight = FontWeight.Bold
                           )
                       )
                   }
               }
           }
       }
    }

}
