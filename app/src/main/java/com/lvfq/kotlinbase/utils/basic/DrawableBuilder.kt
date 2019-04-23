package com.lvfq.kotlinbase.utils.basic

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue

/**
 * DrawableBuilder2019/1/17 上午10:20
 * @desc :
 *
 */
class DrawableBuilder(val context: Context) {
    private var solidColor: Int = 0
    private var strokeWidth = 0f
    private var strokColor: Int = 0
    private var radius = 0f

    private var radiusUnit = TypedValue.COMPLEX_UNIT_DIP

    private var strokeUnit = TypedValue.COMPLEX_UNIT_DIP

    fun setSolidColor(solidColor: Int): DrawableBuilder {
        this.solidColor = solidColor
        return this
    }

    fun setRadius(radius: Float): DrawableBuilder {
        this.radius = radius
        return this
    }

    fun setRadiusUnit(unit: Int): DrawableBuilder {
        radiusUnit = unit
        return this
    }

    fun setStrokeUnit(unit: Int): DrawableBuilder {
        strokeUnit = unit
        return this
    }

    fun setStrokeWidth(strokeWidth: Float): DrawableBuilder {
        this.strokeWidth = strokeWidth
        return this
    }

    fun setStrokColor(strokColor: Int): DrawableBuilder {
        this.strokColor = strokColor
        return this
    }

    fun build(): Drawable {
        val drawable = GradientDrawable().apply {
            if (solidColor != 0) {
                setColor(solidColor)
            }
            if (strokColor != 0 && strokeWidth != 0f) {
                setStroke(TypedValue.applyDimension(strokeUnit, strokeWidth, context.resources.displayMetrics).toInt(),
                        strokColor)
            }
            cornerRadius = TypedValue.applyDimension(radiusUnit, radius, context.resources.displayMetrics)
        }
        return drawable
    }
}