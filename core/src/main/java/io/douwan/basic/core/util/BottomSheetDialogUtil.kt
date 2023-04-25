package io.douwan.basic.core.util

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.douwan.basic.core.R

/**
 * BottomSheepDialogUtil2023/3/18 12:37
 * @desc :
 *
 */
object BottomSheetDialogUtil {

    fun showDialog(
        context: Context,
        contentView: View,
        autoShow: Boolean = true
    ): BottomSheetDialog {
        val dialog = BottomSheetDialog(context, R.style.BottomSheetDialogThemeTransparent)
        dialog.setContentView(contentView)
        val bottomSheetDialogContainer: FrameLayout? =
            dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheetDialogContainer?.setBackgroundResource(android.R.color.transparent);
        if (autoShow) {
            dialog.show()
        }
        return dialog
    }
}