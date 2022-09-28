package com.faqiang.core.utils

import android.content.pm.PackageManager
import android.os.Parcelable
import com.faqiang.core.MyApp
import com.tencent.mmkv.MMKV

/**
 * MMKVUtil2022/7/5 11:56
 * @desc :
 *
 */
object MMKVUtil {

    private val mmkv: MMKV
        get() {
            try {
                val context = MyApp.mContext
                val appInfo = context.packageManager.getApplicationInfo(
                    context.packageName,
                    PackageManager.GET_META_DATA
                )
                val cryptKey = appInfo.metaData.getString("MMKV_secret", "RunBloxKey")
                val mmapID = appInfo.metaData.getString("MMKV_mmapId", "RunBloxMapID")
                return MMKV.mmkvWithID(mmapID, MMKV.SINGLE_PROCESS_MODE, cryptKey)
            } catch (e: Exception) {
                return MMKV.defaultMMKV()
            }
        }

    fun putString(key: String, value: String?) {
        mmkv.encode(key, value)
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return mmkv.decodeString(key, defaultValue) ?: defaultValue
    }

    fun putInt(key: String, value: Int) {
        mmkv.encode(key, value)
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return mmkv.decodeInt(key, defaultValue)
    }

    fun putBool(key: String, value: Boolean) {
        mmkv.encode(key, value)
    }

    fun getBool(key: String, defaultValue: Boolean = false): Boolean {
        return mmkv.decodeBool(key, defaultValue)
    }

    fun putFloat(key: String, value: Float) {
        mmkv.encode(key, value)
    }

    fun getFloat(key: String, defaultValue: Float = 0.0F): Float {
        return mmkv.decodeFloat(key, defaultValue)
    }

    fun putDouble(key: String, value: Double) {
        mmkv.encode(key, value)
    }

    fun getDouble(key: String, defaultValue: Double = 0.0): Double {
        return mmkv.decodeDouble(key, defaultValue)
    }

    fun putParcelable(key: String, value: Parcelable) {
        mmkv.encode(key, value)
    }
}