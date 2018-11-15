package com.lvfq.kotlinbase.utils.tool

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * GsonUtil
 * @author FaQiang on 2018/10/15 下午3:02
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
object GsonUtil {

    /**
     * 把一个json字符串变成对象
     *
     * @param json
     * @return
     */
    inline fun <reified T> parseJsonToBean(json: String): T? {
        val gson = Gson()
        var t: T? = null
        try {
            t = gson.fromJson(json, T::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("hashtech", "json转bean类异常：" + e.toString())
            return null
        }

        return t
    }


    /**
     * 把json字符串变成集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    fun <T> parseJsonToList(json: String): List<T>? {
        val gson = Gson()
        try {
            val t = gson.fromJson<List<T>>(json, object : TypeToken<List<T>>() {}.type)
            return t
        } catch (e: Exception) {
            return null
        }
    }

    /**
     * 把json字符串变成集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    fun <T> parseJsonToList(json: String, type: Type): T? {
        val gson = Gson()
        try {
            val t = gson.fromJson<T>(json, type)
            return t
        } catch (e: Exception) {
            return null
        }
    }

}