package com.lvfq.kotlinbase.api.net

/**
 * CustomException2020/3/1 6:00 PM
 * @desc :
 *
 */
class CustomException
    constructor(val code: String?, message: String?) : Throwable(message) {

    constructor(code: Int, message: String?) : this(code.toString(), message)
}