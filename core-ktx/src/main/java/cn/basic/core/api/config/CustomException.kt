package cn.basic.core.api.config

/**
 * CustomException2020/3/1 6:00 PM
 * @desc :
 *
 */
class CustomException
    constructor(val code: String?, message: String?) : Exception(message) {

    constructor(code: Int, message: String?) : this(code.toString(), message)
}