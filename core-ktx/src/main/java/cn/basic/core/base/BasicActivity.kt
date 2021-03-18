package cn.basic.core.base

import androidx.viewbinding.ViewBinding

/**
 * BaseActivity2020-02-24 15:28
 * @desc :
 *
 */
abstract class BasicActivity<T : ViewBinding> : BasicVMActivity<T, BasicViewModel>() {

    override val viewModelClass: Class<BasicViewModel>
        get() = BasicViewModel::class.java


}