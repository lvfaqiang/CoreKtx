package com.lvfq.kotlinbase.kotlinx

import android.widget.TextView
import androidx.annotation.DrawableRes

/**
 * View2021/1/12 4:27 PM
 * @desc :
 *
 */
fun TextView.drawable(@DrawableRes res: Int?, @Position position: Int) {
    val drawable = if (res != null) resources.getDraw(res) else {
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

@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class Position {
    companion object {
        const val LEFT = 0
        const val TOP = 1
        const val RIGHT = 2
        const val BOTTOM = 3
    }
}