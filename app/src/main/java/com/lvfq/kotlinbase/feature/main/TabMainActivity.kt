package com.lvfq.kotlinbase.feature.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.basic.ui.home.HomeFragment
import com.lvfq.kotlinbase.R
import cn.basic.core.base.BasicVMActivity
import cn.basic.core.common.ILoading
import cn.basic.core.tablayout.TabEntity
import cn.basic.core.tablayout.listener.CustomTabEntity
import cn.basic.core.tablayout.listener.OnTabSelectListener
import com.lvfq.kotlinbase.databinding.ActivityTabMainBinding
import cn.basic.core.util.LogUtil
import cn.basic.core.util.StatusBarUtil
import com.lvfq.kotlinbase.views.LoadingView

/**
 * TabMainActivity2021/1/15 5:12 PM
 * @desc :
 *
 */
class TabMainActivity : BasicVMActivity<ActivityTabMainBinding, TabMainVM>() {
    override val viewModelClass: Class<TabMainVM>
        get() = TabMainVM::class.java

    override fun bindingView(): ActivityTabMainBinding {
        return ActivityTabMainBinding.inflate(layoutInflater)
    }

    override val loadingView: ILoading
        get() = LoadingView.get(this)


    private val tabTitles by lazy {
        ArrayList<String>().apply {
            add("Tab1")
            add("Tab2")
            add("Tab3")
            add("Tab4")
        }
    }

    private val iconSelectIds = intArrayOf(
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher
    )
    private val iconUnSelectIds = intArrayOf(
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher
    )


    private val tabFragments by lazy {
        ArrayList<Fragment>().apply {
            add(HomeFragment().apply {
                content = "1111"
            })
            add(HomeFragment().apply {
                content = "2222"
            })
            add(HomeFragment().apply {
                content = "3333"
            })
            add(HomeFragment().apply {
                content = "4444"
            })
        }
    }

    private val tabEntities by lazy {
        ArrayList<CustomTabEntity>().apply {
            for (i in tabTitles.indices) {
                add(
                    TabEntity(
                        tabTitles[i],
                        iconSelectIds[i],
                        iconUnSelectIds[i]
                    )
                )
            }
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        StatusBarUtil.darkMode(this)
        applySinking()

        initTabLayout()

        initTabMsg()

    }

    private fun initTabLayout() {
        binding.tabLayout.setTabData(tabEntities, this, R.id.flContent, tabFragments)

        binding.tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                LogUtil.i("position: $position")
            }

            override fun onTabReselect(position: Int) {
                LogUtil.i("position: $position")
            }

            override fun onDoubleTabSelect(position: Int) {
                LogUtil.i("position: $position")
            }
        })

        binding.tabLayout.apply {
            iconHeight = 20f
            iconWidth = 20f
            textsize = 16f
        }
    }

    /**
     * 设置 Tab 未读消息数量
     */
    private fun initTabMsg() {
        val msgPosition = 0 // 操作 TabLayout 的某一个下标
        val unRead = "0" // 未读消息条数
        if (unRead.isEmpty() || unRead.equals("0")) {
            binding.tabLayout.hideMsg(msgPosition)
        } else {
            binding.tabLayout.showMsg(msgPosition, unRead.toInt(), "···")
            binding.tabLayout.setMsgMargin(msgPosition, -7.2f, 2f)

        }
    }

    /**
     * 更新当前显示的 Tab
     */
    private fun updateCurTab(curTabIndex: Int) {
        binding.tabLayout.currentTab = curTabIndex
    }
}