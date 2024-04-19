package com.example.workmanagerdemoapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(context: Context, params: WorkerParameters)
    : Worker(context, params) {

    @SuppressLint("MissingPermission")
    override fun doWork(): Result {
        val notification = NotificationCompat
            .Builder(applicationContext, "default")
            .apply {
                setSmallIcon(R.drawable.ic_launcher_foreground)
                setContentTitle("Notification")
                setContentText("This notification is for WorkManager")
                setPriority(NotificationCompat.PRIORITY_DEFAULT)

            }.build()


        var intent : Intent = Intent(applicationContext, MainActivity::class.java)
        var pIntent :PendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        notification.contentIntent = pIntent

        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel =  NotificationChannel("default", "Default",
//                NotificationManager.IMPORTANCE_DEFAULT)
//            val notifManager = getSystemService(NotificationManager::class.java)
//            notifManager.createNotificationChannel(channel)
//        }

        NotificationManagerCompat.from(applicationContext).notify(1, notification)
        //Toast.makeText(applicationContext, "dowork() called", Toast.LENGTH_LONG).show()

        return Result.success()
    }

}