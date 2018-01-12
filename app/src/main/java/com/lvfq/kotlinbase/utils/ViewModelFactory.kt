package com.lvfq.kotlinbase.utils

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

/**
 * ViewModelFactory
 * @author FaQiang on 2018/1/10 上午9:53
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
class ViewModelFactory {

    companion object {
        fun getModel(context: Any, clazz: Class<ViewModel>): ViewModel {
            return when (context) {
                is Fragment -> {
                    ViewModelProviders.of(context).get(clazz)
                }
                is FragmentActivity -> {
                    ViewModelProviders.of(context).get(clazz)
                }
                else -> throw RuntimeException("the context is not instanceof v4.Fragment or FragmentActivity")
            }

        }
    }
}