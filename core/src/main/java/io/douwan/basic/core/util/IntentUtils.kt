package io.douwan.basic.core.util

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * IntentUtils2022/4/19 16:16
 * @desc :
 *
 */
object IntentUtils {

    /**
     * jump to System Settings Page.
     */
    fun gotoSystemSettings(context: Context) {
        val mIntent = Intent()
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        mIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
        mIntent.data = Uri.fromParts("package", context.packageName, null)
        context.startActivity(mIntent)
    }
}