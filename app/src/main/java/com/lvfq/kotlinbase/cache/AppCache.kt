package com.lvfq.kotlinbase.cache

import android.content.Context
import com.lvfq.kotlinbase.utils.basic.LanguageUtil
import com.lvfq.kotlinbase.utils.basic.LocalCacheUtil

/**
 * AppCache2021/1/12 11:41 AM
 * @desc :
 *
 */
object AppCache {
    private const val language = "Language"


    /**
     * index  传参  LanguageUtil.LanguageType
     */
    fun updateLanguage(context: Context, index: Int) {
        LocalCacheUtil.put(language, index)
    }

    fun getLanguage(): Int {
        return LocalCacheUtil.get(language, LanguageUtil.LanguageType.CHINESE)
    }

}