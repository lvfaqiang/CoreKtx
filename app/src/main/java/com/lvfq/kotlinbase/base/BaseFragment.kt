package com.lvfq.kotlinbase.base

import androidx.viewbinding.ViewBinding

/**
 * BaseFragment2021/3/19 1:29 AM
 * @desc :
 *
 */
abstract class BaseFragment<T : ViewBinding> : BaseVMFragment<T, BaseVM>() {

    override val viewModelClass: Class<BaseVM>
        get() = BaseVM::class.java

}