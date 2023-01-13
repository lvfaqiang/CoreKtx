package io.douwan.basic.core.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Base64
import android.view.View
import java.io.ByteArrayOutputStream

/**
 * FileUtil2023/1/13 22:59
 * @desc :
 *
 */
object FileUtil {

    fun convertBase64ToPic(base64: String): Bitmap? {
        var value = base64
        if (base64.contains(",")) {
            value = base64.split(",")[1]
        }
        val decode = Base64.decode(value, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decode, 0, decode.size)
    }

    fun convertBitmapToBase64(bitmap: Bitmap): String {
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos) //参数100表示不压缩
        val bytes = bos.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }


    fun saveViewAsBitmap(view: View): Bitmap? {
        var width: Int = view.width
        var height: Int = view.height
        if (width <= 0 || height <= 0) {
            val specSize: Int =
                View.MeasureSpec.makeMeasureSpec(0 /* any */, View.MeasureSpec.UNSPECIFIED)
            view.measure(specSize, specSize)
            width = view.measuredWidth
            height = view.measuredHeight
        }
        if (width <= 0 || height <= 0) {
            return null
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        if (view.right <= 0 || view.bottom <= 0) {
            view.layout(0, 0, width, height)
            view.draw(canvas)
        } else {
            view.layout(view.left, view.top, view.right, view.bottom)
            view.draw(canvas)
        }
        return bitmap
    }
}