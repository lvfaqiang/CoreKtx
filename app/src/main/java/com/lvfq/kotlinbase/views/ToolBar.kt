package com.lvfq.kotlinbase.views

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import com.lvfq.kotlinbase.R
import com.lvfq.kotlinbase.databinding.LayoutToolBarBinding
import com.lvfq.kotlinbase.kotlinx.Position
import com.lvfq.kotlinbase.kotlinx.drawable

/**
 * TopBar
 * @author FaQiang on 2018/1/3 上午10:19
 * @desc :
 *
 */
class ToolBar
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RelativeLayout(context, attrs, defStyleAttr) {

    private var leftImg: Int = 0
    private var titleText: String = ""
    private var titleSize = 16f

    // 0xff000000 等同于 #000000
    private var titleColor: Int = 0xff000000.toInt()
    private var rightText: String = ""
    private var rightTextSize = 16f
    private var rightTextColor = 0xff000000.toInt()

    private lateinit var binding: LayoutToolBarBinding

    init {
        val binding = LayoutToolBarBinding.inflate(LayoutInflater.from(getContext()), this, false)

        context.withStyledAttributes(attrs, R.styleable.ToolBar) {
            leftImg = getResourceId(R.styleable.ToolBar_leftImg, leftImg)
            titleText = getString(R.styleable.ToolBar_titleText) ?: titleText
            titleSize = getInt(R.styleable.ToolBar_titleSize, titleSize.toInt()).toFloat()
            titleColor = getColor(R.styleable.ToolBar_titleColor, titleColor)

            rightText = getString(R.styleable.ToolBar_rightText) ?: rightText
            rightTextSize =
                getInt(R.styleable.ToolBar_rightTextSize, rightTextSize.toInt()).toFloat()
            rightTextColor = getColor(R.styleable.ToolBar_rightTextColor, rightTextColor)
        }

        binding.toolBarTvTitle.apply {
            setTextColor(titleColor)
            textSize = titleSize
            text = titleText
        }
        binding.toolBarTvRight.apply {
            setTextColor(rightTextColor)
            text = rightText
            textSize = rightTextSize
        }

    }


    fun setBack(activity: Activity?, drawId: Int = 0): ToolBar {
        if (drawId == 0) {
//            toolBar_tv_left.drawable(R.drawable.icon_back, Position.LEFT)
        } else {
            binding.toolBarTvLeft.drawable(drawId, Position.LEFT)
        }
        binding.toolBarTvLeft.setOnClickListener {
            activity?.finish()
        }
        return this
    }

    fun setLeft(string: String, click: (View) -> Unit): ToolBar {
        binding.toolBarTvLeft.text = string
        binding.toolBarTvLeft.setOnClickListener(click)
        return this
    }

    fun setLeft(string: String, click: (View) -> Unit, block: TextView.() -> Unit = {}) {
        binding.toolBarTvLeft.text = string
        binding.toolBarTvLeft.setOnClickListener(click)
        binding.toolBarTvLeft.apply(block)
    }


    fun setTitle(title: String, click: ((View) -> Unit)? = null): ToolBar {
        binding.toolBarTvTitle.text = title
        click?.let {
            binding.toolBarTvTitle.setOnClickListener(it)
        }
        return this
    }

    /**
     * 适用于右上角文本，带图标，可在 block 中自行添加图标显示，
     */
    fun setRight(label: String, click: (View) -> Unit, block: TextView.() -> Unit = {}): ToolBar {
        binding.toolBarTvRight.text = label
        binding.toolBarTvRight.setOnClickListener(click)
        binding.toolBarTvRight.apply(block)
        return this
    }

    /**
     * 右边位置 只显示图标，无文本。
     */
    fun setRight(drawId: Int, click: (View) -> Unit): ToolBar {
        binding.toolBarTvRight.drawable(drawId, Position.RIGHT)
        binding.toolBarTvRight.setOnClickListener(click)
        return this
    }


//    /**
//     * 设置当前界面返回
//     */
//    fun setBack(activity: Activity): ToolBar {
//        visible(leftImageView)
//        leftImageView.setImageResource(leftImg)
//        leftImageView.setOnClickListener {
//            activity.finish()
//        }
//        return this
//    }
//
//    /**
//     * 设置标题
//     */
//    fun setTitle(title: String): ToolBar {
//        visible(titleTextView)
//        titleTextView.text = title
//        return this
//    }
//
//    private fun visible(view: View) {
//        view.visibility = View.VISIBLE
//    }
}