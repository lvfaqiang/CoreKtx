package com.lvfq.kotlinbase

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.multidex.MultiDexApplication
import com.lvfq.library.utils.LvUtils
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * MyApplication
 * @author lvfq
 * @Github: https://github.com/lvfaqiang
 * @Blog: http://blog.csdn.net/lv_fq
 * @date 2017/12/20 上午11:18
 * @desc :
 *
 */
class App : MultiDexApplication(), HasActivityInjector {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context
            private set

        fun getRefWatcher(context: Context) = (context.applicationContext as App).refWatcher
    }

    private lateinit var refWatcher: RefWatcher

    override fun onCreate() {
        super.onCreate()
        mContext = this
        // 初始化相关工具类
        LvUtils.init(this).initLog("lfq", BuildConfig.LvLOG)

        // 初始化 LeakCanary
        refWatcher = LeakCanary.install(this)
    }

    @Inject
    lateinit var activityInject: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = activityInject

}