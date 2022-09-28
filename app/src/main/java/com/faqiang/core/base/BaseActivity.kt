package com.faqiang.core.base

import androidx.viewbinding.ViewBinding


/**
 *
 * @author lin
 * @date 2020/12/6
 */

abstract class BaseActivity<VB : ViewBinding> : BaseVMActivity<VB, BaseViewModel>() {

    override val viewModelClass: Class<BaseViewModel>
        get() = BaseViewModel::class.java

}
