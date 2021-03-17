package cn.basic.core.ktx

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.basic.core.api.Results
import cn.basic.core.api.ApiLauncher
import cn.basic.core.api.BaseResp
import kotlinx.coroutines.CoroutineScope

/**
 * ViewModel2021/3/3 10:16 AM
 * @desc :
 *
 */

fun ViewModel.launch(block: suspend CoroutineScope.() -> Unit) {
    launchUI(viewModelScope, block)
}

suspend fun <T> ViewModel.apiLaunchCommon(call: suspend () -> T): Results<T> {
    return ApiLauncher.launchCommon(call)
}

suspend fun <T> ViewModel.apiLaunch(call: suspend () -> BaseResp<T>): Results<T> {
    return ApiLauncher.launch(call)
}

