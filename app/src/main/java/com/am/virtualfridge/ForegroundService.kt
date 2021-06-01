package com.example.dialogpickerapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class ForegroundService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("TAG", "Operacja wykonana z Service")

        return START_NOT_STICKY
    }
}