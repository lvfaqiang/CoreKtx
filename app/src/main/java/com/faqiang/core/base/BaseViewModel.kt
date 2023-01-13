package com.faqiang.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faqiang.core.api.ApiClient
import com.faqiang.core.api.ApiService

/**
 * BaseViewModel2022/4/15 00:22
 * @desc :
 *
 */
open class BaseViewModel : ViewModel() {

    open val message = MutableLiveData<String>()

    // 这里用 get() 为了服务于 apiService 的动态切换
    protected val apiService: ApiService
        get() {
            return ApiClient.apiService
        }

}