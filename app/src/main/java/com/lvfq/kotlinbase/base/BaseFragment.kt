package com.lvfq.kotlinbase.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.lvfq.kotlinbase.feature.BasicFragment
import com.lvfq.kotlinbase.utils.tool.KeyBoardUtils
import org.greenrobot.eventbus.EventBus

/**
 * BaseFragment2020-02-24 10:52
 * @desc :
 *
 */
abstract class BaseFragment<T : ViewBinding> : BasicFragment() {

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


}