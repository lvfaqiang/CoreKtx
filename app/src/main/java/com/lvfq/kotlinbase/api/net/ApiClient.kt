package com.lvfq.kotlinbase.api.net

import com.lvfq.kotlinbase.BuildConfig
import com.lvfq.kotlinbase.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * ApiClient
 * @author FaQiang on 2018/11/14 下午3:24
 * @desc :
 *
 */
object ApiClient {

    const val API_COMMON = BuildConfig.BASE_URL

    private val mRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(API_COMMON)
            .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(ApiConfig().configOkHttp())
            .build()
    }

    private val mApiService: ApiService by lazy {
        create(ApiService::class.java)
    }

    fun getService(): ApiService {
        return mApiService
    }


    fun <T> create(klass: Class<T>): T = mRetrofit.create(klass)
}