package com.lvfq.kotlinbase.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.*
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.fq.library.kotlin.ex.getDraw
import com.fq.library.kotlin.ex.px2dp
import com.fq.library.kotlin.ex.view.gone
import com.fq.library.kotlin.ex.view.visible
import com.lvfq.kotlinbase.R

/**
 * InputEditText
 * @author FaQiang on 2018/6/5 下午6:42
 * @desc :
 *
 */
class InputEditText
@JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0)
    : RelativeLayout(context, attributeSet, defStyleAttr) {

    private var singleLine = true
    private var textSize = 14f
    private var paddLeft = 0
    private var textColor = android.R.color.black
    private var textColorHint = android.R.color.black
    private var hint = ""
    private var maxLength = -1
    private var pointerLength = 8   // 小数点后面保留位数

    private var inputType = InputType.TYPE_CLASS_TEXT
    private var drawableLeft: Drawable? = null
    private var drawablePadding = 0
    private var showPasswordResource: Drawable? = null
    private var hidePasswordResource: Drawable? = null
    private var closeResource: Drawable? = null
    private var cursorResId = 0

    private var showClose = true
    private var showPasswordToggle = false
    private var enable = true

    var textChangeListener: OnTextChangedListener? = null


    interface OnTextChangedListener {
        fun onTextChanged(s: CharSequence)
    }


    private val rightIv by lazy {
        ImageView(context).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                id = View.generateViewId()
            } else {
                id = 1111.toInt()
            }
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT).apply {
                addRule(ALIGN_PARENT_RIGHT)
                addRule(CENTER_VERTICAL)
            }
        }
    }

    private val rightDelIv by lazy {
        ImageView(context).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                id = View.generateViewId()
            } else {
                id = 2222.toInt()
            }
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT).apply {
                addRule(CENTER_VERTICAL)
                if (showPasswordToggle) {
                    addRule(LEFT_OF, rightIv.id)
                } else {
                    addRule(ALIGN_PARENT_RIGHT)
                }
            }
            visibility = View.GONE
            setImageDrawable(closeResource)
        }
    }

    val editText by lazy {
        EditText(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).apply {
                addRule(CENTER_VERTICAL)
                addRule(LEFT_OF, rightDelIv.id)
            }
            ellipsize = TextUtils.TruncateAt.END
            gravity = Gravity.CENTER_VERTICAL
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                background = null
            } else {
                setBackgroundDrawable(null)
            }
            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)
            compoundDrawablePadding = drawablePadding
            imeOptions = EditorInfo.IME_FLAG_NO_EXTRACT_UI
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
        hint = ta.getString(R.styleable.InputEditText_android_hint) ?: hint
        maxLength = ta.getInt(R.styleable.InputEditText_android_maxLength, maxLength)
        pointerLength = ta.getInt(R.styleable.InputEditText_pointerLength, pointerLength)

        drawableLeft = ta.getDrawable(R.styleable.InputEditText_android_drawableLeft)
        drawablePadding = ta.getDimensionPixelSize(R.styleable.InputEditText_android_drawablePadding, drawablePadding)

        showPasswordResource = ta.getDrawable(R.styleable.InputEditText_showPasswordResource)
        hidePasswordResource = ta.getDrawable(R.styleable.InputEditText_hidePasswordResource)

        closeResource = ta.getDrawable(R.styleable.InputEditText_closeResource)

        showClose = ta.getBoolean(R.styleable.InputEditText_showClose, showClose)
        showPasswordToggle = ta.getBoolean(R.styleable.InputEditText_showPasswordToggle, showPasswordToggle)
        enable = ta.getBoolean(R.styleable.InputEditText_canInput, enable)

        cursorResId = ta.getResourceId(R.styleable.InputEditText_android_textCursorDrawable, cursorResId)


        ta.recycle()

        if (closeResource == null) {
            closeResource = resources.getDraw(R.drawable.img_icon_input_close)
        }
        if (showPasswordResource == null) {
//            showPasswordResource = resources.getDrawable(R.drawable.icon_input_eye_show)
        }
        if (hidePasswordResource == null) {
//            hidePasswordResource = resources.getDrawable(R.drawable.icon_input_eye_hide)
        }

        editText.textSize = context.px2dp(textSize).toFloat()
        editText.setPadding(paddLeft, 0, 0, 0)
        editText.hint = hint
        editText.setSingleLine(singleLine)
        editText.setTextColor(resources.getColor(textColor))
        editText.setHintTextColor(resources.getColor(textColorHint))
        editText.inputType = inputType

        editText.isCursorVisible = enable
        editText.isFocusableInTouchMode = enable
        if (cursorResId == 0) {
            cursorResId = R.drawable.draw_cursor
        }

        if (cursorResId != 0) {
            try {
                val field = TextView::class.java.getDeclaredField("mCursorDrawableRes")
                field.isAccessible = true
                field.set(editText, cursorResId)
            } catch (e: Exception) {
            }
        }

        if (maxLength != -1) {
            editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
        }

        when (inputType) {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD -> {
                rightIv.setImageDrawable(hidePasswordResource)
                rightIv.setOnClickListener {
                    it.isSelected = !it.isSelected
                    rightIv.setImageDrawable(
                            if (it.isSelected)
                                showPasswordResource
                            else
                                hidePasswordResource
                    )
                    showPassEdit(editText, it.isSelected)
                    editText.setSelection(editText.text.toString().length)
                }
                if (showPasswordToggle) {
                    rightIv.visible()
                } else {
                    rightIv.gone()
                }
            }
            else -> {
                rightDelIv.setOnClickListener { editText.setText("") }
                if (inputType == (InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)) {
                    editText.filters = arrayOf<InputFilter>(NumRangeInputFilter(Integer.MAX_VALUE, pointerLength))
                }
                rightIv.gone()
            }
        }

        rightDelIv.setOnClickListener { editText.setText("") }

        addTextChangedListener(TextChangedListener())
        addView(rightIv)
        addView(rightDelIv)
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

    fun getHint() = editText.hint.toString()

    override fun setOnClickListener(l: OnClickListener?) {
        editText.setOnClickListener(l)
    }


    private inner class TextChangedListener : TextWatcher {

        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let {
                if (showClose) {
                    if (it.isNotEmpty()) {
                        rightDelIv.visible()
                    } else {
                        rightDelIv.gone()
                    }
                }
                textChangeListener?.onTextChanged(it)
            }
        }
    }

    /**
     * 是否显示明文密码
     *
     * @param editText
     * @param isShow
     */
    private fun showPassEdit(editText: EditText, isShow: Boolean) {
        if (isShow) {
            // 明文
            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else
        // 密文
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
    }
}