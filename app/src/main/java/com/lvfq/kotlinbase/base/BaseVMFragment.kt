package com.lvfq.kotlinbase.base

import androidx.viewbinding.ViewBinding
import cn.basic.core.base.BasicVMFragment
import cn.basic.core.common.ILoading
import com.lvfq.kotlinbase.views.LoadingView

/**
 * BaseVMFragment2021/3/19 1:28 AM
 * @desc :
 *
 */
abstract class BaseVMFragment<T : ViewBinding, VM : BaseVM> : BasicVMFragment<T, VM>() {

    override val loadingView: ILoading
        get() = LoadingView.get(requireContext())

}