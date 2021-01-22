package com.lvfq.kotlinbase.api.callback

import android.view.View
import com.lvfq.kotlinbase.api.net.ApiCallBack
import com.lvfq.kotlinbase.api.net.ApiException
import com.lvfq.kotlinbase.api.net.CustomException
import com.lvfq.kotlinbase.base.BaseResp
import com.lvfq.kotlinbase.base.BasicResp

/**
 * SimpleRepository2020/3/1 6:30 PM
 * @desc :
 *
 */

/**
 * 方法一
 */
abstract class BasicCallBack<T>(private val bindView: View? = null) : ApiCallBack<T> {

    override fun onStart() {
        bindView?.apply {
            isClickable = false
        }
    }

    override fun success(t: T) {
        bindView?.apply {
            isClickable = true
        }
        response(t)
    }

    override fun failure(e: Throwable) {
        bindView?.apply {
            isClickable = true
        }
        failure(ApiException(e))
    }

    override fun onComplete() {

    }

    abstract fun response(t: T)

    open fun failure(apiException: ApiException) {

    }
}

/**
 * 方法二
 */
abstract class BaseCallBack<T : BasicResp>(bindView: View? = null) : BasicCallBack<T>(bindView) {

    override fun success(t: T) {
        if (t.success) {
            super.success(t)
        } else {
            throw CustomException(t.code, t.message)     //需要抛出异常，再顶层捕获，然后执行 failure(Throwable) 方法。
        }
    }
}

/**
 * 方法三
 */
abstract class SimpleCallBack<T>(bindView: View? = null) : BaseCallBack<BaseResp<T>>(bindView) {

    override fun response(t: BaseResp<T>) {
        if (t.data != null) {
            onSuccess(t.data)
        } else {
            throw CustomException(-1, "the Data parameter of Response is Null")
        }
    }

    abstract fun onSuccess(t: T)

}