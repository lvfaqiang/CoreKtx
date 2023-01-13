package com.faqiang.core.api

import io.douwan.network.NetworkProvider
import io.douwan.network.client.NetWorkApiService
import io.douwan.network.config.ApiConfig
import io.douwan.network.config.NullOrEmptyConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * ApiClient
 * @author FaQiang on 2018/11/14 下午3:24
 * @desc :
 *
 */
object ApiClient {

    private var _apiService: ApiService? = null
    private var retrofit: Retrofit? = null

    val apiService: ApiService
        get() {
            if (_apiService == null) {
                synchronized(ApiClient::class) {
                    if (_apiService == null) {
                        _apiService = create(ApiService::class.java)
                    }
                }
            }
            return _apiService!!
        }

    private val mRetrofit: Retrofit
        get() {
            if (retrofit == null) {
                synchronized(ApiClient::class) {
                    if (retrofit == null) {
                        retrofit = initRetrofit()
                    }
                }
            }
            return retrofit!!
        }

    private fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkProvider.get().baseUrl)
            .addConverterFactory(NullOrEmptyConverterFactory())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiConfig().configOkHttp())
            .build()
    }

    fun <T> create(klass: Class<T>): T = mRetrofit.create(klass)


    fun resetDomain() {
        retrofit = null
        _apiService = null
    }
}