package com.lvfq.kotlinbase.base

import android.content.Context
import androidx.annotation.StringRes

/**
 * IBase
 * @author FaQiang on 2018/9/25 下午2:34
 * @desc :
 *
 */

/**
 *  IBase 作为基础基类来使用， IHxBase 是在 IBase 的基础上进行扩展一些 业务方便的基础配置
 *
 */

interface ISimpleBase : IBase {

    fun useEventBus(): Boolean = false  // EventBus 配置

}

interface IBase {

    fun isFirst(): Boolean

    fun showLoading()

    fun disLoading()

    fun toastSuc(message: String)

    fun toastSuc(@StringRes strId: Int)

    fun toastFailed(message: String)

    fun toastFailed(@StringRes strId: Int)

    fun getContext(): Context?
}
