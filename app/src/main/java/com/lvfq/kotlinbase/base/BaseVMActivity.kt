package com.lvfq.kotlinbase.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding

/**
 * BaseVMActivity2020-02-24 15:39
 * @desc :
 *
 */
abstract class BaseVMActivity<T : ViewBinding, VM : BaseViewModel> : BaseActivity<T>() {

    protected abstract val viewModelClass: Class<VM>

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }


    private fun initViewModel() {
        viewModel = VMFactory.findVM(this, viewModelClass)
    }

}