package cn.basic.core.util

import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView

/**
 * @author fanjianwei
 * @Description
 * @date 20160330
 */
object KeyBoardUtils {
    /**
     * @param mEditText
     * @param mContext
     */
    fun openKeybord(mEditText: EditText?, mContext: Context) {
        val imm = mContext
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
        //
        if (mContext is Activity) {
            mContext.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        }
    }

    /**
     * @param mEditText
     * @param mContext
     */
    fun closeKeybord(mEditText: EditText, mContext: Context) {
        val imm = mContext
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }

    fun closeKeybord(mContext: Context?) {
        if (mContext == null) {
            return
        }
        val imm = mContext
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (mContext is Activity) {
            val activity = mContext
            if (activity.currentFocus != null) {
                imm.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
                activity.window
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
            }
        }
    }

    /**
     * 设置键盘隐藏机制<br></br>
     * 效果：点击输入框外的区域隐藏输入法软键盘<br></br>
     * 前提，当前界面不能有控件设置onTouchListener
     */
    fun setupUISoftKeyBoardHideSystem(
        view: View,
        hasFocus: Boolean
    ) {
        if (view is CheckBox) return
        if (view is TextView) return
        if (hasFocus) { // 防止有些页面自动打开输入框。 如果遇到自动弹出软键盘的，则需要 设置为 true。
            view.isFocusable = true
            view.isFocusableInTouchMode = true
        }
        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { v, event ->
                //View editText = ((Activity) v.getContext()).getCurrentFocus();
                if (event.action == MotionEvent.ACTION_DOWN) {
                    hideKeyboard(v)
                    if (hasFocus) {
                        view.requestFocus()
                    }
                }
                false
            }
        }
        // If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUISoftKeyBoardHideSystem(
                    innerView,
                    hasFocus
                )
            }
        }
    }

    /**
     * 隐藏虚拟键盘
     *
     * @param v
     */
    fun hideKeyboard(v: View) {
        val imm = v.context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) {
            imm.hideSoftInputFromWindow(v.applicationWindowToken, 0)
        }
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    fun showSoftInputFromWindow(
        mContext: Context,
        editText: EditText
    ) {
        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
        editText.requestFocus()
        openKeybord(editText, mContext)
    }
}