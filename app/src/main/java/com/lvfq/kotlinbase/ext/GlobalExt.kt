package com.lvfq.kotlinbase.ext

import android.arch.lifecycle.ViewModel
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.TextView
import com.lvfq.kotlinbase.utils.ViewModelFactory

/**
 * Ext
 * @author FaQiang on 2018/1/4 上午9:47
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */

fun <T : View> T.hide() {
    this.visibility = View.INVISIBLE
}

fun <T : View> T.show() {
    this.visibility = View.VISIBLE
}

fun <T : View> T.gone() {
    this.visibility = View.GONE
}

fun <T : View> T.toggleHide() {
    this.visibility = if (this.visibility == View.VISIBLE) {
        View.INVISIBLE
    } else {
        View.VISIBLE
    }
}

/**
 * 设置 TextView 显示图标
 */
fun TextView.draw(@DrawableRes res: Int?, position: Position) {
    val drawable = if (res != null) resources.getDrawable(res) else {
        null
    }
    drawable?.let {
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    }
    when (position) {
        Position.LEFT -> {
            setCompoundDrawables(drawable, null, null, null)
        }
        Position.RIGHT -> {
            setCompoundDrawables(null, null, drawable, null)
        }
        Position.TOP -> {
            setCompoundDrawables(null, drawable, null, null)
        }
        Position.BOTTOM -> {
            setCompoundDrawables(null, null, null, drawable)
        }
    }
}

enum class Position {
    LEFT, RIGHT, TOP, BOTTOM
}


/**
 * 扩展函数，获取ViewModel
 */
fun FragmentActivity.getModel(context: Any, clazz: Class<ViewModel>): ViewModel {
    return ViewModelFactory.getModel(context, clazz)
}

fun Fragment.getModel(context: Any, clazz: Class<ViewModel>): ViewModel {
    return ViewModelFactory.getModel(context, clazz)
}




