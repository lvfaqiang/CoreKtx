package com.lvfq.kotlinbase.views

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import com.fq.library.kotlin.ex.view.Position
import com.fq.library.kotlin.ex.view.drawable
import com.lvfq.kotlinbase.R
import kotlinx.android.synthetic.main.layout_tool_bar.view.*

/**
 * TopBar
 * @author FaQiang on 2018/1/3 上午10:19
 * @desc :
 *
 */
class ToolBar
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : RelativeLayout(context, attrs, defStyleAttr) {

    private var leftImg: Int = 0
    private var titleText: String = ""
    private var titleSize = 16f
    // 0xff000000 等同于 #000000
    private var titleColor: Int = 0xff000000.toInt()
    private var rightText: String = ""
    private var rightTextSize = 16f
    private var rightTextColor = 0xff000000.toInt()


    init {
        View.inflate(context, R.layout.layout_tool_bar, this)


        context.withStyledAttributes(attrs, R.styleable.ToolBar) {
            leftImg = getResourceId(R.styleable.ToolBar_leftImg, leftImg)
            titleText = getString(R.styleable.ToolBar_titleText) ?: titleText
            titleSize = getInt(R.styleable.ToolBar_titleSize, titleSize.toInt()).toFloat()
            titleColor = getColor(R.styleable.ToolBar_titleColor, titleColor)

            rightText = getString(R.styleable.ToolBar_rightText) ?: rightText
            rightTextSize = getInt(R.styleable.ToolBar_rightTextSize, rightTextSize.toInt()).toFloat()
            rightTextColor = getColor(R.styleable.ToolBar_rightTextColor, rightTextColor)
        }

        toolBar_tv_title.apply {
            setTextColor(titleColor)
            textSize = titleSize
            text = titleText
        }
        toolBar_tv_right.apply {
            setTextColor(rightTextColor)
            text = rightText
            textSize = rightTextSize
        }

    }


    fun setBack(activity: Activity?, drawId: Int = 0): ToolBar {
        if (drawId == 0) {
//            toolBar_tv_left.drawable(R.drawable.icon_back, Position.LEFT)
        } else {
            toolBar_tv_left.drawable(drawId, Position.LEFT)
        }
        toolBar_tv_left.setOnClickListener {
            activity?.finish()
        }
        return this
    }

    fun setLeft(string: String, click: (View) -> Unit): ToolBar {
        toolBar_tv_left.text = string
        toolBar_tv_left.setOnClickListener(click)
        return this
    }

    fun setLeft(string: String, click: (View) -> Unit, block: TextView.() -> Unit = {}) {
        toolBar_tv_left.text = string
        toolBar_tv_left.setOnClickListener(click)
        toolBar_tv_left.apply(block)
    }


    fun setTitle(title: String, click: ((View) -> Unit)? = null): ToolBar {
        toolBar_tv_title.text = title
        click?.let {
            toolBar_tv_title.setOnClickListener(it)
        }
        return this
    }

    /**
     * 适用于右上角文本，带图标，可在 block 中自行添加图标显示，
     */
    fun setRight(label: String, click: (View) -> Unit, block: TextView.() -> Unit = {}): ToolBar {
        toolBar_tv_right.text = label
        toolBar_tv_right.setOnClickListener(click)
        toolBar_tv_right.apply(block)
        return this
    }

    /**
     * 右边位置 只显示图标，无文本。
     */
    fun setRight(drawId: Int, click: (View) -> Unit): ToolBar {
        toolBar_tv_right.drawable(drawId, Position.RIGHT)
        toolBar_tv_right.setOnClickListener(click)
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