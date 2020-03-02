package com.lvfq.kotlinbase.api.net

/**
 * CustomException2020/3/1 6:00 PM
 * @desc :
 *
 */
class CustomException(val code: Int, message: String?) : Throwable(message)