package com.example.numberplate_10.core.firebase

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class CustomFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        const val notificationChannelID = "1"

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val notificationManager: NotificationManager?
        val builder: Notification.Builder?
        val notification = remoteMessage.notification

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(notificationChannelID, "免排推播", NotificationManager.IMPORTANCE_HIGH)
            channel.description = "免排推播訊息"

            notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
            builder = Notification.Builder(this, notificationChannelID)

        } else {
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            builder = Notification.Builder(this)

        }

        builder.setSmallIcon(applicationInfo.icon)
                .setContentTitle(notification?.title)
                .setContentText(notification?.body)
        notificationManager?.notify(notificationChannelID.toInt() + 1, builder.build())
    }
}