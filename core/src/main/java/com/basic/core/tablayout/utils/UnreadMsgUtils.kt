package com.basic.core.tablayout.utils

import android.text.TextUtils
import android.view.View
import android.widget.RelativeLayout
import com.basic.core.tablayout.widget.MsgView

/**
 * 未读消息提示View,显示小红点或者带有数字的红点:
 * 数字一位,圆
 * 数字两位,圆角矩形,圆角是高度的一半
 * 数字超过两位,根据传入moreType 类型显示
 */
object UnreadMsgUtils {
    fun show(msgView: MsgView?, num: Int, moreType: String?) {
        if (msgView == null) {
            return
        }
        val lp =
            msgView.layoutParams as RelativeLayout.LayoutParams
        val dm = msgView.resources.displayMetrics
        msgView.visibility = View.VISIBLE
        if (num <= 0) { //圆点,设置默认宽高
            msgView.setStrokeWidth(0)
            msgView.text = ""
            lp.width = (9 * dm.density).toInt()
            lp.height = (9 * dm.density).toInt()
            msgView.layoutParams = lp
        } else {
            lp.height = (17 * dm.density).toInt()
            //            if (num > 0 && num < 10) {//圆
//                lp.width = (int) (17 * dm.density);
//                msgView.setText(num + "");
//            } else
            if (num > 0 && num < 100) { //圆角矩形,圆角是高度的一半,设置默认padding
                lp.width = RelativeLayout.LayoutParams.WRAP_CONTENT
                msgView.setPadding((5 * dm.density).toInt(), 0, (5 * dm.density).toInt(), 0)
                msgView.text = num.toString() + ""
            } else { //数字超过两位,根据传入moreType 类型显示
                lp.width = RelativeLayout.LayoutParams.WRAP_CONTENT
                msgView.setPadding((8 * dm.density).toInt(), 0, (8 * dm.density).toInt(), 0)
                if (!TextUtils.isEmpty(moreType)) {
                    msgView.text = moreType
                }
            }
            msgView.layoutParams = lp
        }
    }

    @JvmStatic
    fun show(msgView: MsgView?, num: Int, moreType: String?, wh: Int) {
        if (msgView == null) {
            return
        }
        val lp =
            msgView.layoutParams as RelativeLayout.LayoutParams
        val dm = msgView.resources.displayMetrics
        msgView.visibility = View.VISIBLE
        if (num <= 0) { //圆点,设置默认宽高
            msgView.setStrokeWidth(0)
            msgView.text = ""
            lp.width = (9 * dm.density).toInt()
            lp.height = (9 * dm.density).toInt()
            msgView.layoutParams = lp
        } else {
            lp.height = (wh * dm.density).toInt()
            if (num > 0 && num < 100) { //圆
                lp.width = (wh * dm.density).toInt()
                msgView.text = num.toString() + ""
            } else { //数字超过两位,根据传入moreType 类型显示
                lp.width = RelativeLayout.LayoutParams.WRAP_CONTENT
                msgView.setPadding((8 * dm.density).toInt(), 0, (8 * dm.density).toInt(), 0)
                if (!TextUtils.isEmpty(moreType)) {
                    msgView.text = moreType
                }
            }
            msgView.layoutParams = lp
        }
    }

    fun setSize(rtv: MsgView?, size: Int) {
        if (rtv == null) {
            return
        }
        val lp = rtv.layoutParams as RelativeLayout.LayoutParams
        lp.width = size
        lp.height = size
        rtv.layoutParams = lp
    }
}