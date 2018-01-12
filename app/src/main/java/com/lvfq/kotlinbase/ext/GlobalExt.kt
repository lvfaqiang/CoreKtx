package com.lvfq.kotlinbase.ext

import android.arch.lifecycle.ViewModel
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.lvfq.kotlinbase.utils.ViewModelFactory

/**
 * Ext
 * @author FaQiang on 2018/1/4 上午9:47
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
/**
 * 扩展函数，获取ViewModel
 */
fun FragmentActivity.getModel(context: Any, clazz: Class<ViewModel>): ViewModel {
    return ViewModelFactory.getModel(context, clazz)
}

fun Fragment.getModel(context: Any, clazz: Class<ViewModel>): ViewModel {
    return ViewModelFactory.getModel(context, clazz)
}

