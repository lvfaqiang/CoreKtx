package com.lvfq.kotlinbase.views

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import cn.basic.core.builder.DialogBuilder
import cn.basic.core.common.ILoading
import com.lvfq.kotlinbase.databinding.LayoutLoadingBinding

/**
 * LoadingView
 * @author FaQiang on 2018/9/26 下午6:05
 * @desc :
 *
 */


class LoadingView private constructor(private val context: Context) : ILoading {

    companion object {
        fun get(context: Context): LoadingView {
            return LoadingView(context)
        }
    }

    private var cancelable = true
    private var canceledOnTouchOutside = true

    private var loadingBinding: LayoutLoadingBinding =
        LayoutLoadingBinding.inflate(LayoutInflater.from(context))

    private val dialog by lazy {
        DialogBuilder.get(context)
            .setCancelable(cancelable)
            .setCanceledOnTouchOutside(canceledOnTouchOutside)
            .setGravity(Gravity.CENTER)
            .setWidth(0f)
            .build(loadingBinding.root)
    }

    override fun show() {
        dialog.show()
    }

    override fun dismiss() {
        dialog.dismiss()
    }

    override fun isShowing(): Boolean {
        return dialog.isShowing
    }

    override fun setMessage(message: String) {
        loadingBinding.tvLoading.text = message
    }

    override fun setCancelable(boolean: Boolean) {
        cancelable = boolean
        dialog.setCancelable(cancelable)
    }

    override fun setCanceledOnTouchOutside(boolean: Boolean) {
        canceledOnTouchOutside = boolean
        dialog.setCanceledOnTouchOutside(cancelable)
    }
}