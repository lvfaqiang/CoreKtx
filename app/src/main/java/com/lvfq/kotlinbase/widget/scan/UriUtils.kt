package com.lvfq.kotlinbase.widget.scan

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log

class UriUtils private constructor() {
    companion object {
        /**
         * 获取图片
         */
        fun getImagePath(context: Context, data: Intent): String {
            var imagePath = ""
            val uri = data.data
            //获取系統版本
            val currentapiVersion = Build.VERSION.SDK_INT
            if (currentapiVersion > Build.VERSION_CODES.KITKAT) {
                Log.d("uri=intent.getData :", "" + uri)
                if (DocumentsContract.isDocumentUri(context, uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    Log.d("getDocumentId(uri) :", "" + docId)
                    Log.d("uri.getAuthority() :", "" + uri!!.authority)
                    if ("com.android.providers.media.documents" == uri.authority) {
                        val id = docId.split(":").toTypedArray()[1]
                        val selection = MediaStore.Images.Media._ID + "=" + id
                        imagePath = getImagePath(
                            context,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            selection
                        )
                    } else if ("com.android.providers.downloads.documents" == uri.authority) {
                        val contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"),
                            java.lang.Long.valueOf(docId)
                        )
                        imagePath = getImagePath(context, contentUri, null)
                    }
                } else if ("content".equals(uri!!.scheme, ignoreCase = true)) {
                    imagePath = getImagePath(context, uri, null)
                }
            } else {
                imagePath = getImagePath(context, uri, null)
            }
            return imagePath
        }

        /**
         * 通过uri和selection来获取真实的图片路径,从相册获取图片时要用
         */
        private fun getImagePath(context: Context, uri: Uri?, selection: String?): String {
            var path: String = ""
            val cursor = context.contentResolver.query(uri!!, null, selection, null, null)
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
                }
                cursor.close()
            }
            return path
        }
    }

    init {
        throw AssertionError()
    }
}