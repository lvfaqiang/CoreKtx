package com.lvfq.kotlinbase.builder

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.viewpager.widget.ViewPager
import com.lvfq.kotlinbase.kotlinx.getColorById
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView

/**
 * MagicIndicator
 * @author FaQiang on 2018/8/30 上午9:05
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */

class MagicBuilder private constructor(private val context: Context) {

    companion object {
        fun get(context: Context): MagicBuilder {
            return MagicBuilder(context)
        }
    }

    /**
     * ⚠️   颜色值需要传入 R.color.id 即可
     */
    private var titleNorColor: Int = 0
    private var titleSelColor: Int = 0
    private var titleSize: Float = 15f
    private var titleSelSize: Float = 15f
    private var indicatorColor: Int = 0 // 指示器颜色
    private var indicatorHeight: Float = 0f // 指示器高度
    private var indicatorWidth: Float = 20f  // 指示器宽度
    private var intrinsicWidth: Float = 0f    // 内在宽度
    private var datas: ArrayList<String> = ArrayList()
    private var viewPager: ViewPager? = null
    private var indicator: MagicIndicator? = null

    private var isAdjustMode: Boolean = false // 是否均分， 适用少量的 Tab 数量。

    private var isTitleBold = false // 标题是否加粗
    private var titleSelBig = false // 选中标题变大

    private var customTitleView: ((Context, Int) -> IPagerTitleView)? = null


    /**
     * 不为空时，其他指示器的配置失效。
     */
    private var customIndicator: IPagerIndicator? = null    //自定义指示器


    fun setAdjustMode(isAdjustMode: Boolean): MagicBuilder {
        this.isAdjustMode = isAdjustMode
        return this
    }

    fun setTitleNorColor(@ColorRes color: Int): MagicBuilder {
        titleNorColor = context.resources.getColorById(color)
        return this
    }

    fun setTitleSelColor(@ColorRes color: Int): MagicBuilder {
        titleSelColor = context.resources.getColorById(color)
        return this
    }

    fun setTitleSize(textSize: Float): MagicBuilder {
        titleSize = textSize
        return this
    }

    fun setTitleSelSize(textSize: Float): MagicBuilder {
        titleSelSize = textSize
        return this
    }

    fun setIndicatorColor(@ColorRes color: Int): MagicBuilder {
        indicatorColor = context.resources.getColorById(color)
        return this
    }

    fun setIndicatorHeight(height: Float): MagicBuilder {
        indicatorHeight = height
        return this
    }

    fun setIndicatorWidth(width: Float): MagicBuilder {
        indicatorWidth = width
        return this
    }

    fun setIntrinsicWidth(width: Float): MagicBuilder {
        intrinsicWidth = width
        return this
    }

    /**
     * 设置标题是否加粗
     *
     * @param titleBold
     * @return
     */
    fun setTitleBold(titleBold: Boolean): MagicBuilder {
        isTitleBold = titleBold
        return this
    }

    /**
     * 标题选中变大
     */
    fun setTitleSelcetBecomeLarge(becomeLarge: Boolean): MagicBuilder {
        titleSelBig = becomeLarge
        return this
    }

    fun setCustomTitleView(titleView: (Context, Int) -> IPagerTitleView): MagicBuilder {
        this.customTitleView = titleView
        return this
    }

    fun setDatas(datas: List<String>): MagicBuilder {
        this.datas.clear()
        this.datas.addAll(datas)
        return this
    }

    fun bindView(indicator: MagicIndicator, viewPager: ViewPager): MagicBuilder {
        this.indicator = indicator
        this.viewPager = viewPager
        return this
    }


    fun build() {
        if (viewPager == null) {
            throw NullPointerException("this ViewPager is NonNull")
        }
        initCommonNavigator(context)
    }

    private fun initCommonNavigator(context: Context) {
        val commonNavigator = CommonNavigator(context)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return datas.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val badgePagerTitleView = BadgePagerTitleView(context)
                if (customTitleView != null) {
                    badgePagerTitleView.innerPagerTitleView =
                        customTitleView?.invoke(context, index)
                    return badgePagerTitleView
                }

                val simplePagerTitleView =
                    (if (titleSelBig)
                        ScaleTransitionPagerTitleView(context)
                    else
                        ColorTransitionPagerTitleView(context))
                        .apply {
                            normalColor = titleNorColor
                            selectedColor = titleSelColor
                            text = datas[index]
                            typeface =
                                if (isTitleBold) Typeface.defaultFromStyle(Typeface.BOLD) else Typeface.defaultFromStyle(
                                    Typeface.NORMAL
                                )
                            setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSize)
                            setOnClickListener { viewPager?.currentItem = index }
                        }

                badgePagerTitleView.innerPagerTitleView = simplePagerTitleView
                return badgePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                customIndicator?.let {
                    return it
                }
                val linePagerIndicator = LinePagerIndicator(context)
                linePagerIndicator.setColors(indicatorColor)
                linePagerIndicator.mode = LinePagerIndicator.MODE_EXACTLY
                if (indicatorHeight != 0f) {
                    linePagerIndicator.lineHeight =
                        UIUtil.dip2px(context, indicatorHeight.toDouble()).toFloat()
                }
                if (indicatorWidth != 0f) {
                    linePagerIndicator.lineWidth =
                        UIUtil.dip2px(context, indicatorWidth.toDouble()).toFloat()
                }
                return linePagerIndicator
            }
        }
        commonNavigator.isAdjustMode = isAdjustMode
        indicator?.navigator = commonNavigator
        val titleContainer = commonNavigator.titleContainer // must after setNavigator
        titleContainer.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        if (intrinsicWidth != 0f) {
            titleContainer.dividerDrawable = object : ColorDrawable() {
                override fun getIntrinsicWidth(): Int {
                    return UIUtil.dip2px(context, this@MagicBuilder.intrinsicWidth.toDouble())
                }
            }
        }
        ViewPagerHelper.bind(indicator, viewPager)
    }

    class ScaleTransitionPagerTitleView(context: Context?) :
        ColorTransitionPagerTitleView(context) {
        var minScale = 0.75f

        override fun onEnter(
            index: Int,
            totalCount: Int,
            enterPercent: Float,
            leftToRight: Boolean
        ) {
            super.onEnter(index, totalCount, enterPercent, leftToRight) // 实现颜色渐变
            scaleX = minScale + (1.0f - minScale) * enterPercent
            scaleY = minScale + (1.0f - minScale) * enterPercent
        }

        override fun onLeave(
            index: Int,
            totalCount: Int,
            leavePercent: Float,
            leftToRight: Boolean
        ) {
            super.onLeave(index, totalCount, leavePercent, leftToRight) // 实现颜色渐变
            scaleX = 1.0f + (minScale - 1.0f) * leavePercent
            scaleY = 1.0f + (minScale - 1.0f) * leavePercent
        }
    }

}