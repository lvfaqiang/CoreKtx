package com.lvfq.kotlinbase.base

import androidx.viewbinding.ViewBinding
import cn.basic.core.base.BasicVMActivity
import cn.basic.core.common.ILoading
import com.lvfq.kotlinbase.views.LoadingView

/**
 * BaseVMActivity2021/3/19 1:14 AM
 * @desc :
 *
 */
abstract class BaseVMActivity<T : ViewBinding, VM : BaseVM> : BasicVMActivity<T, VM>() {

    override val loadingView: ILoading
        get() = LoadingView.get(this)

}