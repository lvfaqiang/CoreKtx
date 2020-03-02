package com.lvfq.kotlinbase.api

import com.lvfq.kotlinbase.api.net.ApiException
import com.lvfq.kotlinbase.kotlinx.coroutines.launchUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.withContext

/**
 * BaseRepository2020/3/1 6:20 PM
 * @desc :
 *
 */
abstract class BaseRepository<T>(scope: CoroutineScope = GlobalScope, call: suspend () -> T) {

    open fun onStart() {}

    open fun onFinished() {}

    open fun onFinally() {}

    abstract fun success(t: T)

    // 最初始的错误内容，
    open fun failure(e: Throwable) {
        failure(ApiException(e))
    }

    /**
     *  格式化后的异常信息
     */
    open fun failure(apiException: ApiException) {}


    init {
        launchUI(scope) {
            onStart()
            try {
                val t = withContext(Dispatchers.IO) { call.invoke() }
                // 这里处理请求响应之后的正确结果
                success(t)
                onFinished()
            } catch (e: Exception) {
                failure(e)
            } finally {
                onFinally()
            }
        }
    }
}