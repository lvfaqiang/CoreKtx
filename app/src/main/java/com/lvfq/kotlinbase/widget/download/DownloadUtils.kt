package com.lvfq.kotlinbase.widget.download

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import cn.basic.core.ktx.launchUI
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import java.io.File

/**
 */
class DownloadUtils(
    private val mContext: Context,
    url: String,
    val name: String,
    title: String,
    desc: String,
    private val quietDownloadMode: Boolean
) {
    //下载器
    private var downloadManager: DownloadManager? = null

    //下载的ID
    private var downloadId: Long = 0

    //    private Disposable mDisposable;
    private var job: Job? = null
    var path = ""
        private set

    var success = {}
    var failed = {}
    var progress: ((Long, Long) -> Unit)? = null

    //下载apk
    @SuppressLint("CheckResult")
    private fun downloadAPK(url: String, title: String, desc: String) {
        //创建下载任务
        val request = DownloadManager.Request(Uri.parse(url))
        //移动网络情况下是否允许漫游
        request.setAllowedOverRoaming(false)
        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
//        request.setTitle(title)
//        request.setDescription(desc)
        request.setVisibleInDownloadsUi(true)

        //设置下载的路径
        val file = File(mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), name)
        request.setDestinationUri(Uri.fromFile(file))
        path = file.absolutePath
        //获取DownloadManager
        if (downloadManager == null) {
            downloadManager = mContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        }
        //将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
        if (downloadManager != null) {
            downloadId = downloadManager!!.enqueue(request)
        }
        // 前台显示下载
        if (!quietDownloadMode) {
            job = launchUI {
                while (true) {
                    delay(1000)
                    checkStatus()
                }
            }
        }
    }

    //检查下载状态
    private fun checkStatus() {
        val query = DownloadManager.Query()
        //通过下载的id查找
        query.setFilterById(downloadId)
        val cursor = downloadManager!!.query(query)
        if (cursor.moveToFirst()) {
            val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
            when (status) {
                DownloadManager.STATUS_PAUSED -> {
                }
                DownloadManager.STATUS_PENDING -> {
                }
                DownloadManager.STATUS_RUNNING -> {
                    val pro =
                        cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                    val total =
                        cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                    if (pro > 0) {
                        progress?.invoke(pro, total)
                    }
                }
                DownloadManager.STATUS_SUCCESSFUL -> {
                    cursor.close()
                    job?.cancel()
                    success.invoke()
                }
                DownloadManager.STATUS_FAILED -> {
                    cursor.close()
                    job?.cancel()
                    failed.invoke()
                }

            }
        }
    }

    fun destroy() {
        if (job?.isActive == true) {
            job?.cancel()
        }
    }

    init {
        downloadAPK(url, title, desc)
    }
}