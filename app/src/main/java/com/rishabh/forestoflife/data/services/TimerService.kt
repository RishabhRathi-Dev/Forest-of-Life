package com.rishabh.forestoflife.data.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.core.app.ApplicationProvider
import com.rishabh.forestoflife.R
import com.rishabh.forestoflife.composables.utils.bottom.BottomBarScreen.Add.title
import com.rishabh.forestoflife.data.TimerViewModel
import com.rishabh.forestoflife.ui.theme.Green
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.scheduleAtFixedRate

class TimerService : Service() {

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "TimerChannel"
        private const val NOTIFICATION_ID = 1
        private const val ACTION_STOP = "com.rishabh.forestoflife.data.services.timer.ACTION_STOP"
    }

    private var isRunning = false
    private var startTime: Long = 0
    private lateinit var timerViewModel: TimerViewModel

    private  var endTime : Long = 0
    private var elapsedTime : Long = 0

    private lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()
        timerViewModel = TimerViewModel.getInstance(applicationContext)
        timerViewModel.setElapsedTime(0L)
        timerViewModel.setStopped(false)
        startTime = SystemClock.elapsedRealtime()
        endTime = timerViewModel.getTimerTarget()!!
        val sharedPreferences = applicationContext.getSharedPreferences("ForestOfLife", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("CurrentEndTime", endTime)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply()
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        when (intent.action) {

            ACTION_STOP -> {
                // Handle stop action
                stopSelf()
            }
            else -> {
                startForeground(NOTIFICATION_ID, createNotification())

                if (!isRunning) {
                    isRunning = true

                    CoroutineScope(Dispatchers.Main).launch {
                        while (isRunning) {
                            val currentTime = SystemClock.elapsedRealtime()
                            elapsedTime = currentTime - startTime
                            notificationManager.notify(
                                1,
                                createNotification()
                            )

                            // is dnd is stopped by user then stop the timer as well
                            val isDNDTurnedOff = notificationManager.currentInterruptionFilter == NotificationManager.INTERRUPTION_FILTER_ALL

                            //Log.d("Service cal", (elapsedTime >= endTime).toString() + ":" + elapsedTime.toString() + ":" + endTime.toString())
                            if ((elapsedTime >= endTime) || isDNDTurnedOff) {
                                stopSelf() // Stop the service when the timer is finished
                            }
                            timerViewModel.setElapsedTime(elapsedTime)
                            delay(1000)
                        }
                    }
                }
            }
        }

        return START_STICKY
    }


    override fun onDestroy() {
        isRunning = false
        timerViewModel.setStopped(true)
        notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
        stopForeground(STOP_FOREGROUND_REMOVE)
        val sharedPreferences = applicationContext.getSharedPreferences("ForestOfLife", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("TotalFocusTime", elapsedTime)
        editor.putLong("CurrentEndTime", 0L)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply()
        }
        super.onDestroy()
    }

    inner class LocalBinder : Binder() {
        fun getService(): TimerService = this@TimerService
    }

    override fun onBind(intent: Intent?): IBinder? {
        return LocalBinder()
    }




    private fun createNotification(): Notification {
        // Create a notification channel (if not created already, required for Android Oreo and above)
        val stopIntent = Intent(this, TimerService::class.java)
        stopIntent.action = ACTION_STOP
        val stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Timer Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            notificationManager =
                getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }


        return NotificationCompat.Builder(this , NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Focus Time")
            .setOngoing(true)
            .setContentText(
                formatTime(elapsedTime)
            )
            .addAction(R.drawable.close_48px, "Stop", stopPendingIntent)
            .setColorized(true)
            .setColor(Green.toArgb())
            .setSmallIcon(R.drawable.mindfulness_focus)
            .setOnlyAlertOnce(true)
            .setAutoCancel(true)
            .build()
    }

    fun formatTime(timeInMillis: Long): String {
        val seconds = timeInMillis / 1000
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return "%02d:%02d".format(minutes, remainingSeconds)
    }

}
