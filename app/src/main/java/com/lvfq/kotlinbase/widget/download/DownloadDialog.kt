package com.lvfq.kotlinbase.widget.download

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import com.lvfq.kotlinbase.App
import com.lvfq.kotlinbase.R
import com.lvfq.kotlinbase.builder.DialogBuilder
import com.lvfq.kotlinbase.databinding.DialogDownloadBinding
import com.lvfq.kotlinbase.kotlinx.versionName
import com.lvfq.kotlinbase.kotlinx.view.click
import java.io.File
import java.io.IOException

/**
 * DownloadDialog2021/2/23 3:57 PM
 * @desc :
 * 下载弹框
 *
 */
class DownloadDialog private constructor(private val context: Context) {

    companion object {
        fun get(context: Context): DownloadDialog {
            return DownloadDialog(context)
        }
    }

    private var downloadUtil: DownloadUtils? = null
    private val dialogBinding = DialogDownloadBinding.inflate(LayoutInflater.from(context))
    private var dialog: Dialog? = null

    private var downloadUrl = ""
    private var updateContent = ""
    private var isForce = false // 是否强制更新

    private var cancelClick = {}
    private var confirmClick = {}

    private var success = {}
    private var failed = {}

    init {
        initDialog()
    }

    private fun initDialog() {
        dialogBinding.tvCancel.click {
            dialog?.cancel()
            if (isForce) {
                App.exit()
            }
        }
        dialogBinding.tvUpdate.click {
            startDownload()
        }

        dialog?.setOnDismissListener {
            downloadUtil?.destroy()
        }
    }

    private fun startDownload() {
        dialogBinding.llContent.isVisible = false
        dialogBinding.llProgress.isVisible = true

        try {
            downloadUtil = DownloadUtils(
                context, downloadUrl,
                "${context.getString(R.string.app_name)}_v${context.versionName}.apk",
                "", "",
                false
            ).apply {

                success = {
                    installApk()
                    this@DownloadDialog.success.invoke()
                    dialog?.dismiss()
                    App.exit()
                }

                failed = {
                    this@DownloadDialog.failed.invoke()
                }

                progress = { progress, total ->
                    val value = (progress / (total * 1.0) * 100).toInt()
                    dialogBinding.progressBar.progress = value
                    dialogBinding.tvProgress.text = "$value%"

                }
            }
        } catch (e: Exception) {
            failed.invoke()
        }
    }

    fun downloadUrl(url: String): DownloadDialog {
        downloadUrl = url
        return this
    }

    fun onSuccess(success: () -> Unit): DownloadDialog {
        this.success = success
        return this
    }

    fun onFailed(failed: () -> Unit): DownloadDialog {
        this.failed = failed
        return this
    }

    fun isForce(force: Boolean): DownloadDialog {
        isForce = force
        return this
    }

    fun updateContent(content: String): DownloadDialog {
        updateContent = content
        return this
    }

    fun show(): Dialog? {
        dialogBinding.tvContent.text = updateContent

        dialog = DialogBuilder.get(context)
            .setGravity(Gravity.CENTER)
            .setCanceledOnTouchOutside(false)
            .setCancelable(false)
            .setWidth(0.8f)
            .build(dialogBinding.root)
        return dialog
    }

    private fun installApk() {
        val pathstr: String = downloadUtil?.path ?: ""
        val file = File(pathstr)
        setPermission(pathstr)
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        // 执行动作
        intent.action = Intent.ACTION_VIEW
        // 执行的数据类型
        var uri: Uri? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(
                context.applicationContext,
                "${context.packageName}.fileprovider",
                file
            )
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        } else {
            uri = Uri.fromFile(file)
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive")
        context.startActivity(intent)
    }

    //修改文件权限
    private fun setPermission(absolutePath: String) {
        val command = "chmod 777 $absolutePath"
        val runtime = Runtime.getRuntime()
        try {
            runtime.exec(command)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}