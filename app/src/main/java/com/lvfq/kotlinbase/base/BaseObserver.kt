package com.lvfq.kotlinbase.base

import android.view.View
import com.lvfq.kotlinbase.api.net.ApiException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * BaseObserver
 * @author FaQiang on 2018/7/31 下午2:28
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
abstract class BaseObserver<T> : Observer<T> {

    protected var iBase: IBase?
    protected var errorMsg: String = ""
    protected var view: View? = null

    // -------------   构造函数     -----------
    constructor(iBase: IBase?, errorMsg: String = "", view: View?) {
        this.iBase = iBase
        this.errorMsg = errorMsg
        this.view = view
    }

    constructor(iBase: IBase?, errorMsg: String) : this(iBase, errorMsg, null)

    constructor(iBase: IBase?, view: View?) : this(iBase, "", view)

    constructor(iBase: IBase?) : this(iBase, null)

    constructor() : this(null)

    // ---------------  End         ----------


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
        iBase?.toastFailed(
                if (errorMsg.isEmpty())
                    apiException.message
                else
                    errorMsg
        )
    }


    private fun showLoading() {
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