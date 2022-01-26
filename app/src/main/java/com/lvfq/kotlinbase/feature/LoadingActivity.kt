package com.lvfq.kotlinbase.feature

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import cn.basic.core.ktx.launchUI
import cn.basic.core.ktx.startActivity
import com.lvfq.kotlinbase.base.BaseEmptyActivity
import com.lvfq.kotlinbase.feature.main.TabMainActivity
import kotlinx.coroutines.delay

/**
 * LoadingActivity
 * @author FaQiang on 2018/11/15 下午12:02
 * @desc :
 *
 */
class LoadingActivity : BaseEmptyActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchUI(lifecycleScope) {
            delay(2000)
            //                     下一步操作
            startActivity<TabMainActivity>()
            finish()
        }
    }
}