package com.faqiang.core.feature

import android.os.Build
import android.webkit.WebSettings
import com.faqiang.core.AppLifecycle
import com.faqiang.core.base.BaseActivity
import com.faqiang.core.databinding.ActivityWebBinding
import com.gyf.immersionbar.ktx.immersionBar
import io.douwan.basic.core.ktx.click
import io.douwan.basic.core.ktx.startActivity

/**
 * 隐私政策
 */
class WebActivity : BaseActivity<ActivityWebBinding>() {

    companion object {
        const val param_url = "url"
        const val param_title = "title"

        fun startPrivacyPolicy() {
            AppLifecycle.latestActivity()?.startActivity<WebActivity> {
                putExtra(param_title, "隐私协议")
                putExtra(param_url, "")
            }
        }

        fun startUserAgreement() {
            AppLifecycle.latestActivity()?.startActivity<WebActivity> {
                putExtra(param_title, "用户协议")
                putExtra(param_url, "")
            }
        }
    }

    override fun initView() {
        immersionBar {
            statusBarDarkFont(true, 0.2f)
            statusBarView(binding.statusBarView)
        }

        val title = intent?.getStringExtra(param_title) ?: ""
        val url = intent?.getStringExtra(param_url) ?: ""

        binding.tvTitle.text = title
        val webSettings: WebSettings = binding.webView.getSettings()
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        webSettings.allowFileAccess = true
        webSettings.databaseEnabled = false
        webSettings.setSupportMultipleWindows(true)

        //        打开页面时， 自适应屏幕：
        webSettings.useWideViewPort = true //设置此属性，可任意比例缩放 将图片调整到适合webview的大小
        webSettings.loadWithOverviewMode = true // 缩放至屏幕的大小
        webSettings.loadsImagesAutomatically = true //支持自动加载图片
        webSettings.domStorageEnabled = true //不设置此 无法加载h5
        webSettings.javaScriptCanOpenWindowsAutomatically = true //支持js调用window.open方法
        webSettings.javaScriptEnabled = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        binding.webView.loadUrl(url)


        binding.ivBack.click {
            finish()
        }
    }

    override fun initData() {

    }
}