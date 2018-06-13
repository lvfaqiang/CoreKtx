package com.lvfq.kotlinbase.http

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * ApiClient
 * @author lvfq
 * @Github: https://github.com/lvfaqiang
 * @Blog: http://blog.csdn.net/lv_fq
 * @date 2017/12/21 下午4:04
 * @desc :
 *
 */
class ApiClient {
    companion object {
        fun getService(): ApiService {
            return SingletonHolder.INSTANCE.getService()
        }
    }

    private object SingletonHolder {
        internal val INSTANCE = ApiClient()
    }


    private lateinit var service: ApiService

    fun getService(): ApiService {
        service = Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(ApiConfig().configClient())
                .build()
                .create(ApiService::class.java)
        return service
    }

}