package cn.basic.core.ktx.view

import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.annotation.DrawableRes

/**
 * TextView2021/1/14 2:22 AM
 * @desc :
 *
 */
fun TextView.drawable(
    @DrawableRes leftRes: Int? = null,
    @DrawableRes topRes: Int? = null,
    @DrawableRes rightRes: Int? = null,
    @DrawableRes bottomRes: Int? = null
) {
    val ld = if (leftRes != null) context.getDrawable(leftRes) else null
    val td = if (topRes != null) context.getDrawable(topRes) else null
    val rd = if (rightRes != null) context.getDrawable(rightRes) else null
    val bd = if (bottomRes != null) context.getDrawable(bottomRes) else null


    ld?.let { it.setBounds(0, 0, it.minimumWidth, it.minimumHeight) }
    td?.let { it.setBounds(0, 0, it.minimumWidth, it.minimumHeight) }
    rd?.let { it.setBounds(0, 0, it.minimumWidth, it.minimumHeight) }
    bd?.let { it.setBounds(0, 0, it.minimumWidth, it.minimumHeight) }

    setCompoundDrawables(ld, td, rd, bd)
}


/**
 * 设置TextView图标
 *
 * @param drawable      图标
 * @param iconWidth     图标宽dp：默认自动根据图标大小
 * @param iconHeight    图标高dp：默认自动根据图标大小
 * @param direction     图标方向，0左 1上 2右 3下 默认图标位于左侧0
 */
fun TextView.setDrawable(
    drawable: Drawable?,
    iconWidth: Int? = null,
    iconHeight: Int? = null,
    direction: Int = 0
) {
    if (iconWidth != null && iconHeight != null) {
        //第一个0是距左边距离，第二个0是距上边距离，iconWidth、iconHeight 分别是长宽
        drawable?.setBounds(0, 0, iconWidth, iconHeight)
    }

    when (direction) {
        0 -> setCompoundDrawables(drawable, null, null, null)
        1 -> setCompoundDrawables(null, drawable, null, null)
        2 -> setCompoundDrawables(null, null, drawable, null)
        3 -> setCompoundDrawables(null, null, null, drawable)
        else -> throw NoSuchMethodError()
    }
}