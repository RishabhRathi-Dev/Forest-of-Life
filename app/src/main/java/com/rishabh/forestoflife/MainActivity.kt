package com.rishabh.forestoflife

import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rishabh.forestoflife.composables.NavigationComposable
import com.rishabh.forestoflife.composables.utils.bottom.BottomBar
import com.rishabh.forestoflife.data.AppViewModel
import com.rishabh.forestoflife.data.REQUESTCODE
import com.rishabh.forestoflife.data.receiver.DailyNotification
import com.rishabh.forestoflife.ui.theme.ForestOfLifeTheme
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val alarmSet = isAlarmSet()

        // If the alarm is not set, schedule it
        if (!alarmSet) {
            setupDailyAlarm()
            markAlarmAsSet()
        }

        setContent {
            ForestOfLifeTheme {
                
                Surface(
                    color = if (isSystemInDarkTheme()) colorResource(id = R.color.black2) else colorResource(
                        id = R.color.app_bg
                    )
                ) {
                    Screen()
                    val navController = rememberNavController()
                    NavigationComposable(
                        context = LocalContext.current,
                        navController = navController,
                    )
                }

                // A surface container using the 'background' color from the theme
                

            }
        }
    }

    private fun isAlarmSet(): Boolean {
        val sharedPreferences = applicationContext.getSharedPreferences("ForestOfLife", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("alarm", false)
    }

    private fun setupDailyAlarm() {
        val notificationIntent = Intent(applicationContext, DailyNotification::class.java)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, REQUESTCODE, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 7)
        calendar.set(Calendar.MINUTE, 0)

        // Ensure the time is in the future (if it's already past 7 AM today)
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        // Set the repeating interval to one day
        val repeatIntervalMillis = AlarmManager.INTERVAL_DAY

        // Schedule the inexact repeating alarm at 7 AM daily
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            repeatIntervalMillis,
            pendingIntent
        )

    }

    private fun markAlarmAsSet() {
        val sharedPreferences = applicationContext.getSharedPreferences("ForestOfLife", Context.MODE_PRIVATE)
        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val nextAlarmInfo = alarmManager.nextAlarmClock

        if (nextAlarmInfo != null) {
            Log.d("AlarmTest", "Next alarm is set for: ${nextAlarmInfo.triggerTime}")
            val editor = sharedPreferences.edit()
            editor.putBoolean("alarm", true)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                editor.apply()
            }
        } else {
            Log.d("AlarmTest", "No alarms are currently set.")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        // in case of app crashes turn off dnd
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (notificationManager.isNotificationPolicyAccessGranted) {
            notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
        }

    }
}
@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(orientation) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
@Composable
fun Screen() {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
}

