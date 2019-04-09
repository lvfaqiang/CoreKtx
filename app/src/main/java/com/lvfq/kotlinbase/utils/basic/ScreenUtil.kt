package com.lvfq.kotlinbase.utils.basic

import android.app.Activity


/**
 * ScreenUtil2019/4/9 10:34 PM
 * @desc :
 *
 */
object ScreenUtil {

    /**
     * 获取状态栏高度
     */
    fun getStatusBarHeight(activity: Activity): Int {
        var result = 0
        val resourceId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = activity.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

}