package com.lvfq.kotlinbase.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

/**
 * VMFactory2020-02-24 20:21
 * @desc :
 *
 */
object VMFactory {

    fun <T : ViewModel> findVM(owner: ViewModelStoreOwner, vmClass: Class<T>): T {
        return ViewModelProvider(owner).get(vmClass)
    }
}