package com.faqiang.core.feature

import com.faqiang.core.base.BaseViewModel
import io.douwan.network.ApiLauncher
import io.douwan.network.CallBackData
import kotlinx.coroutines.flow.flow

/**
 * MainViewModel2023/1/14 00:27
 * @desc :
 *
 */
class MainViewModel : BaseViewModel() {

    fun getDatas() = flow{
        emit(CallBackData(
            ApiLauncher.launchCommon {
                apiService.getDemoDatas()
            }
        ))
    }
}