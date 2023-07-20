package com.rishabh.forestoflife.data.services

import android.app.*
import android.content.Intent
import android.graphics.Color
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
    }

    private var isRunning = false
    private var startTime: Long = 0
    private val timerViewModel = TimerViewModel()
    private val timerLiveData = MutableLiveData<Long>()
    private  var endTime : Long = 0
    private var elapsedTime : Long = 0

    private lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION_ID, createNotification())

        if (!isRunning) {
            isRunning = true
            startTime = SystemClock.elapsedRealtime()
            endTime = timerViewModel.getTimerTarget()!!



            CoroutineScope(Dispatchers.Main).launch {
                while (isRunning) {
                    val currentTime = SystemClock.elapsedRealtime()
                    elapsedTime = currentTime - startTime
                    notificationManager.notify(
                        1,
                        createNotification()
                    )
                    if (elapsedTime >= endTime) {
                        stopSelf() // Stop the service when the timer is finished
                    }
                    timerViewModel.setElapsedTime(elapsedTime)
                    delay(1000)
                }
            }
        }

        return START_STICKY
    }


    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }



    private fun createNotification(): Notification {
        // Create a notification channel (if not created already, required for Android Oreo and above)
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
