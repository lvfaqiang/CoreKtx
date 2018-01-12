package com.lvfq.kotlinbase.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import com.ekoo.haidaicrm.base.BaseActivity
import com.lvfq.kotlinbase.R
import com.lvfq.library.utils.IntentUtil

/**
 * BaseH5Activity
 * @author FaQiang on 2018/1/10 上午9:10
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
class BaseH5Activity : BaseActivity() {
    companion object {
        fun startH5(url: String) {
            val bund = Bundle()
            bund.putString("url", url)
            IntentUtil.startActivity(BaseH5Activity::class.java, bund)
        }
    }

    override fun getLayoutId(): Int {
        return 0
    }

    private val mWebView by lazy {
        WebView(this).apply {
            layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            setBackgroundColor(resources.getColor(R.color.white))
        }
    }

    private var url: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        url = intent.getStringExtra("url")

        setSetting(mWebView)
        mWebView.loadUrl(url)
        // 桥接 H5 调用 Android 本地
//        mWebView.addJavascriptInterface(H5InterAction(), "HDCRM")
        mWebView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)// 在当前的webview中跳转到新的url
                return true
            }
        }
    }

    override fun create(savedInstanceState: Bundle?) {

    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun setSetting(webView: WebView) {
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(false)  // 不支持缩放
        webView.settings.builtInZoomControls = false    // 设置内置的缩放控件
        webView.settings.displayZoomControls = false    //隐藏原生的缩放控件
        webView.settings.javaScriptCanOpenWindowsAutomatically = true   //支持通过JS打开新窗口

        //以下代码配置使用缓存 如果需要则可放开

//        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE   //不使用缓存

//        webView.settings.domStorageEnabled = true
        // Set cache size to 8 mb by default. should be more than enough
//        webView.settings.setAppCacheMaxSize(1024 * 1024 * 8)
// This next one is crazy. It's the DEFAULT location for your app's cache
// But it didn't work for me without this line.
// UPDATE: no hardcoded path. Thanks to Kevin Hawkins
//        val appCachePath = applicationContext.cacheDir.absolutePath
//        webView.settings.setAppCachePath(appCachePath)
//        webView.settings.allowFileAccess = true
//        webView.settings.setAppCacheEnabled(true)
        webView.setOnLongClickListener({ true })
    }


    override fun onDestroy() {
        mWebView.clearHistory()
        mWebView.destroy()
        super.onDestroy()
    }
}