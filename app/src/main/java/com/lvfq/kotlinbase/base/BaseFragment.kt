package com.lvfq.kotlinbase.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lvfq.kotlinbase.utils.tool.KeyBoardUtils
import org.greenrobot.eventbus.EventBus

/**
 * BaseFragment2020-02-24 10:52
 * @desc :
 *
 */
abstract class BaseFragment : Fragment() {

    abstract val layoutRes: Int

    abstract fun init(savedInstanceState: Bundle?)

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

        if (layoutRes != 0) {
            return inflater.inflate(layoutRes, container, false)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
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