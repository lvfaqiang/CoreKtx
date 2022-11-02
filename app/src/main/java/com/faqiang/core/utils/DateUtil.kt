package com.faqiang.core.utils

import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import org.joda.time.format.DateTimeFormat

/**
 * DateUtil2022/10/31 15:19
 * @desc :
 *
 */
object DateUtil {
    fun convertDate(dateValue: String, curFormat: String, targetFormat: String): String {
        return DateTime.parse(dateValue, DateTimeFormat.forPattern(curFormat))
            .toString(targetFormat)
    }

    fun getWeek(date: String): String {
        return when (DateTime(date).dayOfWeek) {
            DateTimeConstants.MONDAY -> {
                "星期-"
            }
            DateTimeConstants.TUESDAY -> {
                "星期二"
            }
            DateTimeConstants.WEDNESDAY -> {
                "星期三"
            }
            DateTimeConstants.THURSDAY -> {
                "星期四"
            }
            DateTimeConstants.FRIDAY -> {
                "星期五"
            }
            DateTimeConstants.SATURDAY -> {
                "星期六"
            }
            DateTimeConstants.SUNDAY -> {
                "星期天"
            }
            else -> {
                return ""
            }
        }
    }

    fun startDayOfMonth(dateTime: DateTime? = null): String {
        val date = dateTime ?: DateTime()
        val firstDay = date.dayOfMonth().minimumValue
        return "${date.year}-${date.monthOfYear}-$firstDay"
    }

    fun endDayOfMonth(dateTime: DateTime? = null): String {
        val date = dateTime ?: DateTime()
        val endOfDay = date.dayOfMonth().maximumValue
        return "${date.year}-${date.monthOfYear}-$endOfDay"
    }

    fun isBeforeToday(dateTimeStr: String, targetFormat: String = "yyyy-MM-dd"): Boolean {
        try {
            val dateTime = DateTime.parse(dateTimeStr, DateTimeFormat.forPattern(targetFormat))
            val curDate = DateTime.now().toString(targetFormat)
            val date = dateTime.toString(targetFormat)
            if (date == curDate) {
                return false
            }
            if (DateTime.parse(
                    date,
                    DateTimeFormat.forPattern(targetFormat)
                ).millis < DateTime.parse(
                    curDate,
                    DateTimeFormat.forPattern(targetFormat)
                ).millis
            ) {
                return true
            }
        } catch (e: Exception) {
        }
        return false
    }

    fun isAfterToday(dateTimeStr: String, targetFormat: String = "yyyy-MM-dd"): Boolean {
        try {
            val dateTime = DateTime.parse(dateTimeStr, DateTimeFormat.forPattern(targetFormat))
            val curDate = DateTime.now().toString(targetFormat)
            val date = dateTime.toString(targetFormat)
            if (date == curDate) {
                return false
            }
            if (DateTime.parse(
                    date,
                    DateTimeFormat.forPattern(targetFormat)
                ).millis > DateTime.parse(
                    curDate,
                    DateTimeFormat.forPattern(targetFormat)
                ).millis
            ) {
                return true
            }
        } catch (e: Exception) {
        }
        return false
    }


    fun isToday(dateTimeStr: String, targetFormat: String = "yyyy-MM-dd"): Boolean {
        try {
            val dateTime = DateTime.parse(dateTimeStr, DateTimeFormat.forPattern(targetFormat))
            val curDate = DateTime.now().toString(targetFormat)
            val date = dateTime.toString(targetFormat)
            return date == curDate
        } catch (e: Exception) {
        }
        return false
    }
}