package com.lvfq.kotlinbase.utils.basic

import android.app.Activity
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.annotation.StyleRes

/**
 * PopupWindowBuilder
 * @author FaQiang on 2018/10/6 上午3:21
 * @desc :
 *
 */
class PopupWindowBuilder private constructor(private val context: Context) {
    private var width = ViewGroup.LayoutParams.WRAP_CONTENT
    private var height = ViewGroup.LayoutParams.WRAP_CONTENT
    private var mOutsideTouchable = true
    private var mTouchable = true
    private var curActivity: Activity? = null

    @StyleRes
    private var animStyle = -1


    fun setWidth(width: Int): PopupWindowBuilder {
        this.width = width
        return this
    }

    fun setHeight(height: Int):PopupWindowBuilder {
        this.height = height
        return this
    }

    fun setOutsideTouchable(mOutsideTouchable: Boolean): PopupWindowBuilder {
        this.mOutsideTouchable = mOutsideTouchable
        return this
    }

    fun setTouchable(mTouchable: Boolean): PopupWindowBuilder {
        this.mTouchable = mTouchable
        return this
    }

    fun setAnimStyle(animStyle: Int): PopupWindowBuilder {
        this.animStyle = animStyle
        return this
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    fun backgroundAlpha(
        activity: Activity,
        bgAlpha: Float
    ): PopupWindowBuilder {
        curActivity = activity
        val lp = activity.window.attributes
        lp.alpha = bgAlpha // 0.0-1.0
        if (bgAlpha == 1f) {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
        activity.window.attributes = lp
        return this
    }

    fun build(view: View): PopupWindow {
        return buildPopwindow(view)
    }

    private fun buildPopwindow(view: View): PopupWindow {
        val popupWindow = PopupWindow()
        popupWindow.contentView = view
        popupWindow.width = width
        popupWindow.height = height
        popupWindow.setBackgroundDrawable(BitmapDrawable())
        popupWindow.isOutsideTouchable = mOutsideTouchable
        popupWindow.isTouchable = mTouchable
        if (animStyle != -1) {
            popupWindow.animationStyle = animStyle
        }
        popupWindow.isFocusable = true
        popupWindow.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE

        curActivity?.let {
            // 注意不要被覆盖了，
            popupWindow.setOnDismissListener {
                backgroundAlpha(
                    it,
                    1f
                )
            }
        }
        return popupWindow
    }

    companion object {
        operator fun get(context: Context): PopupWindowBuilder {
            return PopupWindowBuilder(context)
        }
    }

}