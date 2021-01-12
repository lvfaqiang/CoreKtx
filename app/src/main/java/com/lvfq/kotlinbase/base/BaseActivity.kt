package com.lvfq.kotlinbase.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.lvfq.kotlinbase.utils.basic.StatusBarUtil
import com.lvfq.kotlinbase.utils.tool.KeyBoardUtils
import org.greenrobot.eventbus.EventBus

/**
 * BaseActivity2020-02-24 15:28
 * @desc :
 *
 */
abstract class BaseActivity<T : ViewBinding> : FragmentActivity() {

    abstract fun init(savedInstanceState: Bundle?)

    abstract fun bindingView(): T

    protected val binding by lazy {
        bindingView()
    }

    open var useEventBus: Boolean = false
        protected set


    override fun onCreate(savedInstanceState: Bundle?) {
        // 禁止页面自动弹出输入法, or 禁止输入法顶起布局
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        // 设置页面竖屏显示
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        KeyBoardUtils.setupUISoftKeyBoardHideSystem(
            window.decorView,
            viewGroupFocused()
        ) //点击空白区域关闭软键盘
    }

    /**
     * 如果页面自动弹出软键盘，则重写该方法返回 true
     */
    open fun viewGroupFocused(): Boolean {
        return false
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (useEventBus) {
            EventBus.getDefault().register(this)
        }

        init(savedInstanceState)

        initListener()
    }

    open fun initListener() {

    }

    protected fun applySinking(){
        //沉侵
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPadding(this, binding.root)
    }


    protected inline fun <reified T : BaseViewModel> findVM(): T {
        return VMFactory.findVM(this, T::class.java)
    }

    override fun onDestroy() {
        if (useEventBus) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
    }

}