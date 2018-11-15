package com.lvfq.kotlinbase

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Process
import android.support.multidex.MultiDexApplication
import java.util.*

/**
 * App
 * @author FaQiang on 2018/8/28 上午11:29
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
class App : MultiDexApplication() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context
            private set

        fun exit() {
            AppLifecycle.finishAll()
            android.os.Process.killProcess(Process.myPid())
        }
    }


    override fun onCreate() {
        super.onCreate()
        mContext = this

        registerActivityLifecycleCallbacks(AppLifecycle)

        initGlobalConfig()

        registerBroadcastReceiver()

    }

    private fun initGlobalConfig() {
//        HawkConfig.init(this)
    }


    private fun registerBroadcastReceiver() {
        val filter = IntentFilter().apply {
            addAction("android.net.conn.CONNECTIVITY_CHANGE");
            addAction("android.net.wifi.WIFI_STATE_CHANGED");
            addAction("android.net.wifi.STATE_CHANGE");
        }
        registerReceiver(mNetworkChangeListener, filter)
    }

    private val mNetworkChangeListener = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                ConnectivityManager.CONNECTIVITY_ACTION -> {
                    val manager = this@App.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val info: NetworkInfo? = manager.activeNetworkInfo
                    if (info != null) {
                        if (info.isConnected) {
                            if (info.type == ConnectivityManager.TYPE_WIFI) {
//                                LogUtil.i("wifi--------")

                            } else if (info.type == ConnectivityManager.TYPE_MOBILE) {
//                                LogUtil.i("mobile--------")
                            }
                        } else {
                            // 当前无网络
//                            LogUtil.i("no NetWork 1--------")
                        }
                    } else {
                        // 当前无网络
//                        LogUtil.i("no NetWork 2--------")
                    }
                }
            }
        }
    }
}


object AppLifecycle : Application.ActivityLifecycleCallbacks {
    private val activitys = LinkedList<Activity>()

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

    fun latestActivity() = takeIf { activitys.size > 0 }?.let { activitys.last }


    override fun onActivityPaused(activity: Activity?) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResumed(activity: Activity?) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityStarted(activity: Activity?) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityDestroyed(activity: Activity?) {
        remove(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityStopped(activity: Activity?) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        add(activity)
    }

}

