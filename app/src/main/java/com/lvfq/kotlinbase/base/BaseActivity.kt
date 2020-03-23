package com.lvfq.kotlinbase.base

import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import org.greenrobot.eventbus.EventBus

/**
 * BaseActivity2020-02-24 15:28
 * @desc :
 *
 */
abstract class BaseActivity : FragmentActivity() {

    abstract val layoutRes: Int

    abstract fun init(savedInstanceState: Bundle?)

    open var useEventBus: Boolean = false
        protected set


    protected val mPerissions: RxPermissions by lazy {
        RxPermissions(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // 禁止页面自动弹出输入法, or 禁止输入法顶起布局
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        super.onCreate(savedInstanceState)
        if (layoutRes != 0) {
            setContentView(layoutRes)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (useEventBus) {
            EventBus.getDefault().register(this)
        }

        init(savedInstanceState)

    }

    override fun onDestroy() {
        if (useEventBus) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
    }

}