package com.lvfq.kotlinbase.api.net

/**
 * BaseRepository2020/3/1 6:20 PM
 * @desc :
 *
 */
interface ApiCallBack<T> {

    fun onStart()

    fun onComplete()

    fun success(t: T)

    fun failure(e: Throwable)
}