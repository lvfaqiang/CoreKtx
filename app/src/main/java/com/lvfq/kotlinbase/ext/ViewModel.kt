package com.lvfq.kotlinbase.ext

import androidx.lifecycle.ViewModel
import cn.basic.core.api.ApiException
import cn.basic.core.api.ApiLauncherDemo
import cn.basic.core.api.Failure
import cn.basic.core.api.Success

/**
 * ViewModel2021/9/5 10:03 下午
 * @desc :
 *
 */
suspend inline fun <T> ViewModel.sendRequest(
    noinline call: suspend () -> T,
    success: (T) -> Unit,
    failed: (ApiException) -> Unit = {},
    finaly: () -> Unit = {}
) {
    val result = ApiLauncherDemo.launchCommon(call)
    if (result is Success) {
        success(result.data)
    }
    if (result is Failure) {
        failed(result.exception)
    }
    finaly()
}