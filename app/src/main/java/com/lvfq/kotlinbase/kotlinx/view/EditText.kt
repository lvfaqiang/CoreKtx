package com.lvfq.kotlinbase.kotlinx.view

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText

/**
 * Ext2021/1/18 6:03 PM
 * @desc :
 *
 */
/**
 * @param toast 弹出提示方法
 * @param string 自定义提示语
 * @param regexMethod 自定义校验方法
 */
fun EditText.checkEditTextInput(
    toast: (String) -> Unit,
    string: String = "",
    regexMethod: ((String) -> Boolean)? = null
): String? {
    val str = text.toString()
    if (str.isEmpty()) {
        if (string.isEmpty()) {
            toast(hint.toString())
        } else {
            toast(string)
        }
        return null
    } else {
        if (regexMethod != null && !regexMethod.invoke(str)) {
            return null
        }
    }
    return str
}

/**
 * 密码显示切换
 */
fun EditText.passwordDisplay(toggle: Boolean) {
    if (toggle) {
        // 明文
        this.transformationMethod = HideReturnsTransformationMethod.getInstance()
    } else
    // 密文
        this.transformationMethod = PasswordTransformationMethod.getInstance()

    setSelection(text.toString().length)
}