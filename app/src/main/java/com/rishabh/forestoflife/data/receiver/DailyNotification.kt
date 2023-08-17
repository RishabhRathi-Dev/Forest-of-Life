package com.rishabh.forestoflife.data.receiver

// NotificationUtils.kt

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.rishabh.forestoflife.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit


private const val CHANNEL_ID = "REMINDER_CHANNEL"

fun scheduleReminderNotification(context: Context, taskId: Long, taskTitle: String, dueDate: Long) {
    val delay = dueDate - System.currentTimeMillis()

    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val data = workDataOf(
        "taskId" to taskId,
        "taskTitle" to taskTitle
    )

    val notificationRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
        .setConstraints(constraints)
        .setInputData(data)
        .addTag(ReminderWorker.TAG)
        .build()

    WorkManager.getInstance(context).enqueue(notificationRequest)
}

fun CancelReminderNotification(context: Context, taskId: Long) {
    WorkManager.getInstance(context).cancelAllWorkByTag(ReminderWorker.TAG)
}

private fun ShowReminderNotification(context: Context, taskId: Long, taskTitle: String) {
    val notificationId = taskId.toInt()

    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setContentTitle("Reminder: $taskTitle")
        .setContentText("Task is due soon!")
        .setSmallIcon(R.mipmap.ic_launcher_round)
        .setAutoCancel(true)
        .build()

    val notificationManager = NotificationManagerCompat.from(context)
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {

    } else {
        notificationManager.notify(notificationId, notification)
    }

}

class ReminderWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    companion object {
        const val TAG = "ReminderWorker"
    }

    override suspend fun doWork(): Result {
        val taskId = inputData.getLong("taskId", -1)
        val taskTitle = inputData.getString("taskTitle")

        if (taskId != -1L && taskTitle != null) {
            withContext(Dispatchers.Main) {
                ShowReminderNotification(applicationContext, taskId, taskTitle)
            }
        }

        return Result.success()
    }
}