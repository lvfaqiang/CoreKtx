package com.lvfq.kotlinbase.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.observe
import androidx.viewbinding.ViewBinding
import com.lvfq.kotlinbase.cache.AppCache
import com.lvfq.kotlinbase.factory.VMFactory
import com.lvfq.kotlinbase.utils.basic.LanguageUtil
import com.lvfq.kotlinbase.utils.basic.StatusBarUtil
import com.lvfq.kotlinbase.utils.tool.KeyBoardUtils
import com.lvfq.kotlinbase.views.LoadingView
import org.greenrobot.eventbus.EventBus

/**
 * BaseVMActivity2020-02-24 15:39
 * @desc :
 *
 */
abstract class BaseVMActivity<T : ViewBinding, VM : BaseViewModel> : FragmentActivity() {

    abstract fun bindingView(): T

    abstract fun init(savedInstanceState: Bundle?)

    protected abstract val viewModelClass: Class<VM>

    protected lateinit var vM: VM

    private val loadingView by lazy {
        LoadingView.get(this)
    }

    private fun initViewModel() {
        vM = VMFactory.findVM(this, viewModelClass)
    }

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

        LanguageUtil.updateLanguage(this, AppCache.getLanguage())    // 默认中文

        initViewModel()

        loadingObserve()

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

    private fun loadingObserve() {
        vM.updateMessage.observe(this) {
            if (loadingView.isShowing()) {
                loadingView.setMessage(it)
            }
        }
        vM.loadingState.observe(this) {
            if (it.loading) {
                loadingView.setCancelable(it.cancelable)
                loadingView.setCanceledOnTouchOutside(it.cancelable)

                if (it.message.isNotEmpty()) {
                    loadingView.setMessage(it.message)
                }

                if (!loadingView.isShowing()) {
                    loadingView.show()
                }

            } else {
                loadingView.dismiss()
            }
        }
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

    protected fun applySinking() {
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