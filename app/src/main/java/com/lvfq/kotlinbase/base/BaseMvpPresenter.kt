package com.lvfq.kotlinbase.base

import android.support.annotation.UiThread
import java.lang.ref.WeakReference

/**
 * IBaseMvpPresenter
 * @author lvfq
 * @Github: https://github.com/lvfaqiang
 * @Blog: http://blog.csdn.net/lv_fq
 * @date 2017/12/21 上午10:24
 * @desc :
 *
 */
open class BaseMvpPresenter<out V : IMvpView> : IMvpPresenter {
    private var viewRef: WeakReference<V>? = null

    @UiThread
    override fun attachView(view: IMvpView) {
        try {
            //如果强转失败，则抛出异常
            viewRef = WeakReference(view as V)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @UiThread
    fun getView(): V? {
        return if (viewRef == null) null else viewRef?.get()
    }

    @UiThread
    override fun detachView() {
        if (viewRef != null) {
            // 最终销毁当前 View 避免内存泄漏
            viewRef?.clear()
            viewRef = null
        }
    }
}