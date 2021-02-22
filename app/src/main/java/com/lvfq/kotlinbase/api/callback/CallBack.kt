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
 *  回调方法释义：
 *
 *  BasicCallBack   方法一，接口给什么就返回什么，
 *
 *  BaseCallBack    方法二，根据统一规范要求，响应成功回调，不需要携带返回数据。 需根据需求，调整 success 条件。
 *
 *  SimpleCallBack  方法三，数据进行简单处理，适用于结果为 BaseResp<T> 类型， 回调方法直接返回 T 数据
 *
 *  以上三者继承关系为  SimpleCallBack   ->    BaseCallBack   ->   BasicCallBack
 */

/**
 * 方法一，接口给什么就返回什么，
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
 * 方法二，根据统一规范要求，响应成功回调，不需要携带返回数据。
 */
abstract class BaseCallBack<T : BasicResp>(bindView: View? = null) : BasicCallBack<T>(bindView) {

    override fun success(t: T) {
        if (t.success) {
            super.success(t)
        } else {
            super.failure(
                CustomException(
                    t.code,
                    t.message
                )
            )
        }
    }
}

/**
 * 方法三，数据进行简单处理，直接返回 泛型数据
 */
abstract class SimpleCallBack<T>(bindView: View? = null) : BaseCallBack<BaseResp<T>>(bindView) {

    override fun response(t: BaseResp<T>) {
        onSuccess(t.data)
    }

    abstract fun onSuccess(t: T?)

}