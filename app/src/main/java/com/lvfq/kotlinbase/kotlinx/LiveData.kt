package com.lvfq.kotlinbase.kotlinx

import androidx.lifecycle.MutableLiveData

/**
 * LiveData2020/6/8 3:18 PM
 * @desc :
 *
 */
/**
 * 该方法需要注意，设置默认值的时候，会出发一次 observe 的执行。
 */
fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }