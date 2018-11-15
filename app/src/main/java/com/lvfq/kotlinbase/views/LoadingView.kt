package com.lvfq.kotlinbase.views

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface

/**
 * LoadingView
 * @author FaQiang on 2018/9/26 下午6:05
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */

interface ILoading {
    fun show()
    fun dismiss()

    fun isShowing(): Boolean

    fun setMessage(message: String)

    fun setCancelable(boolean: Boolean)

    fun setCanceledOnTouchOutside(boolean: Boolean)

    fun setOnDismissListener(listener: DialogInterface.OnDismissListener)
}


class LoadingView constructor(context: Context) : ILoading {


    private val dialog by lazy {
        ProgressDialog(context).apply {
            setMessage("加载中...")
        }
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
        dialog.setMessage(message)
    }

    override fun setCancelable(boolean: Boolean) {
        dialog.setCancelable(boolean)
    }

    override fun setCanceledOnTouchOutside(boolean: Boolean) {
        dialog.setCanceledOnTouchOutside(boolean)
    }

    override fun setOnDismissListener(listener: DialogInterface.OnDismissListener) {
        dialog.setOnDismissListener(listener)
    }


}