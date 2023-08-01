package com.rishabh.forestoflife.data.services

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.provider.Settings
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

class TimerServiceManager constructor(
    private val applicationContext: Context,
){
    private val serviceIntent = Intent(applicationContext, TimerService::class.java)
    private val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    fun startTimerService(){
        startDND()
        if (notificationManager.isNotificationPolicyAccessGranted){
            val notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val ringtone = RingtoneManager.getRingtone(applicationContext, notificationSoundUri)
            ringtone.play()
            ContextCompat.startForegroundService(applicationContext, serviceIntent)
        }
    }
    fun stopTimerService(){
        stopDND()
        applicationContext.stopService(serviceIntent)
    }

    fun startDND(){

        if (notificationManager.isNotificationPolicyAccessGranted) {
            Log.d("NM", "has permissions")
            notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_PRIORITY)

        } else {
            Log.d("NM", "does not have permissions")
            val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
            ContextCompat.startActivity(applicationContext, intent, null)
        }
    }

    fun stopDND(){

        if (notificationManager.isNotificationPolicyAccessGranted) {
            Log.d("NM", "has permissions")
            // Set interruption filter to allow all interruptions
            notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
        } else {
            Log.d("NM", "does not have permissions")
            // Open the system settings screen for notification policy access
            val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
            ContextCompat.startActivity(applicationContext, intent, null)
        }

    }

}