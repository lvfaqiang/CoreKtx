package com.lvfq.kotlinbase.utils.basic

import android.content.Context
import android.content.res.Configuration
import java.util.*

/**
 * LanguageUtil2021/1/25 1:20 PM
 * @desc :
 *
 */
object LanguageUtil {

    object LanguageType {
        const val CHINESE = 0   // 中文
        const val TAIWAN = 1    //繁体
        const val ENGLISH = 2   //英语
        const val KOREAN = 3    // 韩语
        const val JAPANESE = 4  // 日语
        const val THAI = 5  // 泰语
        const val Malay = 6 // 马来语
    }

    fun updateLanguage(context: Context, index: Int) {
//        context.createConfigurationContext(getConfiguration(context, index))  // 代码不好使。
        val resources = context.resources
        val displayMetrics = resources.displayMetrics
        resources.updateConfiguration(
            getConfiguration(context, index),
            displayMetrics
        )
    }


    /**
     * 获取语言
     *
     * @param context
     * @param language
     */
    private fun getConfiguration(context: Context, language: Int): Configuration {
        val resources = context.resources
        val configuration = resources.configuration
        when (language) {
            0 -> configuration.setLocale(Locale.CHINESE)    // 中文
            1 -> configuration.setLocale(Locale.TAIWAN)     //繁体
            2 -> configuration.setLocale(Locale.ENGLISH)    //英语
            3 -> configuration.setLocale(Locale.KOREAN)     // 韩语
            4 -> configuration.setLocale(Locale.JAPANESE)   // 日语
            5 -> configuration.setLocale(Locale("th"))  // 泰语
//            6 -> configuration.setLocale(Locale("vi"))  //越南语
            6 -> configuration.setLocale(Locale("ms"))  // 马来语
            else -> configuration.setLocale(Locale.CHINESE)
        }
        return configuration
    }

}