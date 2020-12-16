package com.lvfq.kotlinbase.utils.tool

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * Decimal
 * @desc :
 *
 */
object Decimal {

    fun add(v1: String?, v2: String?): String {
        val result = checkValue(v1, v2)
        return result[0].add(result[1]).toPlainString()
    }

    fun sub(v1: String?, v2: String?): String {
        val result = checkValue(v1, v2)
        return result[0].subtract(result[1]).toPlainString()
    }

    fun mul(v1: String?, v2: String?): String {
        val result = checkValue(v1, v2)
        return result[0].multiply(result[1]).toPlainString()
    }

    fun div(v1: String?, v2: String?, scale: Int = 10): String {
        val result = checkValue(v1, v2)
        if (result[0] == BigDecimal("0") || result[1] == BigDecimal("0")) {
            return "0"
        } else {
            return result[0].divide(result[1], scale, RoundingMode.DOWN).toPlainString()
        }
    }

    // 以 value1 为参照，大于0 则表示 value1 大， 小于0 则 value1 小，等于0 则两个数相等。
    fun compareTo(value1: String?, value2: String?): Int {
        val result = checkValue(value1, value2)
        return result[0].compareTo(result[1])
    }

    private val checkValue = { v1: String?, v2: String? ->
        val value1 = if (!v1.isNullOrEmpty()) v1!! else "0"
        val value2 = if (!v2.isNullOrEmpty()) v2!! else "0"
        try {
            arrayOf(BigDecimal(value1), BigDecimal(value2))
        } catch (e: Exception) {
            arrayOf(BigDecimal("0"), BigDecimal("0"))
        }
    }

    /**
     * 保留两位整数，不够往前补零
     */
    fun keepTwoNumber(value: Long): String {
        if (value < 10) {
            return "0$value"
        } else {
            return value.toString()
        }
    }


    /**
     * 最多保留小数点后两位，自动舍0（四舍五入）
     *
     * @param number
     * @return
     */
    fun maxKeepTwoDecimal(number: String?): String {
        val df = DecimalFormat("##.##")
        return df.format(BigDecimal(checkNumberValue(number)))
    }

    /**
     * 最多保留小数点后两位(超出部分截取)
     *
     * @param number
     * @return
     */
    fun maxKeepTwoDecimalDown(number: String?): String {
        val df = DecimalFormat("##.##")
        return df.format(BigDecimal(checkNumberValue(number)).setScale(2, RoundingMode.DOWN))
    }

    /**
     * 始终保留小数点后两位（超出四舍五入）
     *
     * @param number
     * @return
     */
    fun keepTwoDecimal(number: String?): String {
        val df = DecimalFormat("#0.00")
        return df.format(BigDecimal(checkNumberValue(number)))
    }

    /**
     * 始终保留小数点后两位（超出部分截取）
     *
     * @param number
     * @return
     */
    fun keepTwoDecimalDown(number: String?): String {
        val num = checkNumberValue(number)
        val df = DecimalFormat("#0.00")
        return df.format(BigDecimal(num).setScale(2, RoundingMode.DOWN))
    }

    /**
     * 最多保存小数点后count位，自动舍0（超出四舍五入）
     *
     * @param number
     * @param count
     * @return
     */
    fun maxKeepDecimal(number: String?, count: Int): String {
        return optionDecimal(number, count, "#", -1)
    }

    /**
     * 最多保存小数点后count位，自动舍0（超出截取）
     *
     * @param number
     * @param count
     * @return
     */
    fun maxKeepDecimalDown(number: String?, count: Int): String {
        return optionDecimal(number, count, "#", BigDecimal.ROUND_DOWN)
    }

    /**
     * 保存小数点后count位（超出四舍五入）
     *
     * @param number
     * @param count
     * @return
     */
    fun keepDecimal(number: String?, count: Int): String {
        return optionDecimal(number, count, "0", -1)
    }

    /**
     * 保存小数点后count位（超出截取）
     *
     * @param number
     * @param count
     * @return
     */
    fun keepDecimalDown(number: String?, count: Int): String {
        return optionDecimal(number, count, "0", BigDecimal.ROUND_DOWN)
    }

    /**
     *保留小数位数区间（四舍五入）
     */
    fun keepDecimalRange(value: String?, start: Int, end: Int): String? {
        val result = maxKeepDecimal(value, end)
        if (result.contains(".")) {
            val arrays = result.split(".")
            if (arrays[1].length >= start) {
                return result
            } else {
                return keepDecimal(result, start)
            }
        } else {
            return keepDecimal(result, start)
        }
    }

    /**
     *保留小数位数区间 (超出截取）
     */
    fun keepDecimalRangeDown(value: String?, start: Int, end: Int): String? {
        val result = maxKeepDecimalDown(value, end)
        if (result.contains(".")) {
            val arrays = result.split(".")
            if (arrays[1].length >= start) {
                return result
            } else {
                return keepDecimalDown(result, start)
            }
        } else {
            return keepDecimalDown(result, start)
        }
    }


    private fun optionDecimal(number: String?, count: Int, symbol: String, mode: Int): String {
        val num = checkNumberValue(number)
        if (count < 0) {
            return "0"
        }
        if (count == 0) {
            try {
                return Integer.parseInt(num?.toDouble()?.toInt().toString()).toString()
            } catch (e: Exception) {
                return "0"
            }
        }
        val stringBuilder = StringBuilder("#0.")
        for (i in 0 until count) {
            stringBuilder.append(symbol)
        }
        val df = DecimalFormat(stringBuilder.toString())
        val bigDecimal: BigDecimal
        if (mode != -1) {
            bigDecimal = BigDecimal(num).setScale(count, mode)
        } else {
            bigDecimal = BigDecimal(num)
        }
        return df.format(bigDecimal)
    }

    private fun checkNumberValue(number: String?): String? {
        if (number.isNullOrEmpty()) {
            return "0"
        } else {
            return number
        }
    }

}


