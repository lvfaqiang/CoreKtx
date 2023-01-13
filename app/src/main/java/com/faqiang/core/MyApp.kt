package com.faqiang.core

import android.annotation.SuppressLint
import android.content.Context
import android.os.Process
import androidx.multidex.MultiDexApplication
import com.tencent.mmkv.MMKV
import io.douwan.basic.core.LogUtil
import io.douwan.basic.core.ktx.i
import io.douwan.network.NetworkProvider

/**
 * MyApp2022/9/14 19:40
 * @desc :
 *
 */
class MyApp : MultiDexApplication() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context

        fun exit() {
//            AppLifecycle.finishAll()
            Process.killProcess(Process.myPid())
        }
    }


    override fun onCreate() {
        super.onCreate()
        mContext = this
        MMKV.initialize(this)
        LogUtil.setLogEnable(BuildConfig.DEBUG)
        registerActivityLifecycleCallbacks(AppLifecycle)

        NetworkProvider.get()
            .withContext(this)
            .baseUrl("https://www.baidu.com/")  // 这里必须要配置一个 Http 或者 https 的链接
            .configApiLog {
                it?.i("HTTP")
            }
    }
}