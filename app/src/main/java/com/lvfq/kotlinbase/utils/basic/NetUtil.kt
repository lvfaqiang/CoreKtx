package com.lvfq.kotlinbase.utils.basic

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.lvfq.kotlinbase.App

/**
 * NetUtil
 * @author FaQiang on 2018/10/12 下午3:02
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *  网络状态判断
 */
object NetUtil {

    /**
     * 获取网络类型
     */
    fun getNetworkType(): NetType {
        val connectivity = getConnectivity(App.mContext)
        return connectivity?.let { manager ->
            val info = manager.activeNetworkInfo ?: return@let NetType.NULL
            info.state?.let { state ->
                networkState(info, state)
            } ?: NetType.NULL
        } ?: NetType.NULL
    }


    /**
     * 是否有网
     */
    fun hasNetWork() = getNetworkType() != NetType.NULL


    private fun networkState(info: NetworkInfo, state: NetworkInfo.State): NetType {
        if (state != NetworkInfo.State.CONNECTED && state != NetworkInfo.State.CONNECTING) {
            return NetType.NULL
        }
        return if (info.type == 1) NetType.WIFI else NetType.MOBILE
    }

    private fun getConnectivity(context: Context): ConnectivityManager? {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    enum class NetType private constructor() {
        NULL,
        WIFI,
        MOBILE
    }
}