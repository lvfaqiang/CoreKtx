package com.lvfq.kotlinbase.api.net

import com.lvfq.kotlinbase.kotlinx.coroutines.launchUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.withContext

class ApiLauncher<T>
private constructor(
    private val call: suspend () -> T
) {

    companion object {
        fun <T> get(call: suspend () -> T): ApiLauncher<T> {
            return ApiLauncher(call)
        }
    }

    private var scope: CoroutineScope = GlobalScope
    private var callBack: ApiCallBack<T>? = null

    fun withScope(scope: CoroutineScope): ApiLauncher<T> {
        this.scope = scope
        return this
    }

    fun callBack(callBack: ApiCallBack<T>?): ApiLauncher<T> {
        this.callBack = callBack
        return this
    }

    fun launch() {
        start()
    }

    private fun start() {
        launchUI(scope) {
            callBack?.onStart()
            try {
                val t = withContext(Dispatchers.IO) { call.invoke() }
                // 这里处理请求响应之后的正确结果
                callBack?.success(t)
            } catch (e: Exception) {
                callBack?.failure(e)
            } finally {
                callBack?.onComplete()
            }
        }
    }
}
