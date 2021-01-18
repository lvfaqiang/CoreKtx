package com.lvfq.kotlinbase.kotlinx.coroutines

import kotlinx.coroutines.*

/**
 * Coroutine2019-08-05 10:31
 * @desc :
 *
 */

fun CoroutineScope.launchUI(block: suspend CoroutineScope.() -> Unit): Job {
    return launch(Dispatchers.Main, block = block)
}

fun CoroutineScope.launchIO(block: suspend CoroutineScope.() -> Unit): Job {
    return launch(Dispatchers.IO, block = block)
}

suspend fun <T> CoroutineScope.launchIO(block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.IO, block)
}
