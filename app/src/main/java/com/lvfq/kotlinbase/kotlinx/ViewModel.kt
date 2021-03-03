package com.lvfq.kotlinbase.kotlinx

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lvfq.kotlinbase.api.net.Results
import com.lvfq.kotlinbase.api.net.ApiLauncher
import com.lvfq.kotlinbase.base.BaseResp
import com.lvfq.kotlinbase.kotlinx.coroutines.launchUI
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

