package com.lvfq.kotlinbase.ext

import android.os.Build
import android.util.TypedValue
import android.view.View
import com.lvfq.kotlinbase.kotlinx.getColorById
import com.lvfq.kotlinbase.utils.basic.DrawableBuilder

/**
 * View2019/4/22 5:06 PM
 * @desc :
 *
 */
inline fun View.setBackgroundDraw(cornerRadius: Float, solidColor: Int, strokeColor: Int = 0, strokeWidth: Float = 1f) {
    val bgDraw = DrawableBuilder(context).apply {
        setRadius(cornerRadius)
        setSolidColor(resources.getColorById(solidColor))
        if (strokeColor != 0) {
            setStrokColor(resources.getColorById(strokeColor))
        }
        setStrokeWidth(strokeWidth)
        setStrokeUnit(TypedValue.COMPLEX_UNIT_PX)
    }.build()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        background = bgDraw
    } else {
        setBackgroundDrawable(bgDraw)
    }
}
