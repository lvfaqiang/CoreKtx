package com.ekoo.haidaicrm.base

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.UiThread
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lvfq.kotlinbase.App
import com.lvfq.kotlinbase.base.IBase
import com.lvfq.library.utils.FragmentUtil
import com.lvfq.library.utils.ToastUtil
import com.trello.rxlifecycle2.components.support.RxFragment
import dagger.android.support.AndroidSupportInjection
import org.greenrobot.eventbus.EventBus


/**
 * BaseFragment
 * @author lvfq
 * @Github: https://github.com/lvfaqiang
 * @Blog: http://blog.csdn.net/lv_fq
 * @date 2017/10/25 下午3:16
 * @desc :
 *
 */
abstract class BaseFragment : RxFragment(), IBase {


    override fun toastSuccess(string: String) {
    }

    override fun toastError(string: String) {

    }


    /* -----------------抽象方法------------------*/


    /**
     * 是否使用 dagger
     */
    protected open fun isUseDagger(): Boolean = true

    //-------------------------

    // 用于控制界面显示 隐藏 Fragment
    private var fragmentUtil: FragmentUtil? = null

    protected var mProgress: ProgressDialog? = null


    override fun onAttach(activity: Activity?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // Perform injection here before M, L (API 22) and below because onAttach(Context)
            // is not yet available at L.
            if (isUseDagger()) AndroidSupportInjection.inject(this)
        }
        super.onAttach(activity)
    }

    override fun onAttach(context: Context?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Perform injection here for M (API 23) due to deprecation of onAttach(Activity).
            if (isUseDagger()) AndroidSupportInjection.inject(this)
        }
        super.onAttach(context)
    }


    /**
     * 创建视图，布局中的控件在 onCreateView 之后的生命周期方法中可直接使用 id
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layoutId = getLayoutId()
        if (layoutId > 0) {
            return inflater.inflate(layoutId, container, false)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 注册 EventBus ，如果需要把此方法返回值改为 true
        if (useEventBus()) EventBus.getDefault().register(this)
        fragmentUtil = FragmentUtil(childFragmentManager)

    }


    /**
     * 是否使用 EventBus
     */
    override fun useEventBus(): Boolean = false

    /**
     * Toast 提示
     */
    @UiThread
    override fun toast(message: String) {
        ToastUtil.showToast(activity, message)
    }

    override fun showLoading() {
        mProgress?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    override fun disLoading() {
        mProgress?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }

    /**
     * 显示 Fragment
     */
    override fun showFragment(fragment: Fragment, id: Int) {
        fragmentUtil?.showFragment(fragment, id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }


    override fun onDestroy() {

        if (useEventBus())
            EventBus.getDefault().unregister(this)

        // 监测内存泄漏
        if (activity != null) {
            val refWatcher = App.getRefWatcher(activity!!)
            refWatcher.watch(this)
        }
        super.onDestroy()
    }
}