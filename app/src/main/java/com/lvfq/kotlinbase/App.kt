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
import androidx.multidex.MultiDexApplication
import cn.basic.core.CoreKtxProvider
import cn.basic.core.api.Error
import cn.basic.core.util.LanguageUtil
import com.lvfq.kotlinbase.cache.AppCache
import com.lvfq.kotlinbase.views.CustomClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import java.util.*

/**
 * App
 * @author FaQiang on 2018/8/28 上午11:29
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
            Process.killProcess(Process.myPid())
        }

        init {
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white) //全局设置主题颜色

                MaterialHeader(
                    context
                )
            }

            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
                CustomClassicsFooter(context)   // 自定义 仅仅是为了解决 刷新加载文案国际化问题，
            }
        }
    }


    override fun onCreate() {
        super.onCreate()
        mContext = this

        registerActivityLifecycleCallbacks(AppLifecycle)

        initGlobalConfig()

        registerBroadcastReceiver()

        LanguageUtil.updateLanguage(this, AppCache.getLanguage())    // 默认中文
    }

    private fun initGlobalConfig() {
        CoreKtxProvider.get()
            .setBaseUrl(BuildConfig.BASE_URL)
            .setSPName(BuildConfig.SP_CACHE_NAME)
            .setCustomExceptionHandling { e ->
                val errorString = e.response()?.errorBody()?.string() ?: ""
                //实现异常处理

                return@setCustomExceptionHandling Error(0, "")
            }
            .build(this)
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
                    val manager =
                        this@App.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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

    /**
     * 应用是否在前台显示
     */
    var isForeground = false
        private set

    private var activityCount = 0

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


    override fun onActivityPaused(activity: Activity) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResumed(activity: Activity) {
        //To change body of created functions use File | Settings | File Templates.
        if (!isForeground) {
            isForeground = true
        }
        activityCount++
    }

    override fun onActivityStarted(activity: Activity) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityDestroyed(activity: Activity) {
        remove(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityStopped(activity: Activity) {
        //To change body of created functions use File | Settings | File Templates.
        activityCount--
        if (activityCount == 0) {
            isForeground = false
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        add(activity)
    }

}

