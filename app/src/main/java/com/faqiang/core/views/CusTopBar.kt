package com.faqiang.core.views

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.faqiang.core.databinding.LayoutTopbarBinding
import io.douwan.basic.core.ktx.click
import io.douwan.basic.core.ktx.dp2px

/**
 * CusTopBar2023/4/5 00:58
 * @desc :
 *
 */
class CusTopBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    val binding = LayoutTopbarBinding.inflate(LayoutInflater.from(context))

    init {
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER_VERTICAL
        addView(binding.root, LayoutParams.MATCH_PARENT, context.dp2px(46f).toInt())
    }

    fun setLeftBack(activity: Activity?): CusTopBar {
        binding.backLayout.isVisible = true
        binding.backLayout.click {
            activity?.finish()
        }
        return this
    }

    fun setLeft(resId: Int, click: () -> Unit): CusTopBar {
        binding.backLayout.isVisible = true
        binding.ivBack.setImageResource(resId)
        binding.backLayout.click {
            click()
        }
        return this
    }


    fun setRight(resId: Int, click: () -> Unit): CusTopBar {
        binding.rightLayout.isVisible = true
        binding.ivRight.setImageResource(resId)
        binding.rightLayout.click {
            click()
        }
        return this
    }

    fun setTitle(title: String): CusTopBar {
        binding.tvTitle.text = title
        return this
    }


}