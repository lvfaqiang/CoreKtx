package com.lvfq.kotlinbase.utils.basic

import android.content.res.Resources


/**
 * ScreenUtil2019/4/9 10:34 PM
 * @desc :
 *
 */
object ScreenUtil {

    /**
     * 获取状态栏高度
     */
    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = Resources.getSystem().getDimensionPixelSize(resourceId)
        }
        return result
    }

}