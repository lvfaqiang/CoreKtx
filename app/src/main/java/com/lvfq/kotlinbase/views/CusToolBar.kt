package com.lvfq.kotlinbase.views

import android.app.Activity
import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.lvfq.kotlinbase.R
import com.lvfq.library.utils.DPUtil

/**
 * TopBar
 * @author FaQiang on 2018/1/3 上午10:19
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
class CusToolBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : RelativeLayout(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    private var leftImg: Int = 0
    private var titleText: String = ""
    private var titleSize = 16f
    // 0xff000000 等同于 #000000
    private var titleColor: Int = 0xff000000.toInt()
    private var rightText: String = ""
    private var rightTextSize = 16f
    private var rightTextColor = 0xff000000.toInt()

    val leftImageView by lazy {
        ImageView(context).apply {
            layoutParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                setMargins(DPUtil.dip2px(5f), 0, 0, 0)
                addRule(ALIGN_PARENT_LEFT)
                addRule(CENTER_VERTICAL)
            }
            setPadding(DPUtil.dip2px(10f), 0, DPUtil.dip2px(10f), 0)
            visibility = View.GONE
        }
    }


    val titleTextView by lazy {
        TextView(context).apply {
            textSize = titleSize
            text = titleText
            setTextColor(titleColor)
            layoutParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                addRule(CENTER_IN_PARENT)
            }
            visibility = View.GONE
        }
    }

    val rightTextView by lazy {
        TextView(context).apply {
            textSize = rightTextSize
            text = rightText
            setTextColor(rightTextColor)
            layoutParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                setMargins(0, 0, DPUtil.dip2px(15f), 0)
                addRule(ALIGN_PARENT_RIGHT)
                addRule(CENTER_VERTICAL)
            }
        }
    }

    init {

        val types = context.obtainStyledAttributes(attrs, R.styleable.CusToolBar)

        leftImg = types.getResourceId(R.styleable.CusToolBar_leftImg, leftImg)
        titleText = types.getString(R.styleable.CusToolBar_titleText) ?: titleText
        titleSize = types.getInt(R.styleable.CusToolBar_titleSize, titleSize.toInt()).toFloat()
        titleColor = types.getColor(R.styleable.CusToolBar_titleColor, titleColor)

        rightText = types.getString(R.styleable.CusToolBar_rightText) ?: rightText
        rightTextSize = types.getInt(R.styleable.CusToolBar_rightTextSize, rightTextSize.toInt()).toFloat()
        rightTextColor = types.getColor(R.styleable.CusToolBar_rightTextColor, rightTextColor)

        types.recycle()

        leftImageView.visibility = if (leftImg == 0) View.GONE else View.VISIBLE
        titleTextView.visibility = if (titleText.isEmpty()) View.GONE else View.VISIBLE
        rightTextView.visibility = if (TextUtils.isEmpty(rightText)) View.GONE else View.VISIBLE
        background
        initUI()
    }

    private fun initUI() {
        addView(leftImageView)
        addView(titleTextView)
        addView(rightTextView)

        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                } else {
                    viewTreeObserver.removeGlobalOnLayoutListener(this)
                }
                layoutParams.height = DPUtil.dip2px(45f)
            }
        })
    }

    /**
     * 设置当前界面返回
     */
    fun setBack(activity: Activity): CusToolBar {
        visible(leftImageView)
        leftImageView.setImageResource(leftImg)
        leftImageView.setOnClickListener {
            activity.finish()
        }
        return this
    }

    /**
     * 设置标题
     */
    fun setTitle(title: String): CusToolBar {
        visible(titleTextView)
        titleTextView.text = title
        return this
    }

    private fun visible(view: View) {
        view.visibility = View.VISIBLE
    }
}