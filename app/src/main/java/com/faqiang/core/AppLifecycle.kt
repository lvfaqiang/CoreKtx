package com.faqiang.core

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.util.*


/**
 * AppLifecycle2022/4/25 09:52
 * @desc :
 *
 */
object AppLifecycle : Application.ActivityLifecycleCallbacks {

    private val activitys = LinkedList<Activity>()
    private var activityCount = 0

    /**
     * 应用是否在前台显示
     */
    var isForeground = false
        private set

    private var backgroundTime = 0L

    private fun add(activity: Activity?) = activity?.let { activitys.add(it) }

    private fun remove(activity: Activity?) = activity?.let { activitys.remove(it) }

    fun finishAll() {
        activitys.filter { !it.isFinishing }
            .forEach {
                it.finish()
            }
    }


    fun <T> contains(clazz: Class<T>): Boolean {
        val name = clazz.name  // 格式： tech.hash.option.feature.login.LoginActivity
        return activitys.any { item ->
            item::class.java.name == name
        }
    }

    fun <T> startTo(clazz: Class<T>) {
        try {
            while (activitys.peekLast()::class.java.name != clazz.name) {
                val activity = activitys.pollLast()
                activity?.finish()
            }
        } catch (e: Exception) {
        }
    }

    fun latestActivity() = takeIf { activitys.size > 0 }?.let { activitys.last }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        add(p0)
    }

    override fun onActivityStarted(p0: Activity) {

    }

    override fun onActivityResumed(p0: Activity) {
        if (!isForeground) {
            isForeground = true
            val curTime = System.currentTimeMillis()
            if (backgroundTime != 0L && curTime - backgroundTime > 3000) {
                backgroundTime = 0
                // 应用再后台停留3秒以上，回来执行某些操作，
            }
        }
        activityCount++
    }

    override fun onActivityPaused(p0: Activity) {
        activityCount--
    }

    override fun onActivityStopped(p0: Activity) {
        if (activityCount == 0) {
            isForeground = false
            backgroundTime = 0
        }
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

    }

    override fun onActivityDestroyed(p0: Activity) {
        remove(p0)
    }
}