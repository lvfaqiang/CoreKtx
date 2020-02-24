package com.lvfq.kotlinbase.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel

/**
 * BaseVMFragment2020-02-24 16:54
 * @desc :
 *
 */
abstract class BaseVMFragment<VM : ViewModel> : BaseFragment() {
    protected abstract val viewModelClass: Class<VM>

    protected lateinit var viewModel: VM


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViewModel() {
        viewModel = VMFactory.findVM(this, viewModelClass)
    }

    protected inline fun <reified T : ViewModel> findVM(): T {
        return VMFactory.findVM(this, T::class.java)
    }

    protected inline fun <reified T : ViewModel> findVMByActivity(): T {
        return activity?.run {
            VMFactory.findVM(this, T::class.java)
        } ?: throw Exception("Invalid Activity")
    }
}