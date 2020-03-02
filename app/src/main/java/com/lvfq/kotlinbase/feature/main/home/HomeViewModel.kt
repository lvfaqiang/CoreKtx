package com.example.basic.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.lvfq.kotlinbase.api.Request
import com.lvfq.kotlinbase.api.net.ApiClient
import com.lvfq.kotlinbase.entities.LoginData
import com.lvfq.kotlinbase.utils.basic.LogUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, t ->
        {
            Log.i("lfq", "--------1123214213123")
            t.printStackTrace()
        }
    }

    val data: LiveData<LoginData> = liveData(Dispatchers.IO + coroutineExceptionHandler) {
        //        val result = ApiClient.getService().login("13178126836", "111111", "670145")
//        emit(result)
    }

    fun loadData() {

        Request.create(
            viewModelScope,
            {
                ApiClient.getService().login("13178126836", "111111", "313112")
            },
            { data ->
                LogUtil.i("success ")
            },
            { apiException ->
                LogUtil.i("error : ----- ${apiException.message}")
            }
        )
    }

}