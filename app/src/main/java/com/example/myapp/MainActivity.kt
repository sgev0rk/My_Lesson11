package com.example.myapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    companion object {

        private const val CHANNEL_ID = "CHANNEL_ID"

        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }

    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupListeners()
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupListeners() {
        binding.btnSendSimpleNotification.setOnClickListener {
            sendSimpleNotification()
        }
        binding.sendNotificationWithAction.setOnClickListener {
            sendNotificationWithAction()
        }

        binding.sendNotificationWithProgressBar.setOnClickListener {
            sendNotificationWithProgressBar()
        }

        binding.monthDate.setOnClickListener {
            secondActivity()
        }

        binding.mapOpen.setOnClickListener {
            mapOpen()
        }

        binding.tapLog.setOnClickListener {
            tapLog()
        }


    }

    private fun sendSimpleNotification() {

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Simple Notification")
                .setContentText("Simple text")
                .setStyle(
                        NotificationCompat.BigTextStyle()
                                .bigText("Much longer text that cannot fit one line...")
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        createNotificationChannel()
        NotificationManagerCompat.from(this).notify(1, builder.build())
    }

    private fun sendNotificationWithAction() {

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                0
        )

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Simple notification")
                .setContentText("Short text in one line")
                .setStyle(
                        NotificationCompat.BigTextStyle()
                                .bigText("Much longer text that cannot fit one line...")
                )
                .addAction(R.drawable.ic_launcher_foreground, "Action Title", pendingIntent)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        NotificationManagerCompat.from(this).notify(1, builder.build())

    }

    private fun sendNotificationWithProgressBar() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setContentTitle("Picture Download")
            setContentText("Download in progress")
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setPriority(NotificationCompat.PRIORITY_LOW)
        }
        var PROGRESS_MAX = 100
        var PROGRESS_CURRENT = 0
        NotificationManagerCompat.from(this).apply {
            builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
            notify(1, builder.build())
            Thread(Runnable {
                SystemClock.sleep(2000)
                var progress = 0
                builder.setOnlyAlertOnce(true)
                while (progress <= PROGRESS_MAX) {

                    builder.setProgress(PROGRESS_MAX, progress, false)

                    notify(1, builder.build())
                    SystemClock.sleep(1000)
                    progress += 15
                }
                builder.setOnlyAlertOnce(false)
                builder.setContentText("Download finished")
                        .setProgress(0, 0, false)
                        .setOngoing(false)
                notify(1, builder.build())
            }).start()
        }
    }

    private fun secondActivity() {
        SecondActivity.start(this, "January", 4)
    }

    private fun mapOpen() {
        val gmmIntentUri = Uri.parse("google.streetview:cbll=50.4489128,30.5133284")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    private fun tapLog() {
        registerReceiver(TapReceiver(), IntentFilter("ACTION"))
        sendBroadcast(Intent())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, "name", importance).apply {
                description = "descriptionText"
            }
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}