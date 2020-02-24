package com.lvfq.kotlinbase.base

import android.view.View
import com.lvfq.kotlinbase.api.net.ApiException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * BaseObserver
 * @author FaQiang on 2018/7/31 下午2:28
 * @desc :
 *
 */
abstract class BaseObserver<T>
@JvmOverloads constructor(open var iBase: IBase? = null, open var view: View? = null) : Observer<T> {


    override fun onSubscribe(d: Disposable) {
        showLoading()
        viewClick(false)

    }

    override fun onNext(t: T) {
        viewClick(true)
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        disLoading()
        viewClick(true)

        onError(ApiException(e))

        onFinally()
    }

    override fun onComplete() {
        disLoading()
        onFinally()
    }


    abstract fun onSuccess(t: T)

    protected fun onFinally() {
    }


    open fun onError(apiException: ApiException) {
        iBase?.toastFailed(apiException.message)
    }


    open fun showLoading() {
        iBase?.showLoading()
    }

    private fun disLoading() {
        iBase?.disLoading()
    }

    private fun viewClick(clickable: Boolean) {
        view?.apply {
            isClickable = clickable
        }
    }


}