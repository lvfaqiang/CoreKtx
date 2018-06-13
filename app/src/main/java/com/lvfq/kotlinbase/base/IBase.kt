package com.lvfq.kotlinbase.base

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment

/**
 * IBase
 * @author FaQiang on 2018/6/3 上午10:08
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
interface IBase {

    // 初始化相关

    fun getLayoutId(): Int

    fun init(savedInstanceState: Bundle?)


    // 配置
    fun useEventBus(): Boolean

    fun showFragment(fragment: Fragment, @IdRes controllerId: Int)

    // 基础功能

    fun toastSuccess(string: String)

    fun toastError(string: String)

    fun toast(string: String)

    fun showLoading()

    fun disLoading()
}