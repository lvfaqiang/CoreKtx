package com.lvfq.kotlinbase.base

import android.support.annotation.UiThread

/**
 * IMvpPresenter
 * @author lvfq
 * @Github: https://github.com/lvfaqiang
 * @Blog: http://blog.csdn.net/lv_fq
 * @date 2017/12/21 上午10:48
 * @desc :
 *
 */
interface IMvpPresenter {

    @UiThread
    fun attachView(view: IMvpView)

    @UiThread
    fun detachView()
}