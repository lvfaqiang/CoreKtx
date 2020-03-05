package com.lvfq.kotlinbase.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import androidx.core.app.NotificationCompat
import com.lvfq.kotlinbase.R
import kotlinx.coroutines.*

/**
 * BeatService2019-08-07 23:06
 * @desc :
 *
 */
class BeatService : Service(), CoroutineScope by MainScope() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private var beatJob: Job? = null

    override fun onCreate() {
        super.onCreate()
        /**
         * 服务启动代码。 如果需要，在首界面执行。
         */
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            startForegroundService(Intent(this, BeatService::class.java))
//        } else {
//            startService(Intent(this, BeatService::class.java))
//        }


        val id = (System.currentTimeMillis() % 10000).toInt()
        val channelId = getString(R.string.app_name)    // todo  应用名称
        val notification: Notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelId,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                enableLights(true)
                description = channelId
                setSound(
                    Settings.System.DEFAULT_NOTIFICATION_URI,
                    Notification.AUDIO_ATTRIBUTES_DEFAULT
                )
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)

            notification = NotificationCompat.Builder(this, channelId)
                .setChannelId(channelId)
//                    .setSmallIcon(R.drawable.icon_launcher)   todo 添加应用图标
                .setContentTitle(channelId)
                .setContentText(channelId + "正在运行")
                .setAutoCancel(true)
                .build()

        } else {
            notification = NotificationCompat.Builder(this, channelId)
//                    .setSmallIcon(R.drawable.icon_launcher)  todo 添加应用图标
                .setContentTitle(channelId)
                .setContentText(channelId + "正在运行")
                .setAutoCancel(true)
                .build()
        }
        startForeground(id, notification)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 每次 startIntent 进来都会执行 当前方法， onCreate 只会在第一次创建的时候执行。
        if (beatJob == null) {
            initBeatJob()
        }
        // todo

        return super.onStartCommand(intent, flags, startId)
    }

    private fun initBeatJob() {
        beatJob = GlobalScope.launch(coroutineContext) {
            delay(10 * 1000)
            //todo 心跳检测
        }
    }


    private fun releaseBeatJob() {
        beatJob?.cancel()
        beatJob = null
    }

    override fun onDestroy() {
//        releaseBeatJob() // 释放轮询器
        super.onDestroy()
        cancel()    // 释放当前 CoroutineContext 中所有的协程。
    }

}