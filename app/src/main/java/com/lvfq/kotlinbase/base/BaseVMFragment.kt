package com.lvfq.kotlinbase.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.lvfq.kotlinbase.factory.VMFactory
import com.lvfq.kotlinbase.utils.tool.KeyBoardUtils
import org.greenrobot.eventbus.EventBus

/**
 * BaseVMFragment2020-02-24 16:54
 * @desc :
 *
 */
abstract class BaseVMFragment<T : ViewBinding, VM : ViewModel> : Fragment() {
    protected abstract val viewModelClass: Class<VM>

    protected lateinit var vM: VM

    private fun initViewModel() {
        vM = VMFactory.findVM(this, viewModelClass)
    }

    abstract fun init(savedInstanceState: Bundle?)

    protected abstract fun bindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): T

    protected lateinit var binding: T

    open var useEventBus: Boolean = false
        protected set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        view?.let {
            it.isClickable = true
        }
        KeyBoardUtils.setupUISoftKeyBoardHideSystem(view ?: return, viewGroupFocused())
        super.onActivityCreated(savedInstanceState)
    }

    /**
     * 如果页面自动弹出软键盘，则重写该方法返回 true
     */
    open fun viewGroupFocused(): Boolean {
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingView(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        if (useEventBus) {
            EventBus.getDefault().register(this)
        }
        init(savedInstanceState)
    }

    override fun onDestroyView() {
        if (useEventBus) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroyView()
    }

    protected inline fun <reified T : ViewModel> findVM(): T {
        return VMFactory.findVM(this, T::class.java)
    }

    protected inline fun <reified T : ViewModel> findVMByActivity(): T {
        return activity?.run {
            VMFactory.findVM(this, T::class.java)
        } ?: throw Exception("Invalid Activity")
    }


}