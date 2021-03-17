package com.lvfq.kotlinbase.feature.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import cn.basic.core.api.Failure
import cn.basic.core.api.Success
import cn.basic.core.api.demo.DemoReq
import cn.basic.core.ktx.apiLaunch
import cn.basic.core.ktx.launch
import com.lvfq.kotlinbase.entities.LoginData
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
        /**
         * 请求演示
         */
        launch {

            val result = apiLaunch {
                DemoReq.get().getService().login("13178126836", "111111", "313112")
            }

            if (result is Failure) {

            }

            if (result is Success) {
                result.data
            }
        }
    }
}