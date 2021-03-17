package cn.basic.core.api.demo

import cn.basic.core.api.ApiClient

/**
 * DemoReq
 * @author FaQiang on 2018/11/14 下午3:53
 * @desc :
 *
 */
class DemoReq private constructor() {
    private val apiService = ApiClient.create(DemoApiService::class.java)


    companion object {
        fun get(): DemoReq {
            return Holder.INSTANCE
        }
    }

    private object Holder {
        val INSTANCE = DemoReq()
    }

    fun getService(): DemoApiService {
        return apiService
    }


    // ... Request Method


}