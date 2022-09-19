package io.douwan.basic.core.util

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import java.util.*

/**
 * LanguageUtil2021/1/25 1:20 PM
 * @desc :
 *
 */
object LanguageUtil {

    fun setDefaultLanguage(context: Context, language: String = "en") {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config: Configuration = context.resources.configuration
        val metrics = context.resources.displayMetrics
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            config.setLocales(localeList)
            context.applicationContext.createConfigurationContext(config)
            Locale.setDefault(locale)
        } else {
            config.locale = locale
        }
        context.resources.updateConfiguration(config, metrics)
    }

}