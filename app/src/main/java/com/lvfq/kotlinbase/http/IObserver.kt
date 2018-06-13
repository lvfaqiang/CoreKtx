package com.lvfq.kotlinbase.http

/**
 * IObserver
 * @author FaQiang on 2018/6/6 下午4:13
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
interface IObserver<T> {

    fun onSuccess(t: T?)

    fun onError(apiException: ApiException)
}