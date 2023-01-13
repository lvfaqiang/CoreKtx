package io.douwan.basic.core.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.StrictMode
import android.provider.MediaStore
import android.text.TextUtils
import java.util.*

/**
 * ShareUtil2023/1/13 23:11
 * @desc :
 *
 */
object ShareUtil {

    /**
     * 分享文本
     * @param context
     * @param path
     */
    fun shareText(context: Context, path: String?) {
        if (TextUtils.isEmpty(path)) {
            return
        }
        checkFileUriExposure()
        val it = Intent(Intent.ACTION_SEND)
        it.putExtra(Intent.EXTRA_TEXT, path)
        it.type = "text/plain"
        context.startActivity(Intent.createChooser(it, getAppName(context)))
    }

    /**
     * 分享图片
     */
    fun shareImage(context: Context, bitmap: Bitmap) {
        val uri = Uri.parse(
            MediaStore.Images.Media.insertImage(
                context.contentResolver,
                bitmap,
                "IMG" + Calendar.getInstance().getTime(),
                null
            )
        )
        checkFileUriExposure()
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, uri)
        }
        context.startActivity(Intent.createChooser(intent, getAppName(context)))
    }

    /**
     *
     * 分享前必须执行本代码，主要用于兼容SDK18以上的系统
     *
     */
    private fun checkFileUriExposure() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            val builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
            builder.detectFileUriExposure()
        }
    }

    /**
     * 获取app的名称
     * @param context
     * @return
     */
    private fun getAppName(context: Context): String? {
        var appName = ""
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(
                context.packageName, 0
            )
            val labelRes = packageInfo.applicationInfo.labelRes
            appName = context.resources.getString(labelRes)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return appName
    }

}