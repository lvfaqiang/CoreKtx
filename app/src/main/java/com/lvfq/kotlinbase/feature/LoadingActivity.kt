package com.lvfq.kotlinbase.feature

import android.os.Bundle
import com.fq.library.kotlin.ex.startActivity
import com.lvfq.kotlinbase.base.old.BaseActivity_old
import com.lvfq.kotlinbase.kotlinx.scheduler.applyScheduler
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * LoadingActivity
 * @author FaQiang on 2018/11/15 下午12:02
 * @desc :
 *
 */
class LoadingActivity : BaseActivity_old() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Observable.interval(2, TimeUnit.SECONDS)
                .take(1)
                .applyScheduler()
                .subscribe {
//                     下一步操作
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