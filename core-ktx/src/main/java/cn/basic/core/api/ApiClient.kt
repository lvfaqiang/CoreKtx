package cn.basic.core.api

import cn.basic.core.CoreKtxProvider
import cn.basic.core.api.config.ApiConfig
import cn.basic.core.api.config.NullOrEmptyConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * ApiClient
 * @author FaQiang on 2018/11/14 下午3:24
 * @desc :
 *
 */
object ApiClient {

    private val mRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(CoreKtxProvider.get().baseUrl)
            .addConverterFactory(NullOrEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiConfig().configOkHttp())
            .build()
    }

    fun <T> create(klass: Class<T>): T = mRetrofit.create(klass)
}