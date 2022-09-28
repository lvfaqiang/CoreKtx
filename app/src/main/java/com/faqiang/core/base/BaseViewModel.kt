package com.faqiang.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * BaseViewModel2022/4/15 00:22
 * @desc :
 *
 */
open class BaseViewModel : ViewModel() {

    open val message = MutableLiveData<String>()

//    protected val apiService by lazy {
//        ApiClient.apiService
//    }

}