 package com.lvfq.kotlinbase.utils.tool

import android.content.Context
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File

/**
 * Luban2021/9/29 4:02 下午
 * @desc :
 *
 */
object LubanUtil {
    fun compressImage(
        context: Context,
        imagePath: String,
        start: () -> Unit = {},
        success: (File?) -> Unit = {},
        error: (Throwable?) -> Unit = {}
    ) {
        Luban.with(context)
            .load(imagePath)                                   // 传人要压缩的图片列表
            .ignoreBy(100)                                  // 忽略不压缩图片的大小
            .setCompressListener(object : OnCompressListener {
                override fun onStart() {
                    start()
                }

                override fun onSuccess(file: File?) {
                    success(file)
                }

                override fun onError(e: Throwable?) {
                    error(e)
                }
            }).launch();    //启动压缩
    }
}