package com.lvfq.kotlinbase.base

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lvfq.kotlinbase.utils.basic.FragmentUtil
import com.lvfq.kotlinbase.utils.basic.ToastUtil
import com.lvfq.kotlinbase.views.ILoading
import com.lvfq.kotlinbase.views.LoadingView
import com.tbruyelle.rxpermissions2.RxPermissions
import org.greenrobot.eventbus.EventBus

/**
 * BaseFragment
 * @author FaQiang on 2018/8/28 下午3:40
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
abstract class BaseFragment : Fragment(), ISimpleBase {

    protected val mPerissions: RxPermissions by lazy {
        RxPermissions(this)
    }

    protected val mFragmentUtil: FragmentUtil by lazy {
        FragmentUtil(childFragmentManager)
    }

    private var mLoadingView: ILoading? = null

    protected var mContentView: View? = null
    private var mIsFirst = true
        private set

    override fun isFirst(): Boolean {
        return mIsFirst
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mLoadingView = LoadingView(this.requireContext())
        mLoadingView?.setOnDismissListener(DialogInterface.OnDismissListener {
        })

        if (getLayoutId() != 0 && mContentView == null) {
            mContentView = inflater.inflate(getLayoutId(), container, false)
            return mContentView
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        initUI(savedInstanceState)
        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData(savedInstanceState)
        if (mIsFirst) {
            mIsFirst = false
        }
    }

    override fun onDestroyView() {
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroyView()
    }

    override fun showLoading() {
        mLoadingView?.let { loading ->
            loading.takeIf {
                !loading.isShowing() && !isResumed
            }?.let {
                loading.show()
            }
        }
    }

    override fun disLoading() {
        mLoadingView?.dismiss()
    }

    override fun toastSuc(message: String) {
        if (isResumed) {
            ToastUtil.showToast(context, message)
        }
    }

    override fun toastSuc(strId: Int) {
        if (isResumed) {
            ToastUtil.showToast(context, getString(strId))
        }
    }

    override fun toastFailed(message: String) {
        if (isResumed) {
            ToastUtil.showToast(context, message)
        }
    }

    override fun toastFailed(strId: Int) {
        if (isResumed) {
            ToastUtil.showToast(context, getString(strId))
        }
    }

    override fun getLifecycleOwner(): LifecycleOwner = this


    override fun getContext(): Context? = super.getContext()


    abstract fun getLayoutId(): Int

    abstract fun initUI(savedInstanceState: Bundle?)

    abstract fun initData(savedInstanceState: Bundle?)

    abstract fun initListener()


}