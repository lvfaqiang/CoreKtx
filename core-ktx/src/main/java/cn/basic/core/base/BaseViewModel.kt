package cn.basic.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.basic.core.CoreKtxProvider
import cn.basic.core.ktx.R

/**
 * BaseViewModel2020-02-24 17:39
 * @desc :
 *
 */
open class BaseViewModel : ViewModel() {
    val loadingState = MutableLiveData<LoadingState>()
    val updateMessage = MutableLiveData<String>()


    protected fun setMessage(message: String?) {
        message?.let {
            updateMessage.value = it
        }
    }

    protected fun showLoading(message: String? = null, cancelable: Boolean = false) {
        loadingState.value =
            LoadingState(true, message ?: CoreKtxProvider.mContext.getString(R.string.loading), cancelable)
    }

    protected fun disLoading() {
        loadingState.value = LoadingState(false)
    }

}