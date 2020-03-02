package com.lvfq.kotlinbase.entities.base

/**
 * BaseEntity2019/4/24 10:55 AM
 * @desc :
 *
 */
open class BaseEntity(
        var response_time: Int = 0,
        var response_code: String? = null,
        var response_message: String? = null,
        var token: String? = null
)