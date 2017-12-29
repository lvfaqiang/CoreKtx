package com.lvfq.kotlinbase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * StartActivity
 * @author lvfq
 * @Github: https://github.com/lvfaqiang
 * @Blog: http://blog.csdn.net/lv_fq
 * @date 2017/12/20 下午2:18
 * @desc :
 *
 */
class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Observable.interval(1 /*启动页停留时间*/, TimeUnit.SECONDS /*单位，秒*/)
                .take(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    // 下一步操作
                    //                    IntentUtil.startIntent(this,
//                            if (UserUtil.isLogined())
//                                MainActivity::class.java
//                            else LoginActivity::class.java )
                    finish()
                }

    }
}