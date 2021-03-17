package com.lvfq.kotlinbase.widget.scan

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.lifecycle.lifecycleScope
import cn.basic.core.ktx.launchIO
import com.google.zxing.Result
import com.king.zxing.CaptureActivity
import com.lvfq.kotlinbase.R
import cn.basic.core.ktx.launchUI
import com.king.zxing.util.CodeUtils

/**
 * ScanActivity2021/2/25 10:22 AM
 * @desc :
 *
 */
class ScanActivity : CaptureActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_scan
    }

    companion object {
        private const val REQUEST_CODE_PHOTO = 111

        const val SCAN_RESULT = "scanResult"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val toolBar: ToolBar = findViewById(R.id.toolBar)
//        toolBar.setBack(this)
//        toolBar.setRight(getString(R.string.album), {
//            startPhotoCode()
//        }, {
//            setTextColor(resources.getColor(R.color.white))
//        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) return
        when (requestCode) {
            REQUEST_CODE_PHOTO -> {
                val path = UriUtils.getImagePath(this, data ?: return)
                parsePhoto(path)
            }
        }
    }

    override fun onScanResultCallback(result: Result?): Boolean {
        result?.let {
            callBackResult(result.text)
            return true
        }
        return super.onScanResultCallback(result)
    }

    private fun parsePhoto(path: String) {
        launchUI(this.lifecycleScope) {
            val result = lifecycleScope.launchIO {
                CodeUtils.parseCode(path)
            }
            callBackResult(result)
        }
    }

    private fun callBackResult(result: String) {
        val intent = Intent()
        intent.putExtra(SCAN_RESULT, result)
        setResult(RESULT_OK, intent)
        finish()
    }

    /**
     * 跳转去相册选择图片
     */
    private fun startPhotoCode() {
        val pickIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(
            pickIntent,
            REQUEST_CODE_PHOTO
        )
    }


}