package com.lvfq.kotlinbase.base

import androidx.viewbinding.ViewBinding

/**
 * BaseActivity2021/3/19 1:13 AM
 * @desc :
 *
 */
abstract class BaseActivity<T : ViewBinding> : BaseVMActivity<T, BaseVM>() {

    override val viewModelClass: Class<BaseVM>
        get() = BaseVM::class.java


}