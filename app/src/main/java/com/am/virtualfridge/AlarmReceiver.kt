package com.example.dialogpickerapp

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.am.virtualfridge.R


class AlarmReceiver: BroadcastReceiver() {

    companion object {
        const val ID = "CHANEL_ID"
        const val chanelName = "CHANEL_NAME"
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        val manager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelNotification: NotificationCompat.Builder =
            NotificationCompat.Builder(
            context, ID)
            .setContentTitle("Powiadomienie!!")
            .setContentText("Subskrybuj teraz!")
            .setSmallIcon(R.drawable.ic_launcher_background)

        val channel = NotificationChannel(ID,
            chanelName, NotificationManager.IMPORTANCE_HIGH)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(channel)
            manager.notify(1,channelNotification.build())
        }
    }
}





