package com.lvfq.kotlinbase.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.lvfq.kotlinbase.feature.main.TabMainActivity
import com.lvfq.kotlinbase.kotlinx.coroutines.launchUI
import com.lvfq.kotlinbase.kotlinx.startActivity
import kotlinx.coroutines.delay

/**
 * LoadingActivity
 * @author FaQiang on 2018/11/15 下午12:02
 * @desc :
 *
 */
class LoadingActivity : AppCompatActivity() {


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