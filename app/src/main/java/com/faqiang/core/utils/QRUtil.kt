package com.faqiang.core.utils

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder

/**
 * QRUtil2022/12/31 00:47
 * @desc :
 *
 */
object QRUtil {

    fun generateBitmap(qrContent: String, width: Int, height: Int): Bitmap? {
        val bEncoder = BarcodeEncoder()
        val encodeRes = bEncoder.encode(qrContent, BarcodeFormat.QR_CODE, width, height)
        return bEncoder.createBitmap(deleteWhite(encodeRes))
    }

    private fun deleteWhite(matrix: BitMatrix): BitMatrix {
        val rec = matrix.enclosingRectangle
        //数字2 代表了留的空白区域的大小
        val resWidth = rec[2] + 2
        val resHeight = rec[3] + 2
        val resMatrix = BitMatrix(resWidth, resHeight)
        resMatrix.clear()
        for (i in 2 until resWidth) {
            for (j in 2 until resHeight) {
                if (matrix[i + rec[0], j + rec[1]]) resMatrix[i] = j
            }
        }
        return resMatrix
    }
}