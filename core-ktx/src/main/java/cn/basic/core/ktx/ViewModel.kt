package cn.basic.core.ktx

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

/**
 * ViewModel2021/3/3 10:16 AM
 * @desc :
 *
 */

fun ViewModel.launch(block: suspend CoroutineScope.() -> Unit) {
    launchUI(viewModelScope, block)
}
