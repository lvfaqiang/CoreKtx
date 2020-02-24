package com.lvfq.kotlinbase.utils.tool

import com.lvfq.kotlinbase.utils.basic.LogUtil
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * TimeConverts
 * @author FaQiang on 2018/9/30 上午10:17
 * @desc :
 *
 */
object TimeConverts {
    const val DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val DEFAULT_FORMAT_SSS = "yyyy-MM-dd HH:mm:ss.SSS"

    const val UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val UTC_FORMAT_SSS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"


    /**
     * utc 时间转换本地时间
     * @param utcTime utc 时间字符串
     * @param localTimePatten 转换的结果类型
     * @param utcFormat utc 时间字符串的格式
     * @return 返回转换后的 localTimePatten 格式时间
     */
    fun utc2Local(utcTime: String?, localTimePatten: String = DEFAULT_FORMAT, utcFormat: String = UTC_FORMAT): String {
        val time = utc2LocalTime(utcTime, utcFormat)
        val localFormater = SimpleDateFormat(localTimePatten, Locale.getDefault())
        localFormater.timeZone = TimeZone.getDefault()
        return localFormater.format(time)
    }

    /**
     * utc 时间转换成时间戳
     * @param utcTime utc 时间字符串
     * @param utcFormat utc 时间字符串的格式
     * @return 返回转换后的时间戳
     */
    fun utc2LocalTime(utcTime: String?, utcFormat: String = UTC_FORMAT): Long {
        if (utcTime == null || utcTime.isEmpty()) {
            return 0
        }
        val utcFormater = SimpleDateFormat(utcFormat, Locale.getDefault())
        utcFormater.timeZone = TimeZone.getTimeZone("UTC")//时区定义并进行时间获取
        val gpsUTCDate: Date?
        try {
            LogUtil.i("utc mill : " + utcFormater.parse(utcTime).time)
            gpsUTCDate = utcFormater.parse(utcTime)
        } catch (e: ParseException) {
            e.printStackTrace()
            return 0
        }
        return gpsUTCDate?.time ?: 0
    }

    fun format(date: String?, format: String = DEFAULT_FORMAT): Long {
        if (date.isNullOrEmpty()) {
            return 0L
        }
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.parse(date).time
    }

    fun format(date: Long, format: String): String? {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(Date(date))
    }

    /**
     * 获取当前年份
     */
    fun getCurYear(): Int {
        return Calendar.getInstance().get(Calendar.YEAR)
    }

    /**
     * 获取当前月份
     */
    fun getCurMonth(): Int {
        return Calendar.getInstance().get(Calendar.MONTH) + 1
    }
}