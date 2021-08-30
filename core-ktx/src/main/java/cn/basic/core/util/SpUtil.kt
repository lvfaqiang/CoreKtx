package cn.basic.core.util

import android.content.Context
import android.content.SharedPreferences
import cn.basic.core.CoreKtxProvider

object SpUtil {
    private const val defaultSpName = "CoreKtx"

    fun cache(
        spName: String? = defaultSpName,
        block: SharedPreferences.Editor.() -> Unit
    ): Boolean {
        val sharedPre = CoreKtxProvider.mContext.getSharedPreferences(spName, Context.MODE_PRIVATE)
        return with(sharedPre?.edit()) {
            this?.apply(block)
            return this?.commit() ?: false
        }
    }

    fun get(spName: String? = defaultSpName): SharedPreferences? =
        CoreKtxProvider.mContext.applicationContext.let {
            return it.getSharedPreferences(spName, Context.MODE_PRIVATE)
        }

    fun clear(spName: String) {
        get(spName)?.let {
            val editor = it.edit()
            editor.clear()
            editor.commit()
        }
    }

    fun asyncCache(spName: String? = defaultSpName, block: SharedPreferences.Editor.() -> Unit) {
        val sharedPre = CoreKtxProvider.mContext.getSharedPreferences(spName, Context.MODE_PRIVATE)
        return with(sharedPre?.edit()) {
            this?.apply(block)
            this?.apply()
        }
    }
}