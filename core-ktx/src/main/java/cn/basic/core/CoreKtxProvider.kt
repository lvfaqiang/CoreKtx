package cn.basic.core

import android.content.Context
import cn.basic.core.api.Error
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

    private var spName: String = ""

    private var baseUrl: String = ""

    private var version = ""

    private var customExceptionHandling: ((HttpException) -> Error?)? = null

    private var apiInterceptors = ArrayList<Interceptor>()


    fun setSPName(name: String): CoreKtxProvider {
        spName = name
        return this
    }

    fun setBaseUrl(url: String): CoreKtxProvider {
        this.baseUrl = url
        return this
    }

    fun setVersion(version: String): CoreKtxProvider {
        this.version = version
        return this
    }

    fun setLogEnable(enable: Boolean): CoreKtxProvider {
        LogUtil.setLogEnable(enable)
        return this
    }

    // 设置自定义错误处理，不需要就不设置
    fun setCustomExceptionHandling(handling: (HttpException) -> Error?): CoreKtxProvider {
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


    fun build(context: Context) {
        mContext = context
        initHawk()
    }

    private fun initHawk() {
        HawkConfig.init(mContext, spName)
    }


    fun getBaseUrl(): String {
        return baseUrl
    }

    fun getVersion(): String {
        return version
    }

    fun getExceptionHandling(): ((HttpException) -> Error?)? {
        return customExceptionHandling
    }

    fun getApiInterceptors(): ArrayList<Interceptor> {
        return apiInterceptors
    }
}