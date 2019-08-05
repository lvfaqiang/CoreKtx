package com.lvfq.kotlinbase.base

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import com.lvfq.kotlinbase.R
import com.lvfq.kotlinbase.kotlinx.activity.fullScreen
import com.lvfq.kotlinbase.utils.basic.FragmentUtil
import com.lvfq.kotlinbase.utils.basic.LogUtil
import com.lvfq.kotlinbase.utils.basic.ScreenUtil
import com.lvfq.kotlinbase.utils.basic.ToastUtil
import com.lvfq.kotlinbase.views.ILoading
import com.lvfq.kotlinbase.views.LoadingView
import com.tbruyelle.rxpermissions2.RxPermissions
import org.greenrobot.eventbus.EventBus

/**
 * BaseActivity
 * @author FaQiang on 2018/9/25 下午3:05
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
abstract class BaseActivity : AppCompatActivity(), ISimpleBase {

    var isOnResumed = false
        private set

    var mIsFirst = true
        private set

    protected val mPermissions: RxPermissions by lazy {
        RxPermissions(this)
    }

    protected val mFragmentUtil: FragmentUtil by lazy {
        FragmentUtil(supportFragmentManager)
    }

    private var mLoadingView: ILoading? = null

    /**
     *  横屏 or 竖屏  ， default 竖屏
     */
    protected open fun isLandScape(): Boolean = false

    override fun isFirst(): Boolean {
        return mIsFirst
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        // 禁止页面自动弹出输入法, or 禁止输入法顶起布局
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        // 设置页面始终竖屏展示
        requestedOrientation = if (isLandScape()) {
            fullScreen()
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        super.onCreate(savedInstanceState)

        mLoadingView = LoadingView(this)

        initStatusBar()
    }

    private fun initStatusBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        if (getLayoutId() > 0) {
            if (isFullScreen()) {
                setContentView(getLayoutId())
            } else {
                val mRootLayout = LayoutInflater.from(this).inflate(R.layout.root_layout, null, false) as LinearLayout
                val mStatusBarView = mRootLayout.getChildAt(0)
                val statusBarLayoutParams = mStatusBarView.layoutParams
                statusBarLayoutParams.height = ScreenUtil.getStatusBarHeight(this)
                mStatusBarView.layoutParams = statusBarLayoutParams

                LayoutInflater.from(this).inflate(getLayoutId(), mRootLayout, true)
                setContentView(mRootLayout)
            }
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        if (useEventBus()) EventBus.getDefault().register(this)

        initUI(savedInstanceState)

        initListener()

        initData(savedInstanceState)
    }

    override fun onResume() {
        if (mIsFirst) {
            mIsFirst = false
        }
        super.onResume()
        isOnResumed = true
    }

    override fun onPause() {
        super.onPause()
        isOnResumed = false
    }

    override fun onStop() {
        super.onStop()
    }

    // -------------
    override fun showLoading() {
        LogUtil.i("loading", "showloading -------")
        mLoadingView?.let { loading ->
            loading.takeIf {
                !loading.isShowing() && !isOnResumed
            }?.let {
                loading.show()
            }
        }
    }

    override fun disLoading() {
        LogUtil.i("loading", "disLoading -------")
        mLoadingView?.dismiss()
    }

    override fun toastSuc(message: String) {
        if (isOnResumed) {
            ToastUtil.showToast(this, message)
        }
    }

    override fun toastSuc(strId: Int) {
        if (isOnResumed) {
            ToastUtil.showToast(this, getString(strId))
        }
    }

    override fun toastFailed(message: String) {
        if (isOnResumed) {
            ToastUtil.showToast(this, message)
        }
    }

    override fun toastFailed(strId: Int) {
        if (isOnResumed) {
            ToastUtil.showToast(this, getString(strId))
        }
    }

    override fun getContext(): Context? = this

    override fun getLifecycleOwner(): LifecycleOwner = this

// -------------

    abstract fun getLayoutId(): Int

    abstract fun initUI(savedInstanceState: Bundle?)

    abstract fun initData(savedInstanceState: Bundle?)

    abstract fun initListener()

    fun isFullScreen(): Boolean {
        return false
    }

}