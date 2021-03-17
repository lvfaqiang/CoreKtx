package cn.basic.core.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

/**
 * ClipboardUtil
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
        val cmb: ClipboardManager? =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cmb?.setPrimaryClip(ClipData.newPlainText(null, content.trim()))
    }

    /**
     * 实现粘贴功能
     *
     * @param context
     * @return
     */
    fun paste(context: Context): CharSequence {
        // 得到剪贴板管理器
        val cmb: ClipboardManager? =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = cmb?.primaryClip
        return if (clip != null && clip.itemCount > 0) {
            clip.getItemAt(0).coerceToText(context)
        } else ""
    }

}