package com.lvfq.kotlinbase.builder

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import androidx.annotation.StyleRes
import com.lvfq.kotlinbase.R

/**
 * DialogBuilder2021/1/12 4:58 PM
 * @desc :
 *
 */
class DialogBuilder private constructor(val mContext: Context) {

    private var isCancelable = true
    private var isCanceledOnTouchOutside = true
    private var mGravity = -1
    private var mWidth = 0
    private var mHeight = 0
    private var mWidthScale = 0.86f
    private var mHeightScale = 0f

    @StyleRes
    private val styleRes = R.style.style_loading_light_dialog

    fun setWidth(width: Int): DialogBuilder {
        mWidth = width
        return this
    }

    fun setWidth(width: Float): DialogBuilder {
        mWidthScale = width
        return this
    }

    fun setHeight(height: Int): DialogBuilder {
        mHeight = height
        return this
    }

    fun setHeight(height: Float): DialogBuilder {
        mHeightScale = height
        return this
    }

    fun setGravity(gravity: Int): DialogBuilder {
        mGravity = gravity
        return this
    }

    fun setCancelable(cancelable: Boolean): DialogBuilder {
        isCancelable = cancelable
        return this
    }

    fun setCanceledOnTouchOutside(canceledOnTouchOutside: Boolean): DialogBuilder {
        isCanceledOnTouchOutside = canceledOnTouchOutside
        return this
    }

    fun build(view: View): Dialog {
        val dialog = Dialog(mContext, styleRes)
        dialog.setContentView(view)
        dialog.setCancelable(isCancelable)
        dialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
        val window = dialog.window!!
        if (mGravity != -1) {
            window.setGravity(mGravity)
        }
        val display = (mContext as Activity).windowManager.defaultDisplay
        val windowAttr = window.attributes
        if (mWidthScale != 0f) {
            windowAttr.width = (display.width * mWidthScale).toInt()
        } else {
            if (mWidth != 0) {
                windowAttr.width = mWidth
            }
        }
        if (mHeightScale != 0f) {
            windowAttr.height = (display.height * mHeightScale).toInt()
        } else {
            if (mHeight != 0) {
                windowAttr.height = mHeight
            }
        }
        window.attributes = windowAttr
        dialog.show()
        return dialog
    }

    companion object {
        operator fun get(context: Context): DialogBuilder {
            return DialogBuilder(context)
        }
    }

}