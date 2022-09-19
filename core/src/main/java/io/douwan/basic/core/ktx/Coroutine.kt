package io.douwan.basic.core.ktx

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Coroutine2022/4/17 15:53
 * @desc :
 *
 */
fun CoroutineScope.launchUI(block: suspend CoroutineScope.() -> Unit): Job {
    return this.launch(Dispatchers.Main, block = block)
}


fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }
