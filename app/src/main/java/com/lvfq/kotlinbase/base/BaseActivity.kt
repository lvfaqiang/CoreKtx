package com.lvfq.kotlinbase.base

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.tbruyelle.rxpermissions2.RxPermissions
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


    protected val mPerissions: RxPermissions by lazy {
        RxPermissions(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (useEventBus) {
            EventBus.getDefault().register(this)
        }

        init(savedInstanceState)

    }

    override fun onDestroy() {
        if (useEventBus) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
    }

}