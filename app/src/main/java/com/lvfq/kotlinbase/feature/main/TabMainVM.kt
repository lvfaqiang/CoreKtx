package com.lvfq.kotlinbase.feature.main

import androidx.lifecycle.viewModelScope
import cn.basic.core.base.BasicViewModel
import cn.basic.core.ktx.launchUI
import kotlinx.coroutines.delay

/**
 * TabMainVM2021/2/4 10:04 PM
 * @desc :
 *
 */
class TabMainVM : BasicViewModel() {

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