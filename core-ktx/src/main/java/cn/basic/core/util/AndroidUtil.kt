package cn.basic.core.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

/**
 * AndroidUtil2021/11/21 12:14 下午
 * @desc :
 *
 */
object AndroidUtil {

    /**
     * Android 11 上需要添加  <uses-permission android:name="android.permission.QUERY_ALL_PACKAGES"/> 权限
     */
    fun checkPackage(context: Context, packageName: String, needToStore: Boolean = false): Boolean {
        val manager = context.packageManager
        try {
            val pkgs =
                manager.getInstalledPackages(0)
            val app = pkgs.firstOrNull { it.packageName.equals(packageName) }
            if (app != null) {
                return true
            }
        } catch (e: Exception) {
        }

        if (needToStore) {
            //手机未安装，跳转到应用商店下载，并返回false
            toMarket(context, packageName)
        }
        return false
    }

    fun toMarket(ctx: Context, packageName: String, noMarket: () -> Unit = {}) {
        val uri = Uri.parse("market://details?id=${packageName}")
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        val packageManager = ctx.packageManager
        val list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        if (list.size > 0) {
            ctx.startActivity(intent)
        } else {
            // 无法跳转到应用市场，请在主流应用市场下载，
            noMarket()
        }
    }
}