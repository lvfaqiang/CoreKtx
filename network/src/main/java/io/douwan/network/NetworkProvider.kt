package io.douwan.network

import android.annotation.SuppressLint
import android.content.Context
import okhttp3.Interceptor
import retrofit2.HttpException

/**
 * NetworkProvider2022/9/2 09:51
 * @desc :
 *
 */
class NetworkProvider private constructor() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context
            private set

        @SuppressLint("StaticFieldLeak")
        private var singleInstance: NetworkProvider? = null

        fun get(): NetworkProvider {
            if (singleInstance == null) {
                singleInstance = NetworkProvider()
            }
            return singleInstance!!
        }
    }

    private var customExceptionHandling: ((HttpException) -> CustomError?)? = null

    private var apiInterceptors = ArrayList<Interceptor>()

    var apiLog: (String?) -> Unit = {}
        private set

    var baseUrl: String = ""
        private set


    fun withContext(context: Context): NetworkProvider {
        mContext = context
        return this
    }

    fun baseUrl(url: String): NetworkProvider {
        baseUrl = url
        return this
    }

    // 设置自定义错误处理，不需要就不设置
    fun setCustomExceptionHandling(handling: (HttpException) -> CustomError?): NetworkProvider {
        customExceptionHandling = handling
        return this
    }

    // 自定义拦截器
    fun setApiInterceptors(vararg interceptor: Interceptor): NetworkProvider {
        interceptor.map {
            apiInterceptors.add(it)
        }
        return this
    }

    fun configApiLog(log: (String?) -> Unit): NetworkProvider {
        apiLog = log
        return this
    }

    fun getExceptionHandling(): ((HttpException) -> CustomError?)? {
        return customExceptionHandling
    }

    fun getApiInterceptors(): ArrayList<Interceptor> {
        return apiInterceptors
    }

}