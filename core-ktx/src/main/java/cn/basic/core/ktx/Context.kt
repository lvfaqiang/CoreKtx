package cn.basic.core.ktx

import android.content.Context
import android.content.pm.PackageInfo
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.DisplayMetrics
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 * Context2019-12-30 18:14
 * @desc :
 *
 */

inline val Context.appDisplayMetrics: DisplayMetrics
    get() = this.applicationContext.resources.displayMetrics


// PackageInfo

inline val Context.packageInfo: PackageInfo?
    get() = packageManager.getPackageInfo(packageName, 0)

/**
 * Value of App version name.
 */
inline val Context.versionName: String?
    get() = packageInfo?.versionName

/**
 * Value of App version Code.
 */
inline val Context.versionCode: Long
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        packageInfo?.longVersionCode ?: -1L
    } else {
        (packageInfo?.versionCode ?: 0) as Long
    }


/**
 * Value of dp to value of px.
 *
 * @param dpValue The value of dp.
 * @return value of px
 */
fun Context.dp2px(dpValue: Float): Int {
    val scale = this.appDisplayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * Value of px to value of dp.
 *
 * @param pxValue The value of px.
 * @return value of dp
 */
fun Context.px2dp(pxValue: Float): Int {
    val scale = this.appDisplayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}


/**
 * Value of sp to value of px.
 *
 * @param spValue The value of sp.
 * @return value of px
 */
fun Context.sp2px(spValue: Float): Int {
    val fontScale = this.appDisplayMetrics.density
    return (spValue * fontScale + 0.5f).toInt()
}

/**
 * Value of px to value of sp.
 *
 * @param pxValue The value of px.
 * @return value of sp
 */
fun Context.px2sp(pxValue: Float): Int {
    val fontScale = this.appDisplayMetrics.density
    return (pxValue / fontScale + 0.5f).toInt()
}

fun Context.getColorById(@ColorRes id: Int): Int {
    return ContextCompat.getColor(this, id)
}

fun Context.getDrawableById(@DrawableRes id: Int): Drawable? {
    return ContextCompat.getDrawable(this, id)
}

/**
 * Value of Screen height
 */
inline val Context.screenHeight
    get() = this.resources.displayMetrics.heightPixels


/**
 * Value of Screen width
 */
inline val Context.screenWidth
    get() = this.resources.displayMetrics.widthPixels

