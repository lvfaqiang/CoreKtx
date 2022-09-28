package com.faqiang.core

import android.annotation.SuppressLint
import android.content.Context
import android.os.Process
import androidx.multidex.MultiDexApplication
import com.tencent.mmkv.MMKV
import io.douwan.basic.core.LogUtil

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
        LogUtil.setLogEnable(true)
        registerActivityLifecycleCallbacks(AppLifecycle)
    }
}