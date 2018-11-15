package com.lvfq.kotlinbase.ext.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager

/**
 * Activity
 * @author FaQiang on 2018/8/29 下午4:15
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *  reified 真泛型必须配合 inline 关键字使用，
 *  crossinline 表明 如果在 lambda 表达式中 进行 return ，不会影响 lambda 之外的后续代码执行。
 */

inline fun <reified T : Activity> Activity.startActivity(requestCode: Int = -1, crossinline action: Intent.() -> Unit = {}) {
    startActivityForResult(
            Intent(this, T::class.java)
                    .apply {
                        action.invoke(this)
                    }
            , requestCode)
}

inline fun Activity.startAction(intentAction: String = "", requestCode: Int = -1, crossinline action: Intent.() -> Unit = {}) {
    startActivityForResult(
            (takeIf { !intentAction.isEmpty() }?.let { return@let Intent(intentAction) }
                    ?: Intent())
                    .apply {
                        action.invoke(this)
                    }
            , requestCode)
}

fun AppCompatActivity.fullScreen() {
    supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
}




