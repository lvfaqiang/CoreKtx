package cn.basic.core.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.observe
import androidx.viewbinding.ViewBinding
import cn.basic.core.common.ILoading
import cn.basic.core.factory.VMFactory
import cn.basic.core.util.KeyBoardUtils
import cn.basic.core.util.StatusBarUtil

/**
 * BaseVMActivity2020-02-24 15:39
 * @desc :
 *
 */
abstract class BasicVMActivity<T : ViewBinding, VM : BasicViewModel> : FragmentActivity() {

    protected abstract val viewModelClass: Class<VM>

    abstract fun bindingView(): T

    abstract fun init(savedInstanceState: Bundle?)

    protected lateinit var vM: VM

    abstract val loadingView: ILoading

    private fun initViewModel() {
        vM = VMFactory.findVM(this, viewModelClass)
    }

    protected val binding by lazy {
        bindingView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // 禁止页面自动弹出输入法, or 禁止输入法顶起布局
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        // 设置页面竖屏显示
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        super.onCreate(savedInstanceState)

//        LanguageUtil.updateLanguage(this, AppCache.getLanguage())    // 默认中文

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
                if (loadingView.isShowing()) {
                    loadingView.dismiss()
                }
            }
        }
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        init(savedInstanceState)

        initListener()
    }

    open fun initListener() {

    }

    protected fun applySinking(view: View = binding.root) {
        //沉侵
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPadding(this, view)
    }


    protected inline fun <reified T : BasicViewModel> findVM(): T {
        return VMFactory.findVM(this, T::class.java)
    }

}