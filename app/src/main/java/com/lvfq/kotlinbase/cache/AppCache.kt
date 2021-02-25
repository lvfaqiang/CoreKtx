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
     * 需要切换语言直接调用当前方法，
     * 然后重启 App 即可。
     */
    fun updateLanguage(context: Context, index: Int) {
        LocalCacheUtil.put(language, index)
        LanguageUtil.updateLanguage(context, index)
    }

    /**
     *  获取当前App选择的语言
     */
    fun getLanguage(): Int {
        return LocalCacheUtil.get(language, LanguageUtil.LanguageType.CHINESE)
    }

}