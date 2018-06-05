package com.lvfq.kotlinbase.utils

import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Schedulers
 * @author FaQiang on 2018/6/4 下午4:03
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
object RxSchedulers {

    fun <T> applySchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer<T, T> {
            it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> applySchedulers(transformer: LifecycleTransformer<T>): ObservableTransformer<T, T> {
        return ObservableTransformerImpl<T>(transformer)
    }

    fun <T> applySchedulers(provider: LifecycleProvider<*>): ObservableTransformer<T, T> {
        return ObservableTransformerImpl1(provider)
    }


    private class ObservableTransformerImpl<T>(private val transformer: LifecycleTransformer<T>) : ObservableTransformer<T, T> {

        override fun apply(upstream: Observable<T>): ObservableSource<T> {
            return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).compose(transformer)
        }
    }

    private class ObservableTransformerImpl1<T>(private val provider: LifecycleProvider<*>) : ObservableTransformer<T, T> {

        override fun apply(upstream: Observable<T>): ObservableSource<T> {
            return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).compose(provider.bindToLifecycle())
        }
    }
}