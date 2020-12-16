package com.lvfq.kotlinbase.kotlinx

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

/**
 * Resource2019-12-30 18:11
 * @desc :
 *
 */
inline fun Resources.getDraw(@DrawableRes id: Int): Drawable? {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        return getDrawable(id, null)
    } else {
        return getDrawable(id)
    }
}

inline fun Resources.getColorById(@ColorRes id: Int): Int {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        return getColor(id, null)
    } else {
        return getColor(id)
    }
}