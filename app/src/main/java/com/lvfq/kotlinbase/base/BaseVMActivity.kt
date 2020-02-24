package com.lvfq.kotlinbase.base

import android.os.Bundle

/**
 * BaseVMActivity2020-02-24 15:39
 * @desc :
 *
 */
abstract class BaseVMActivity<VM : BaseViewModel> : BaseActivity() {

    protected abstract val viewModelClass: Class<VM>

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }


    private fun initViewModel() {
        viewModel = VMFactory.findVM(this, viewModelClass)
    }

    protected inline fun <reified T : BaseViewModel> findVM(): T {
        return VMFactory.findVM(this, T::class.java)
    }

}