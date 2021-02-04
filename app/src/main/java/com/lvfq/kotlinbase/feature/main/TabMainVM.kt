package com.lvfq.kotlinbase.feature.main

import androidx.lifecycle.viewModelScope
import com.lvfq.kotlinbase.base.BaseViewModel
import com.lvfq.kotlinbase.kotlinx.coroutines.launchUI
import kotlinx.coroutines.delay

/**
 * TabMainVM2021/2/4 10:04 PM
 * @desc :
 *
 */
class TabMainVM : BaseViewModel() {

    fun show() {
        showLoading(cancelable = false)

        val job = launchUI(viewModelScope) {
            delay(3000)
//            setMessage("我要修改内容")
            showLoading("我要修改内容我要修改内容我要修改内容", true)
            delay(3000)
            disLoading()
        }


    }


}