package com.faqiang.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ktx.immersionBar
import io.douwan.basic.core.util.FragmentUtil
import org.greenrobot.eventbus.EventBus
import java.lang.reflect.ParameterizedType

/**
 * BaseVMFragment2022/4/15 00:23
 * @desc :
 *
 */
abstract class BaseVMFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {

    protected abstract val viewModelClass: Class<VM>

    protected lateinit var viewModel: VM

    protected val fragmentUtil by lazy {
        FragmentUtil(childFragmentManager)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    protected val binding get() = _binding!!

    private var _binding: VB? = null

    open fun useEventBus(): Boolean {
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }

        val type = javaClass.genericSuperclass
        val clazz = (type as ParameterizedType).actualTypeArguments[0] as Class<VB>
        val method = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        _binding = method.invoke(null, layoutInflater, container, false) as VB


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        viewModel.message.observe(viewLifecycleOwner) {
            toastError(it)
        }
        initView()
        initData()
        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * 设置沉浸式状态栏
     */
    protected fun setStatusBar(isDarkFont: Boolean = true, view: View) {
        immersionBar {
            statusBarDarkFont(isDarkFont, 0.2f)
            statusBarView(view)
        }
    }


    abstract fun initView()

    abstract fun initData()

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(viewModelClass)
    }

    protected inline fun <reified T : ViewModel> findVMByActivity(): T {
        return activity?.run {
            ViewModelProvider(this).get(T::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    open fun toastError(message: String?) {
        val parentActivity = activity
        if (parentActivity is BaseVMActivity<*, *>) {
            parentActivity.toastError(message)
        }
    }

    open fun toastError(@StringRes strRes: Int) {
        toastError(getString(strRes))
    }

    open fun toastSuccess(message: String?) {
        val parentActivity = activity
        if (parentActivity is BaseVMActivity<*, *>) {
            parentActivity.toastSuccess(message)
        }
    }

    open fun toastSuccess(@StringRes strRes: Int) {
        toastSuccess(getString(strRes))
    }

    open fun showLoading(isCancelable: Boolean = true) {
        val parentActivity = activity
        if (parentActivity is BaseVMActivity<*, *>) {
            parentActivity.showLoading(isCancelable)
        }
    }

    open fun disLoading() {
        val parentActivity = activity
        if (parentActivity is BaseVMActivity<*, *>) {
            parentActivity.disLoading()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }

    open fun onReload() {}
}