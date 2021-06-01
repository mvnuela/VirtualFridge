package com.example.dialogpickerapp

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.core.app.NotificationCompat
import com.am.virtualfridge.R

class NotificationHelper(context: Context?) : ContextWrapper(context) {
    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setNotificationChanel()
        }
    }
    companion object {
        const val ID = "CHANEL_ID"
        const val chanelName = "CHANEL_NAME"
    }

    private var mManager: NotificationManager? = null
    val manager: NotificationManager?
        get() {
            if (mManager == null) {
                mManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            return mManager
        }
    val channelNotification: NotificationCompat.Builder = NotificationCompat.Builder(
        context!!, ID)
            .setContentTitle("Powiadomienie!!")
            .setContentText("Subskrybuj teraz!")
            .setSmallIcon(R.drawable.ic_launcher_background)

    @TargetApi(Build.VERSION_CODES.O)
    private fun setNotificationChanel() {
        val channel = NotificationChannel(ID, chanelName, NotificationManager.IMPORTANCE_HIGH)
        manager!!.createNotificationChannel(channel)
    }
}