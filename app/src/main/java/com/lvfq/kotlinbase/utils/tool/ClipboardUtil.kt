package com.lvfq.kotlinbase.utils.tool

import android.content.ClipboardManager
import android.content.Context

/**
 * ClipboardUtil
 * @author FaQiang on 2018/9/30 上午11:40
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
object ClipboardUtil {
    /**
     * 实现文本复制功能
     *
     * @param content
     */
    fun copy(context: Context, content: String) {
        // 得到剪贴板管理器
        val cmb: ClipboardManager? = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cmb?.text = content?.trim()
    }

    /**
     * 实现粘贴功能
     *
     * @param context
     * @return
     */
    fun paste(context: Context): String {
        // 得到剪贴板管理器
        val cmb: ClipboardManager? = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        return cmb?.text.toString().trim()
    }

}