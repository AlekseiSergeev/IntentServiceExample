package com.example.intentserviceexample

import android.app.IntentService
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat

class ExampleIntentService: IntentService("ExampleIntentService") {
    private val TAG = "ExampleIntentService"
    private lateinit var wakeLock: PowerManager.WakeLock

    init {
        setIntentRedelivery(true)
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")

        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK, "ExampleApp:Wakelock")
        wakeLock.acquire(10*60*1000L /*10 minutes*/)
        Log.d(TAG, "Wakelock acquire")

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Example IntentService")
                .setContentText("Running...")
                .setSmallIcon(R.drawable.ic_android)
                .build()

            startForeground(1, notification)
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "onHandleIntent")

        val input = intent?.getStringExtra("inputExtra")

        for(i in 1..10) {
            Log.d(TAG, "$input - $i")
            SystemClock.sleep(1000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
        wakeLock.release()
        Log.d(TAG, "Wakelock released")
    }
}