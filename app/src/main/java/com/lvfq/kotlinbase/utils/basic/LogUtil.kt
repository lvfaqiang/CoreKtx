package com.lvfq.kotlinbase.utils.basic

import android.util.Log
import com.lvfq.kotlinbase.BuildConfig

/**
 * LogUtil
 * @author FaQiang on 2018/9/28 上午11:42
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
object LogUtil {

    private var logOff = BuildConfig.DEBUG

    /**
     * 开关
     */
    fun setLogEnable(flag: Boolean?) {
        logOff = flag ?: logOff
    }

    fun i(msg: String?) {
        i("", msg)
    }

    fun i(tag: String, msg: String?) {
        if (logOff) {
            Log.i(generateTempTag(tag), msg)
        }
    }

    fun e(msg: String?) {
        e("", msg)
    }

    fun e(tag: String, msg: String?) {
        if (logOff) {
            Log.e(generateTempTag(tag), msg)
        }
    }

    fun d(msg: String?) {
        d("", msg)
    }

    fun d(tag: String, msg: String?) {
        if (logOff) {
            Log.d(generateTempTag(tag), msg)
        }
    }

    fun v(msg: String) {
        v("", msg)
    }

    fun v(tag: String, msg: String?) {
        if (logOff) {
            Log.v(generateTempTag(tag), msg)
        }
    }

    fun w(tag: String, msg: String?) {
        if (logOff) {
            Log.w(generateTempTag(tag), msg)
        }
    }

    fun w(msg: String?) {
        w("", msg)
    }


    private fun generateTempTag(tag: String): String {
        return if (tag.isEmpty())
            getDefaultTag()
        else
            "${getDefaultTag()}, $tag"
    }

    private fun getDefaultTag(): String {
//        val element = Throwable().stackTrace[3] // 这里的下标 表示的层级， 当前方法体为 0 ，当前类中调用方法 1，外部调用该输出方法层级为 2 等等。

        val elements = Throwable().stackTrace
        var element: StackTraceElement? = null
        for (i in 2 until elements.size) {
            if (elements[i].className == this.javaClass.name) {
                element = elements[i + 1]
            }
        }
        element?.let {
            return "(${it.fileName}:${it.lineNumber})->${it.methodName}"
        }

        return ""
    }
}
