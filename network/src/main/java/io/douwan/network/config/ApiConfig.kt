package io.douwan.network.config

import io.douwan.network.NetworkProvider
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * ApiConfig
 * @author FaQiang on 2018/9/12 上午9:58
 * @desc :
 *
 */
class ApiConfig {
    private val TIME_OUT = 30L  // 默认请求时间 10s

    // 网络日志
    val logger = HttpLogIntercepter(object :
        HttpLogIntercepter.Logger {
        override fun log(message: String?) {
            NetworkProvider.get().apiLog.invoke(message)
        }
    }).apply {
        setLevel(HttpLogIntercepter.Level.BODY)
    }

    // 添加公共请求头
    val headerInterceptor = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .addHeader("client", "android")
            .build()
        chain.proceed(request)
    }

    fun configOkHttp(): OkHttpClient {
        val builder = OkHttpClient.Builder()
//        builder.addInterceptor(headerInterceptor)
        builder.addInterceptor(logger)  // 网络请求日志

        NetworkProvider.get().getApiInterceptors().map {
            builder.addInterceptor(it)
        }
        // 配置超时时间
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        builder.readTimeout(TIME_OUT, TimeUnit.SECONDS)
        builder.writeTimeout(TIME_OUT, TimeUnit.SECONDS)

        return builder.build()
    }
}