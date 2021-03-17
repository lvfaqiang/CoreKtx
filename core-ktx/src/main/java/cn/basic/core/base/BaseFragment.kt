package cn.basic.core.base

import androidx.viewbinding.ViewBinding

/**
 * BaseFragment2020-02-24 10:52
 * @desc :
 *
 */
abstract class BaseFragment<T : ViewBinding> : BaseVMFragment<T, BaseViewModel>() {

    override val viewModelClass: Class<BaseViewModel>
        get() = BaseViewModel::class.java
}