package com.lvfq.kotlinbase.feature

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.lvfq.kotlinbase.kotlinx.startActivity
import com.lvfq.kotlinbase.base.BaseActivity
import com.lvfq.kotlinbase.kotlinx.coroutines.launchUI
import kotlinx.coroutines.delay

/**
 * LoadingActivity
 * @author FaQiang on 2018/11/15 下午12:02
 * @desc :
 *
 */
class LoadingActivity : BaseActivity() {
    override val layoutRes: Int
        get() = 0

    override fun init(savedInstanceState: Bundle?) {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchUI(lifecycleScope) {
            delay(2000)
            //                     下一步操作
            startActivity<MainActivity>()
            finish()
        }
    }
}