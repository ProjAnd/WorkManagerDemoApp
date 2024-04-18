package com.example.workmanagerdemoapp

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =  NotificationChannel("default", "Default",
                NotificationManager.IMPORTANCE_DEFAULT)
            val notifManager = getSystemService(NotificationManager::class.java)
            notifManager.createNotificationChannel(channel)
        }
        val notifWorkerRequest : WorkRequest =
            OneTimeWorkRequest.Builder(NotificationWorker::class.java)
                .apply {
                    setInitialDelay(1, TimeUnit.MINUTES)
                }
                .build()

        WorkManager.getInstance(this).enqueue(notifWorkerRequest)


        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(
                this,
                        arrayOf (Manifest.permission.POST_NOTIFICATIONS),
                100
            )
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)

            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED && requestCode==100)
            {
                Toast.makeText(applicationContext, "Permission Granted !", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(applicationContext, "Permission Denied !", Toast.LENGTH_LONG).show()

            }
        }

}