package com.faqiang.core

import com.faqiang.core.base.BaseActivity
import com.faqiang.core.databinding.ActivityMainBinding
import com.faqiang.core.feature.main.HomeFragment
import io.douwan.basic.core.ktx.click

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        const val HOME = 0
        const val DINNER = 1
        const val VIDEO = 2
        const val BLESS = 3
        const val KEY_TARGET_INDEX = "TargetIndex"
    }

    private val tabs by lazy {
        arrayListOf(
            binding.homeLayout,
            binding.dinnerLayout,
            binding.videoLayout,
            binding.blessLayout
        )
    }

    private val fragments by lazy {
        arrayListOf(
            HomeFragment(),
            HomeFragment(),
            HomeFragment(),
            HomeFragment()
        )
    }


    override fun initView() {
        tabs.mapIndexed { index, linearLayout ->
            linearLayout.click {
                checkTabByIndex(index)
            }
        }
        checkTabByIndex(0)
    }

    override fun initData() {
        val index = intent.getIntExtra(KEY_TARGET_INDEX, -1)
        if (index != -1) {
            checkTabByIndex(index)
        }
    }

    private fun checkTabByIndex(pos: Int) {
        tabs.mapIndexed { index, linearLayout ->
            linearLayout.isSelected = index == pos
        }
        fragmentUtil.showFragment(fragments[pos], R.id.rootLayout)
    }
}