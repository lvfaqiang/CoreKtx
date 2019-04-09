package com.lvfq.kotlinbase.feature

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import com.lvfq.kotlinbase.base.BaseActivity
import com.lvfq.kotlinbase.kotlinx.activity.startActivity
import com.lvfq.kotlinbase.kotlinx.scheduler.applyScheduler
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * LoadingActivity
 * @author FaQiang on 2018/11/15 下午12:02
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
class LoadingActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Observable.interval(2, TimeUnit.SECONDS)
                .take(1)
                .applyScheduler(this, Lifecycle.Event.ON_DESTROY)
                .subscribe {
                    // 下一步操作
                    startActivity<MainActivity>()
                    finish()
                }
    }

    override fun getLayoutId(): Int = 0

    override fun initUI(savedInstanceState: Bundle?) {

    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initListener() {

    }
}