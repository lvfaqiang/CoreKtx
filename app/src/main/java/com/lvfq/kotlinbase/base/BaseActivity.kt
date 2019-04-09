package com.lvfq.kotlinbase.base

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.lvfq.kotlinbase.ext.activity.fullScreen
import com.lvfq.kotlinbase.utils.basic.FragmentUtil
import com.lvfq.kotlinbase.utils.basic.LogUtil
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

    var isResumed = false
        private set

    var isFirst = true
        private set

    protected val mPermissions: RxPermissions by lazy {
        RxPermissions(this)
    }

    protected val mFragmentUtil: FragmentUtil by lazy {
        FragmentUtil(supportFragmentManager)
    }

    private var mLoadingView: ILoading? = null
    private var loadingCount: Int = 0

    /**
     *  横屏 or 竖屏  ， default 竖屏
     */
    protected open fun isLandScape(): Boolean = false


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
        mLoadingView?.setOnDismissListener(DialogInterface.OnDismissListener {
            if (loadingCount != 0) {
                loadingCount = 0
            }
        })

        if (getLayoutId() != 0) {
            setContentView(getLayoutId())
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
        if (isFirst) {
            isFirst = false
        }
        super.onResume()
        isResumed = true
    }

    override fun onPause() {
        super.onPause()
        isResumed = false
    }

    override fun onStop() {
        super.onStop()
    }

    // -------------
    override fun showLoading() {
        LogUtil.i("loading", "showloading -------")
        mLoadingView?.let { loading ->
            loadingCount++
            loading.takeIf {
                !loading.isShowing() && !isResumed
            }?.let {
                loading.show()
            }
        }
    }

    override fun disLoading() {
        LogUtil.i("loading", "disLoading -------")
        if (mLoadingView != null) {
            if (loadingCount > 0) {
                loadingCount--
            }
            takeIf {
                mLoadingView?.isShowing() == true
            }?.let {
                if (loadingCount == 0) {
                    mLoadingView?.dismiss()
                }
            }
        } else {
            loadingCount = 0
        }
    }

    override fun toastSuc(message: String) {
        if (isResumed) {
            ToastUtil.showToast(this, message)
        }
    }

    override fun toastSuc(strId: Int) {
        if (isResumed) {
            ToastUtil.showToast(this, getString(strId))
        }
    }

    override fun toastFailed(message: String) {
        if (isResumed) {
            ToastUtil.showToast(this, message)
        }
    }

    override fun toastFailed(strId: Int) {
        if (isResumed) {
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

}