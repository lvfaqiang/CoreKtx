package com.lvfq.kotlinbase.utils

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lvfq.library.utils.SpUtil.getString
import com.lvfq.library.utils.SpUtil.setString

/**
 * SpUtil
 * @author lvfq
 * @Github: https://github.com/lvfaqiang
 * @Blog: http://blog.csdn.net/lv_fq
 * @date 2017/12/21 上午12:50
 * @desc : 保存，获取 （对象、集合、列表）
 *
 */
object SpUtil {
    /**
     * 保存对象（所有类型）
     *
     * @param key
     * @param t
     * @param <T>
    </T> */
    fun <T> setT(key: String, t: T) {
        setString(key, Gson().toJson(t))
    }


    /**
     * 获取保存的对象
     *
     * @param key
     * @param tClass
     * @param <T>
     * @return
    </T> */
    fun <T> getT(key: String, tClass: Class<T>): T? {
        var t: T? = null
        val str = getString(key, "")
        if (!TextUtils.isEmpty(str)) {
            val go = Gson()
            t = go.fromJson<T>(str, tClass)
        }
        return t
    }

    /**
     * 获取保存的对象,支持所有类型对象。
     * (主要获取集合的时候使用)
     *
     * @param key
     * @param type
     * @return
     */
    fun <T> getT(key: String, type: TypeToken<T>): T? {
        var o: T? = null
        val str = getString(key, "")
        if (!TextUtils.isEmpty(str)) {
            val gson = Gson()
            o = gson.fromJson(str, type.getType())
        }
        return o
    }
}
