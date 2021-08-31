package cn.basic.core.ktx.view

import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import cn.basic.core.ktx.launchUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

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
    this?.setOnClickListener(l)
}

/**
 * 控制View 重复点击事件
 * @param interval 单位 毫秒(ms)
 * @param l 触发事件
 */
fun View.clickInterval(interval: Long = 1000, l: (View) -> Unit = {}) {
    var lastClickTime = 0L
    this.setOnClickListener {
        val curTime = System.currentTimeMillis()
        if (curTime - lastClickTime > interval) {
            l(it)
            lastClickTime = System.currentTimeMillis()
        }
    }
}

fun onClicksInterval(interval: Long = 1000, vararg v: View, l: (View) -> Unit = {}) {
    v.forEach {
        it.clickInterval(interval, l)
    }
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
 * 控制 View 单次点击 和双次点击
 */
fun View.clickOnceOrDouble(
    interval: Long = 500,
    onceClick: (View) -> Unit = {},
    doubleClick: (View) -> Unit = {}
) {
    var preClickTime = 0L
    var doubleClicked = false
    this.setOnClickListener {
        val curTime = System.currentTimeMillis()
        if (curTime - preClickTime < interval) {
            doubleClick(it)
            preClickTime = 0L
            doubleClicked = true
        } else {
            launchUI {
                val a = withContext(Dispatchers.Default) {
                    delay(interval)
                    true
                }
                if (a && !doubleClicked) {
                    onceClick(it)
                } else {
                    doubleClicked = false
                }
            }
        }
        preClickTime = System.currentTimeMillis()
    }
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