package com.lvfq.kotlinbase.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lvfq.kotlinbase.App
import com.lvfq.kotlinbase.R
import com.lvfq.kotlinbase.entities.LoadingState

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

    protected fun showLoading(message: String? = null, cancelable: Boolean) {
        loadingState.value =
            LoadingState(true, message ?: App.mContext.getString(R.string.loading), cancelable)
    }

    protected fun disLoading() {
        loadingState.value = LoadingState(false)
    }

}