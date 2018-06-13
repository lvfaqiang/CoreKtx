package com.lvfq.kotlinbase.http

import com.lvfq.kotlinbase.base.IBase
import com.lvfq.library.utils.LvLog
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * HxObserver
 * @author FaQiang on 2018/6/6 下午1:36
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
abstract class ApiObserver<T>() : Observer<HttpBase<T>>, IObserver<T> {
    private var iBase: IBase? = null

    constructor(IBase: IBase?) : this() {
        iBase = IBase
    }

    override fun onComplete() {
        iBase?.disLoading()
    }

    override fun onSubscribe(d: Disposable) {
        iBase?.showLoading()
    }

    override fun onNext(t: HttpBase<T>) {

        onSuccess(t.data)

        t.data?.let {
            LvLog.i("data : ${it.toString()}")
        }
        t.error?.let {
//            LvLog.i("error :${it.code} ,${it.message}")
        }
    }

    override fun onError(e: Throwable) {
        iBase?.disLoading()
        onError(ApiException(e))
    }
}