package com.lvfq.kotlinbase.feature.main

import androidx.lifecycle.viewModelScope
import cn.basic.core.ktx.launchUI
import com.lvfq.kotlinbase.base.BaseVM
import kotlinx.coroutines.delay

/**
 * TabMainVM2021/2/4 10:04 PM
 * @desc :
 *
 */
class MainVM : BaseVM() {
    var curIndex: Int = 0
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