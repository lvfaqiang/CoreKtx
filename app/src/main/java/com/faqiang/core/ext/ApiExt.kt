package com.faqiang.core.ext

import com.faqiang.core.api.BaseResp
import io.douwan.network.ApiLauncher
import io.douwan.network.Results
import io.douwan.network.config.CustomException

suspend fun <T> ApiLauncher.launch(
    call: suspend () -> BaseResp<T>
): Results<T?> {
    return try {
        val t = call.invoke()
        if (t.success) {
            Results.success(t.data)
        } else {
            Results.failure(CustomException(-1, t.message ?: ""))
        }
    } catch (e: Exception) {
        Results.failure(e)
    }
}