package cn.basic.core.ktx

import cn.basic.core.util.Decimal

/**
 * StringKtx2022/5/17 10:49
 * @desc :
 *
 */
inline fun String.add(value: String?): String {
    return Decimal.add(this, value)
}

inline fun String.sub(value: String?): String {
    return Decimal.sub(this, value)
}

inline fun String.mul(value: String?): String {
    return Decimal.mul(this, value)
}

inline fun String.div(value: String?): String {
    return Decimal.div(this, value)
}

inline fun String.compare(value: String?): Int {
    return Decimal.compareTo(this, value)
}

inline fun String.keepToInt(defaultValue: Int = 0): Int {
    try {
        return this.maxKeepTwoDown().toDouble().toInt()
    } catch (e: Exception) {
        return defaultValue
    }
}

inline fun String.between(value1: String?, value2: String?): Boolean {
    return this.compare(value1) > 0 && this.compare(value2) < 0
}

inline fun String.betweenStart(value1: String?, value2: String?): Boolean {
    return this.compare(value1) >= 0 && this.compare(value2) < 0
}

inline fun String.betweenEnd(value1: String?, value2: String?): Boolean {
    return this.compare(value1) > 0 && this.compare(value2) <= 0
}

inline fun String.betweenAnd(value1: String?, value2: String?): Boolean {
    return this.compare(value1) >= 0 && this.compare(value2) <= 0
}

inline fun String.maxKeep(count: Int): String {
    return Decimal.maxKeepDecimal(this, count)
}

inline fun String.maxKeepDown(count: Int): String {
    return Decimal.maxKeepDecimalDown(this, count)
}

inline fun String.keep(count: Int): String {
    return Decimal.keepDecimal(this, count)
}

inline fun String.keepDown(count: Int): String {
    return Decimal.keepDecimalDown(this, count)
}

inline fun String.maxKeepTwoDown(): String {
    return Decimal.maxKeepTwoDecimalDown(this)
}

inline fun String.keepTwoDown(): String {
    return Decimal.keepTwoDecimal(this)
}