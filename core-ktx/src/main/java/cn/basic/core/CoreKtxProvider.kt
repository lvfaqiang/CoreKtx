package cn.basic.core

import android.content.Context
import cn.basic.core.config.HawkConfig

/**
 * CoreKtxProvider2021/3/17 6:11 PM
 * @desc :
 *
 */
class CoreKtxProvider private constructor(val context: Context) {

    companion object {
        lateinit var mContext: Context
            private set

        var INSTANCE: CoreKtxProvider? = null
            private set
            get() {
                if (field == null) {
                    throw NullPointerException("CoreKtxProvider not init ")
                } else {
                    return field
                }
            }

        fun get(context: Context): CoreKtxProvider {
            if (INSTANCE == null) {
                INSTANCE = CoreKtxProvider(context)
            }
            return INSTANCE!!
        }
    }

    init {
        mContext = context
    }

    private var spName: String = ""


    private var baseUrl: String = ""

    fun setSPName(name: String): CoreKtxProvider {
        spName = name
        return this
    }

    fun getBaseUrl(): String {
        return baseUrl
    }

    fun setBaseUrl(url: String): CoreKtxProvider {
        this.baseUrl = url
        return this
    }

    fun build() {
        initHawk()
    }

    private fun initHawk() {
        HawkConfig.init(context, spName)
    }

}