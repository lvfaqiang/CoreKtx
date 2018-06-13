package com.lvfq.kotlinbase.http

import com.lvfq.library.utils.LvLog
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * ApiClient
 * @author lvfq
 * @Github: https://github.com/lvfaqiang
 * @Blog: http://blog.csdn.net/lv_fq
 * @date 2017/12/21 下午4:04
 * @desc :
 *
 */
class ApiConfig {
    private val TIME_OUT = 10L  // 源码内部默认也是 10s

    fun configClient(): OkHttpClient {
        val client = OkHttpClient().newBuilder()

        client.interceptors().add(Interceptor { chain ->
            val request = chain.request()

//            if (UserExt.isLogined()) {
//                return@Interceptor chain.proceed(request.newBuilder().header("Authorization", UserExt.getToken()).build());
//            }
            LvLog.i("ApiConfig", chain.request().url().newBuilder().build().url().toString())
            return@Interceptor chain.proceed(request)
        })
        /**
         * 添加全局参数
         */
//        client.interceptors().add(Interceptor { chain ->
//            val request = chain.request()
//            val url = request.url().newBuilder().addQueryParameter("platformType", "ANDROID").addQueryParameter("version", AppUtil.getVersionName()).build()
//            return@Interceptor chain.proceed(request.newBuilder().url(url).build())
//        })

        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        client.interceptors().add(logger)

        client.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        client.readTimeout(TIME_OUT, TimeUnit.SECONDS)
        client.writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        return client.build()
    }
}