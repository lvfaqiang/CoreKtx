package cn.basic.core.ktx.view

import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible

/**
 * View2021/1/12 4:27 PM
 * @desc :
 *
 */

/***
 * 简化点击事件的View
 * @param block: (T) -> Unit 函数
 * @return Unit
 */
fun View?.click(l: (View) -> Unit = {}) {
    this?.setOnClickListener { l(it) }
}


/**
 * 批量设置View点击事件
 *
 * @param listener 按钮事件监听类
 * @param v 控件
 * @use #onClicks(vararg v: View, listener: View.OnClickListener)
 */
@Deprecated("@use #onClicks(vararg v: View, listener: View.OnClickListener)")
fun setOnClickListener(listener: View.OnClickListener, vararg v: View) {
    v.forEach { it.setOnClickListener(listener) }
}

/**
 * 批量设置View点击事件
 *
 * @param v 控件
 * @param listener 按钮事件监听类
 */
fun onClicks(vararg v: View, listener: View.OnClickListener) {
    v.forEach { it.setOnClickListener(listener) }
}

/**
 * 批量显示view
 */
fun visibles(vararg views: View?) {
    for (view in views) {
        view?.isVisible = true
    }
}

/**
 * 批量隐藏view
 */
fun Any.gones(vararg views: View?) {
    for (view in views) {
        view?.isVisible = false
    }
}

/**
 * 批量占位隐藏view
 */
fun Any.invisibles(vararg views: View?) {
    for (view in views) {
        view?.isInvisible = true
    }
}