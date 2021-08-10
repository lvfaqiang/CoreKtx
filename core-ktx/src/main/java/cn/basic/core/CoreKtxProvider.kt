package cn.basic.core

import android.content.Context
import cn.basic.core.api.CustomError
import cn.basic.core.config.HawkConfig
import cn.basic.core.util.LogUtil
import okhttp3.Interceptor
import retrofit2.HttpException

/**
 * CoreKtxProvider2021/3/17 6:11 PM
 * @desc :
 *
 */
class CoreKtxProvider private constructor() {

    companion object {
        lateinit var mContext: Context
            private set

        private var singleInstance: CoreKtxProvider? = null

        fun get(): CoreKtxProvider {
            if (singleInstance == null) {
                singleInstance = CoreKtxProvider()
            }
            return singleInstance!!
        }
    }

    var spName: String = ""
        private set

    var baseUrl: String = ""
        private set

    var appVersion = ""
        private set

    var DEBUG = false
        private set

    var appFileProvider: String = ""
        private set


    private var customExceptionHandling: ((HttpException) -> CustomError?)? = null

    private var apiInterceptors = ArrayList<Interceptor>()


    fun spName(name: String): CoreKtxProvider {
        spName = name
        return this
    }

    fun baseUrl(url: String): CoreKtxProvider {
        this.baseUrl = url
        return this
    }

    fun appVersion(version: String): CoreKtxProvider {
        this.appVersion = version
        return this
    }

    fun logEnable(enable: Boolean): CoreKtxProvider {
        LogUtil.setLogEnable(enable)
        return this
    }

    fun fileProvider(fileProvider: String): CoreKtxProvider {
        appFileProvider = fileProvider
        return this
    }


    // 设置自定义错误处理，不需要就不设置
    fun setCustomExceptionHandling(handling: (HttpException) -> CustomError?): CoreKtxProvider {
        customExceptionHandling = handling
        return this
    }

    // 自定义拦截器
    fun setApiInterceptors(vararg interceptor: Interceptor): CoreKtxProvider {
        interceptor.map {
            apiInterceptors.add(it)
        }
        return this
    }

    fun isDebug(debug: Boolean): CoreKtxProvider {
        this.DEBUG = debug
        return this
    }


    fun build(context: Context) {
        mContext = context
        initHawk()
    }

    private fun initHawk() {
        HawkConfig.init(mContext, spName)
    }


    fun getExceptionHandling(): ((HttpException) -> CustomError?)? {
        return customExceptionHandling
    }

    fun getApiInterceptors(): ArrayList<Interceptor> {
        return apiInterceptors
    }
}