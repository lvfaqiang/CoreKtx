package com.lvfq.kotlinbase.feature.h5

import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.fq.library.kotlin.ex.startActivity
import com.lvfq.kotlinbase.AppLifecycle
import com.lvfq.kotlinbase.base.old.BaseActivity_old

/**
 * H5Activity
 * @author FaQiang on 2018/11/8 下午2:44
 * @desc :
 *
 */
class H5Activity : BaseActivity_old() {

    override fun getLayoutId(): Int = 0

    companion object {
        fun start(url: String, title: String) {
            AppLifecycle.latestActivity()?.startActivity<H5Activity> {
                putExtra("url", url)
                putExtra("title", title)
            }
        }
    }

    private val webView by lazy {
        WebView(this).apply {
            settings.javaScriptEnabled = true
            settings.setSupportZoom(false)  // 不支持缩放
            settings.builtInZoomControls = false    // 设置内置的缩放控件
            settings.displayZoomControls = false    //隐藏原生的缩放控件
            settings.javaScriptCanOpenWindowsAutomatically = true   //支持通过JS打开新窗口

            webChromeClient = WebChromeClient()
            isHorizontalScrollBarEnabled = false//水平不显示
            isVerticalScrollBarEnabled = false //垂直不显示
            requestFocus()
            this.setOnLongClickListener { true }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(webView)
    }

    override fun initUI(savedInstanceState: Bundle?) {
        val url = intent.getStringExtra("url") ?: ""
        val title = intent.getStringExtra("title") ?: ""

        showLoading()   // pageStarted 中会执行多次加载网页数据，但是 finished 只会执行一次

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, urlValue: String) {
                disLoading()
                super.onPageFinished(view, urlValue)
            }

            override fun onPageStarted(view: WebView, urlValue: String, favicon: Bitmap?) {
                super.onPageStarted(view, urlValue, favicon)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        webView.loadUrl(url)
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initListener() {

    }


    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            finish()
        }
    }

    override fun onDestroy() {
        webView.removeAllViews()
        webView.clearHistory()
        webView.destroy()
        super.onDestroy()
    }
}