package cn.basic.core.base

import androidx.viewbinding.ViewBinding

/**
 * BaseActivity2020-02-24 15:28
 * @desc :
 *
 */
abstract class BaseActivity<T : ViewBinding> : BaseVMActivity<T, BaseViewModel>() {

    override val viewModelClass: Class<BaseViewModel>
        get() = BaseViewModel::class.java


}