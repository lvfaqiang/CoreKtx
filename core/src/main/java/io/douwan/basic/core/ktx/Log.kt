package io.douwan.basic.core.ktx

import io.douwan.basic.core.LogUtil

/**
 * Log2022/4/30 16:51
 * @desc :
 *
 */
inline fun String.i(tag: String = "") {
    LogUtil.i(tag, this)
}

inline fun String.d(tag: String = "") {
    LogUtil.d(tag, this)
}

inline fun String.e(tag: String = "") {
    LogUtil.e(tag, this)
}

inline fun String.w(tag: String = "") {
    LogUtil.w(tag, this)
}