package com.lvfq.kotlinbase.base

import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.lvfq.kotlinbase.utils.tool.KeyBoardUtils
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


    override fun onCreate(savedInstanceState: Bundle?) {
        // 禁止页面自动弹出输入法, or 禁止输入法顶起布局
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        super.onCreate(savedInstanceState)
        if (layoutRes != 0) {
            setContentView(layoutRes)
        }
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

    override fun onDestroy() {
        if (useEventBus) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
    }

}