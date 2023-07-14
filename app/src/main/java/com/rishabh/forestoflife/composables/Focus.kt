package com.rishabh.forestoflife.composables

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.getSystemService
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.LockScreenOrientation
import com.rishabh.forestoflife.composables.utils.bottom.BottomBar
import com.rishabh.forestoflife.composables.utils.headers.MainHeader


@Composable
fun Focus(navHostController : NavHostController){
    // TODO:: Create Focus Page
    Scaffold(
        topBar = { MainHeader(pageName = "Focus") },
        bottomBar = { BottomBar(navController = navHostController) }
    ) {
        Modifier.padding(it)
        startDND()
    }

}

@Composable
fun timer(){

}

@Composable
fun startDND(){

    val notificationManager = LocalContext.current.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (notificationManager.isNotificationPolicyAccessGranted) {
        Log.d("NM", "has permissions")
        notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_PRIORITY)

    } else {
        Log.d("NM", "does not have permissions")
        val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
        startActivity(LocalContext.current, intent, null)
    }
}

@Composable
fun stopDND(){
    val notificationManager = LocalContext.current.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (notificationManager.isNotificationPolicyAccessGranted) {
        Log.d("NM", "has permissions")
        // Set interruption filter to allow all interruptions
        notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
    } else {
        Log.d("NM", "does not have permissions")
        // Open the system settings screen for notification policy access
        val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
        startActivity(LocalContext.current, intent, null)
    }

}

@Preview
@Composable
fun FocusPreview(){
    timer()
}