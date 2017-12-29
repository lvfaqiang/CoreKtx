package com.lvfq.kotlinbase.base

import android.support.v4.app.Fragment

/**
 * IBaseUI
 * @author FaQiang on 2017/12/23 下午10:15
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
interface IBaseUI {

    fun useEventBus(): Boolean = false

    fun toast(message: String)

    fun showFragment(fragment: Fragment, layoutId: Int)

    fun showProgress()

    fun dismissProgress()
}