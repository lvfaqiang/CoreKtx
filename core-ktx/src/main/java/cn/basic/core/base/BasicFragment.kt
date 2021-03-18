package cn.basic.core.base

import androidx.viewbinding.ViewBinding

/**
 * BaseFragment2020-02-24 10:52
 * @desc :
 *
 */
abstract class BasicFragment<T : ViewBinding> : BasicVMFragment<T, BasicViewModel>() {

    override val viewModelClass: Class<BasicViewModel>
        get() = BasicViewModel::class.java
}