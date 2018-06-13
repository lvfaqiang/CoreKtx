package com.lvfq.kotlinbase.views

import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import com.lvfq.kotlinbase.R
import com.lvfq.kotlinbase.ext.gone
import com.lvfq.kotlinbase.ext.show
import com.lvfq.kotlinbase.utils.NumRangeInputFilter
import com.lvfq.library.utils.DPUtil
import com.lvfq.library.utils.V

/**
 * InputEditText
 * @author FaQiang on 2018/6/5 下午6:42
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
class InputEditText(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attributeSet, defStyleAttr) {

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null)


    private var singleLine = false
    private var textSize = 14f
    private var paddLeft = 0
    private var textColor = android.R.color.black
    private var textColorHint = android.R.color.black
    private var hint = ""
    private var inputType = InputType.TYPE_CLASS_TEXT
    private var maxLength = -1
    var textChangeListener: OnTextChangedListener? = null


    interface OnTextChangedListener {
        fun onTextChanged(s: CharSequence)
    }


    private val rightIv by lazy {
        ImageView(context).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                id = View.generateViewId()
            } else {
                id = 1111.toInt()
            }
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT).apply {
                addRule(ALIGN_PARENT_RIGHT)
                addRule(CENTER_VERTICAL)
            }
            setPadding(DPUtil.dip2px(10f), 0, DPUtil.dip2px(10f), 0)
        }
    }

    private val editText by lazy {
        EditText(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).apply {
                addRule(LEFT_OF, rightIv.id)
                addRule(CENTER_VERTICAL)
            }
            gravity = Gravity.CENTER_VERTICAL

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                background = null
            } else {
                setBackgroundDrawable(null)
            }
        }
    }

    init {

        val dm = resources.displayMetrics
        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize, dm)
        paddLeft = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, paddLeft.toFloat(), dm).toInt()

        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.InputEditText)
        singleLine = ta.getBoolean(R.styleable.InputEditText_android_singleLine, singleLine)

        textSize = ta.getDimension(R.styleable.InputEditText_android_textSize, textSize)
        paddLeft = ta.getDimensionPixelSize(R.styleable.InputEditText_editPaddingLeft, paddLeft)

        textColor = ta.getResourceId(R.styleable.InputEditText_android_textColor, textColor)
        textColorHint = ta.getResourceId(R.styleable.InputEditText_android_textColorHint, textColorHint)
        inputType = ta.getInt(R.styleable.InputEditText_android_inputType, inputType)
        hint = ta.getString(R.styleable.InputEditText_android_hint)
        maxLength = ta.getInt(R.styleable.InputEditText_android_maxLength, maxLength)

        ta.recycle()

        editText.textSize = DPUtil.px2dip(textSize).toFloat()
        editText.setPadding(paddLeft, 0, 0, 0)
        editText.hint = hint
        editText.setSingleLine(singleLine)
        editText.setTextColor(resources.getColor(textColor))
        editText.setHintTextColor(resources.getColor(textColorHint))
        editText.inputType = inputType

        if (maxLength != -1) {
            editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
        }

        when (inputType) {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD -> {
//                rightIv.setImageResource(R.drawable.password_eye_selector)
                rightIv.setOnClickListener {
                    it.isSelected = !it.isSelected
                    V.showPassEdit(editText, it.isSelected)
                    editText.setSelection(editText.text.toString().length)
                }
                addTextChangedListener(null)
            }
            else -> {
                rightIv.setImageResource(R.drawable.img_icon_input_close)
                rightIv.setOnClickListener { editText.setText("") }
                rightIv.gone()
                if (inputType == (InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)) {
                    editText.filters = arrayOf<InputFilter>(NumRangeInputFilter(Integer.MAX_VALUE, 8))
                }

                addTextChangedListener(TextChangedListener())
            }
        }

//        setBackgroundResource(R.drawable.shape_bg_white_corner_2)
        addView(rightIv)
        addView(editText)
    }

    fun setText(text: CharSequence) {
        editText.setText(text)
    }

    fun getText() = editText.text.toString()


    private fun addTextChangedListener(watcher: TextWatcher?) {
        watcher?.let {
            editText.addTextChangedListener(it)
        }
    }

    fun setHint(value: CharSequence) {
        editText.hint = value
    }


    private inner class TextChangedListener : TextWatcher {

        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let {
                if (it.isNotEmpty()) {
                    rightIv.show()
                } else {
                    rightIv.gone()
                }
                textChangeListener?.onTextChanged(it)
            }
        }
    }
}