package com.lvfq.kotlinbase.kotlinx

import androidx.lifecycle.MutableLiveData

/**
 * LiveData2020/6/8 3:18 PM
 * @desc :
 *
 */
fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }