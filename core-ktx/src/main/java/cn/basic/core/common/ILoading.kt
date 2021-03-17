package cn.basic.core.common

interface ILoading {
    fun show()
    fun dismiss()

    fun isShowing(): Boolean

    fun setMessage(message: String)

    fun setCancelable(boolean: Boolean)

    fun setCanceledOnTouchOutside(boolean: Boolean)
}
