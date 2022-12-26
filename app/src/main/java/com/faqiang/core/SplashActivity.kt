package com.faqiang.core

import androidx.lifecycle.lifecycleScope
import com.faqiang.core.base.BaseActivity
import com.faqiang.core.databinding.ActivitySplashBinding
import com.gyf.immersionbar.ktx.immersionBar
import io.douwan.basic.core.ktx.launchUI
import io.douwan.basic.core.ktx.startActivity
import kotlinx.coroutines.delay

/**
 * SplashActivity2022/12/26 22:03
 * @desc :
 *
 */
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun initView() {
        immersionBar {
            statusBarDarkFont(true, 0.2f)
            statusBarView(binding.statusBarView)
        }
    }

    override fun initData() {
        gotoMainActivity()
    }

    private fun gotoMainActivity(delayMills: Long = 2000) {
        lifecycleScope.launchUI {
            delay(delayMills)
            startActivity<MainActivity>()
            finish()
        }
    }
}