package com.lvfq.kotlinbase.kotlinx

import android.app.Activity
import android.content.Intent

/**
 * Activity2019-12-30 18:13
 * @desc :
 *  reified 真泛型必须配合 inline 关键字使用，
 *  crossinline 表明 如果在 lambda 表达式中 进行 return ，不会影响 lambda 之外的后续代码执行。
 */
inline fun <reified T : Activity> Activity.startActivity(
    requestCode: Int = -1,
    crossinline action: Intent.() -> Unit = {}
) {
    startActivityForResult(
        Intent(this, T::class.java)
            .apply {
                action.invoke(this)
            }
        , requestCode)
}

inline fun Activity.startAction(
    intentAction: String = "",
    requestCode: Int = -1,
    crossinline action: Intent.() -> Unit = {}
) {
    startActivityForResult(
        (takeIf { !intentAction.isEmpty() }?.let { return@let Intent(intentAction) }
            ?: Intent())
            .apply {
                action.invoke(this)
            }
        , requestCode)
}