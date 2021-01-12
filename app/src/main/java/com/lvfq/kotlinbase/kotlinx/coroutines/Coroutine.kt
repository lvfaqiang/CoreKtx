package com.lvfq.kotlinbase.kotlinx.coroutines

import kotlinx.coroutines.*

/**
 * Coroutine2019-08-05 10:31
 * @desc :
 *
 */

fun launchUI(block: suspend CoroutineScope.() -> Unit): Job {
    return GlobalScope.launch(Dispatchers.Main, block = block)
}

fun launchUI(scope: CoroutineScope, block: suspend CoroutineScope.() -> Unit): Job {
    return scope.launch(Dispatchers.Main, block = block)
}

fun launchAsync(block: suspend CoroutineScope.() -> Unit): Job {
    return GlobalScope.launch(Dispatchers.IO, block = block)
}

fun launchAsync(scope: CoroutineScope, block: suspend CoroutineScope.() -> Unit): Job {
    return scope.launch(Dispatchers.IO, block = block)
}

suspend fun <T> CoroutineScope.launchAsync(block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.IO, block)
}
