package com.lvfq.kotlinbase.api.req

import com.lvfq.kotlinbase.api.net.ApiClient

/**
 * DemoReq
 * @author FaQiang on 2018/11/14 下午3:53
 * @desc :
 *
 */
class DemoReq private constructor() {
    private val apiService = ApiClient.getService()


    companion object {
        fun get(): DemoReq {
            return Holder.INSTANCE
        }
    }

    private object Holder {
        val INSTANCE = DemoReq()
    }


    // ... Request Method


}