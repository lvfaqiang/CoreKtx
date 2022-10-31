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

    fun isBeforeToday(dateTime: DateTime): Boolean {
        val curDate = DateTime.now().toString("yyyy.MM.dd")
        val date = dateTime.toString("yyyy.MM.dd")
        if (date == curDate) {
            return false
        }
        if (DateTime.parse(date, DateTimeFormat.forPattern("yyyy.MM.dd")).millis > DateTime.parse(
                curDate,
                DateTimeFormat.forPattern("yyyy.MM.dd")
            ).millis
        ) {
            return true
        }
        return false
    }

    fun isAfterToday(dateTime: DateTime): Boolean {
        val curDate = DateTime.now().toString("yyyy.MM.dd")
        val date = dateTime.toString("yyyy.MM.dd")
        if (date == curDate) {
            return false
        }
        if (DateTime.parse(date, DateTimeFormat.forPattern("yyyy.MM.dd")).millis > DateTime.parse(
                curDate,
                DateTimeFormat.forPattern("yyyy.MM.dd")
            ).millis
        ) {
            return true
        }
        return false
    }


    fun isToday(dateTime: DateTime): Boolean {
        val curDate = DateTime.now().toString("yyyy.MM.dd")
        val date = dateTime.toString("yyyy.MM.dd")
        return date == curDate
    }
}