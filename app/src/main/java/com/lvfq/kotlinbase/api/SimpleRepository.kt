package com.lvfq.kotlinbase.api

import android.view.View
import com.lvfq.kotlinbase.api.net.ApiException
import com.lvfq.kotlinbase.api.net.CustomException
import com.lvfq.kotlinbase.entities.base.BaseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope

/**
 * SimpleRepository2020/3/1 6:30 PM
 * @desc :
 *
 */


object Request {
    fun <T : BaseEntity> create(
        scope: CoroutineScope = GlobalScope,
        call: suspend () -> T,
        success: (T) -> Unit,
        failure: (ApiException) -> Unit = {},
        bindView: View? = null
    ): BaseRepository<T> {
        return object : SimpleRepository<T>(scope, call) {
            override fun onStart() {
                bindView?.apply {
                    isClickable = false
                }
            }

            override fun response(t: T) {
                bindView?.apply {
                    isClickable = true
                }
                success.invoke(t)
            }

            override fun failure(apiException: ApiException) {
                bindView?.apply {
                    isClickable = true
                }
                failure.invoke(apiException)
            }
        }
    }
}

abstract class SimpleRepository<T : BaseEntity>(scope: CoroutineScope, call: suspend () -> T) :
    BaseRepository<T>(scope, call) {

    abstract fun response(t: T)

    override fun success(t: T) {
        // 这里处理请求响应之后的正确结果
        if (t.response_code == "APPLY_SUCCESS") {
            if (!t.token.isNullOrEmpty()) {
//                        UserCache.cacheToken(t.token)
//                        App.mContext.startService(Intent(App.mContext, NotifyService::class.java).apply {
//                            putExtra("resetSocket", true)
//                        })
            }
            response(t)
        } else {
            if (t.response_code == "AUTHORIZE_FAIL") {
//                        UserCache.clear()
//                        AppLifecycle.latestActivity()?.startActivity<LoginActivity>() {
//                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        }
            } else {
                failure(CustomException(-1, t.response_message ?: ""))
            }
        }
    }
}