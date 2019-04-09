package com.lvfq.kotlinbase.kotlinx

import android.content.Context

/**
 * Screen2019/4/9 10:29 PM
 * @desc :
 *
 */
inline val Context.screenHeight
    get() = this.resources.displayMetrics.heightPixels


inline val Context.screenWidth
    get() = this.resources.displayMetrics.widthPixels

