package com.rishabh.forestoflife.data.receiver

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import com.rishabh.forestoflife.R
import com.rishabh.forestoflife.data.AppViewModel
import com.rishabh.forestoflife.ui.theme.Green

class DailyNotification : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {

        val applicationContext = p0?.applicationContext as? Application ?: return

        val appViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(applicationContext)
            .create(AppViewModel::class.java)

        val notificationData = appViewModel.getTodayTaskList().value!!.size

        createNotification(context = p0, notificationData)
    }

    private fun createNotification(context: Context, notificationData: Int): Notification {
        // Create a notification channel (if not created already, required for Android Oreo and above)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Daily Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.createNotificationChannel(channel)
        }

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        return NotificationCompat.Builder(context , NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Today's Task")
            .setOngoing(true)
            .setContentText(
                "You have $notificationData task pending"
            )
            .setColorized(true)
            .setColor(Green.toArgb())
            .setSmallIcon(R.drawable._223)
            .setOnlyAlertOnce(true)
            .setAutoCancel(true)
            .setSound(soundUri)
            .build()
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "DailyChannel"
        private const val NOTIFICATION_ID = 2
    }

}