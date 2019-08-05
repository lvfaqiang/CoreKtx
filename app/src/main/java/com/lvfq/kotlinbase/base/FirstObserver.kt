package com.lvfq.kotlinbase.base

import android.view.View

/**
 * FirstObserver2019/5/6 3:20 PM
 * @desc :
 * 加载框只有第一次的时候才显示。
 */
abstract class FirstObserver<T>
@JvmOverloads constructor(override var iBase: IBase? = null,
                          override var view: View? = null
) : SimpleObserver<T>(iBase, view) {

    override fun showLoading() {
        if (iBase?.isFirst() == true) {
            iBase?.showLoading()
        }
    }
}