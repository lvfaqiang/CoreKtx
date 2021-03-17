package cn.basic.core.config

import android.content.Context
import android.content.SharedPreferences
import com.orhanobut.hawk.Hawk
import com.orhanobut.hawk.Storage

/**
 * HawkStorage
 * @author FaQiang on 2018/9/28 上午8:36
 * @desc :
 *
 */

object HawkConfig {

    fun init(context: Context, spName: String) {
        Hawk.init(context)
            .setStorage(
                HawkStorage(
                    context,
                    spName
                )
            )
            .build()
    }
}


private class HawkStorage(context: Context, spName: String) : Storage {

    private var preferences: SharedPreferences =
        context.getSharedPreferences(spName, Context.MODE_PRIVATE)

    override fun <T> put(key: String, value: T?): Boolean {
        return value?.let { getEditor().putString(key, value.toString()).commit() } ?: false
    }

    override fun <T> get(key: String): T? {
        return preferences.getString(key, null) as T?
    }

    override fun delete(key: String): Boolean {
        return getEditor().remove(key).commit()
    }

    override fun contains(key: String): Boolean {
        return preferences.contains(key)
    }

    override fun deleteAll(): Boolean {
        return getEditor().clear().commit()
    }

    override fun count(): Long {
        return preferences.all.size.toLong()
    }

    private fun getEditor(): SharedPreferences.Editor {
        return preferences.edit()
    }
}