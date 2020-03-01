package com.lvfq.kotlinbase.utils.tool

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lvfq.kotlinbase.Const.AppConst
import com.lvfq.kotlinbase.R
import com.lvfq.kotlinbase.kotlinx.scheduler.applyScheduler
import com.lvfq.kotlinbase.views.SwipeRefreshView
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * ViewUtil
 * @author FaQiang on 2018/9/28 上午11:06
 * @desc :
 *
 */
object ViewUtil {

    /**
     * @param view  如果 editTexts 中有输入框是未输入内容的，则 view 为不可点击状态，以及 isSelected = false 状态
     * @param editTexts
     */
    fun bindEditChangeSelectState(
        view: View,
        vararg editTexts: EditText,
        block: (hasEmpty: Boolean) -> Unit = {}
    ) {

        fun checkInputValue(): Int {
            val inputSize = editTexts.filter { editText ->
                editText.text.toString().isEmpty()
            }.size
            view.apply {
                isSelected = !(inputSize > 0)
                isClickable = !(inputSize > 0)
            }
            return inputSize
        }

        checkInputValue()

        val testWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                block.invoke(checkInputValue() > 0)
            }

        }
        editTexts.map { edit ->
            edit.addTextChangedListener(testWatcher)
        }
    }


    fun countDown(
        textView: TextView,
        owner: LifecycleOwner,
        resetLabel: Int = 0,
        sendingLabel: Int = 0,
        seconds: Long = 60
    ): Disposable? {
        val context = textView.context

        val resetStrId = if (resetLabel == 0) R.string.str_send_code_resend else resetLabel
        val sendingStrId =
            if (sendingLabel == 0) R.string.str_send_code_count_down_label_s else sendingLabel

        textView.isClickable = false
        textView.isSelected = false

        textView.text = context.getString(sendingStrId, seconds.toString())
        return Observable.interval(1000, TimeUnit.MILLISECONDS)
            .take(seconds)
            .applyScheduler()
            .subscribe({ it ->
                val result = seconds - (it + 1)
                textView.text = context.getString(sendingStrId, result.toString())
            }, {}, {
                textView.isClickable = true
                textView.isSelected = true
                textView.text = context.getString(resetStrId)
            })
    }

    fun <T : Any> loadComplete(
        list: ArrayList<T>,
        swipeRefreshView: SwipeRefreshView,
        adapter: BaseQuickAdapter<T, BaseViewHolder>?
    ) {
        if (swipeRefreshView.pageNo == AppConst.PAGE) {
            adapter?.setNewData(list)
        } else {
            adapter?.addData(list)
        }
        if (list.size < AppConst.PAGE_SIZE) {
            swipeRefreshView.loadMoreEnd(true)
        } else {
            swipeRefreshView.loadMoreComplete()
        }
    }
}