package com.ekoo.haidaicrm.base

import android.app.ProgressDialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.annotation.UiThread
import android.support.v4.app.Fragment
import android.view.WindowManager
import com.lvfq.kotlinbase.base.IBase
import com.lvfq.library.utils.AppManager
import com.lvfq.library.utils.FragmentUtil
import com.lvfq.library.utils.ToastUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import org.greenrobot.eventbus.EventBus

/**
 * BaseActivity
 * @author lvfq
 * @Github: https://github.com/lvfaqiang
 * @Blog: http://blog.csdn.net/lv_fq
 * @date 2017/10/25 下午2:38
 * @desc :
 *
 */
open class BaseActivity : RxAppCompatActivity(), IBase {
    override fun getLayoutId(): Int {
        return 0
    }

    override fun init(savedInstanceState: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toastSuccess(string: String) {
    }

    override fun toastError(string: String) {

    }


    // 用于控制界面显示 隐藏 Fragment
    private var fragmentUtil: FragmentUtil? = null
    // 用于显示加载进度弹框
    protected var mProgress: ProgressDialog? = null
    // 用于申请权限
    protected var mPermissions: RxPermissions? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 禁止页面自动弹出输入法, or 禁止输入法顶起布局
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        // 设置页面始终竖屏展示
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        // 维护一个 Activity 栈
        AppManager.getAppManager().addActivity(this)

        if (getLayoutId() != 0) {
            setContentView(getLayoutId())
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // 初始化 Fragment 操作工具类
        fragmentUtil = FragmentUtil(supportFragmentManager)

        // 初始化权限控制类
        mPermissions = RxPermissions(this)
        mPermissions?.requestEach(android.Manifest.permission.ACCESS_CHECKIN_PROPERTIES)?.subscribe {
            if (it.granted) {
                // `permission.name` is granted ! - 允许
            } else if (it.shouldShowRequestPermissionRationale) {
                // Denied permission without ask never again    拒绝此次

            } else {
                // Denied permission with ask never again  - 拒绝，并且下次不再询问
                // Need to go to the settings

            }
        }

        if (useEventBus()) EventBus.getDefault().register(this)


        mProgress = ProgressDialog(this)
        mProgress?.setCanceledOnTouchOutside(false)
        mProgress?.setCancelable(true)
        mProgress?.setMessage("加载中...")

        init(savedInstanceState)

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
        ToastUtil.showToast(this, message)
    }

    /**
     * 显示当前 Fragmnet
     */
    override fun showFragment(fragment: Fragment, id: Int) {
        fragmentUtil?.showFragment(fragment, id)
    }

    /* -------------- 加载框 --------------*/

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


    override fun onDestroy() {
        if (useEventBus()) EventBus.getDefault().unregister(this)

        AppManager.getAppManager().finishActivity(this)

        disLoading()
        super.onDestroy()
    }
}