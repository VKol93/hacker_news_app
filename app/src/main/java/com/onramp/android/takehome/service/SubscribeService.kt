package com.onramp.android.takehome.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.onramp.android.takehome.R
import com.onramp.android.takehome.datasource.RemoteDataSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SubscribeService : Service() {
    lateinit var notificationBuilder: NotificationCompat.Builder

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val channelId =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    createNotificationChannel()
                } else {
                    // If earlier version channel ID is not used
                    // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
                    ""
                }
        notificationBuilder = NotificationCompat.Builder(this, channelId )

        val foregroundNotification = notificationBuilder
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setContentTitle("Hacker News Subscribe")
                .setContentText("Hacker News Subscribe")
                .build()
        startForeground(1, foregroundNotification)

        val keyword = intent?.getStringExtra("keyword")
        if(keyword!=null)
            GlobalScope.launch {
                 while(true){
                     val topStories = RemoteDataSource.getTopStories()
                        for(story in topStories)
                            if(story.title.contains(keyword, ignoreCase = true))
                                showNotification()
                        delay(1000*60*60)
            }
        }
        else
            stopSelf() //todo
        return super.onStartCommand(intent, flags, startId)
    }
    private fun showNotification(){ //todo{
        val notification = notificationBuilder
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setContentTitle("New story found")
                .setContentText("New story found")
                .build()
        NotificationManagerCompat.from(this).notify(2, notification)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): String{
        val channelId = "my_service"
        val channelName = "My Background Service"
        val chan = NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_HIGH)
        chan.lightColor = Color.BLUE
        chan.importance = NotificationManager.IMPORTANCE_NONE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }
}