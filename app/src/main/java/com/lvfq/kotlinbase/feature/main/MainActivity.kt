package com.lvfq.kotlinbase.feature.main

import android.os.Bundle
import cn.basic.core.ktx.view.click
import cn.basic.core.util.StatusBarUtil
import com.lvfq.kotlinbase.feature.main.home.HomeFragment
import com.lvfq.kotlinbase.R
import com.lvfq.kotlinbase.base.BaseVMActivity
import com.lvfq.kotlinbase.databinding.ActivityMainBinding
import com.lvfq.kotlinbase.utils.basic.FragmentUtil

/**
 * MainActivity2021/9/5 12:18 下午
 * @desc :
 *
 */
class MainActivity : BaseVMActivity<ActivityMainBinding, MainVM>() {
    override val viewModelClass: Class<MainVM>
        get() = MainVM::class.java

    override fun bindingView(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }


    private val fragmentUtil by lazy {
        FragmentUtil(supportFragmentManager)
    }

    val tabViews by lazy {
        arrayListOf(
            binding.flTabHome,
            binding.flTabComposition,
            binding.flTabNote,
            binding.flTabMine
        )
    }

    val fragments by lazy {
        arrayListOf(
            HomeFragment(),
            HomeFragment(),
            HomeFragment(),
            HomeFragment()
        )
    }

    override fun init(savedInstanceState: Bundle?) {
        StatusBarUtil.darkMode(this, true)
        applySinking()

        checkedTab(vM.curIndex)
    }

    override fun initListener() {
        binding.flTabHome.click {
            checkedTab(0)
        }
        binding.flTabComposition.click {
            checkedTab(1)
        }
        binding.flTabNote.click {
            checkedTab(2)
        }
        binding.flTabMine.click {
            checkedTab(3)
        }
    }


    private fun checkedTab(index: Int) {
        showFragment(index)
        tabView(index)
        vM.curIndex = index
    }

    private fun showFragment(index: Int) {
        fragmentUtil.showFragment(fragments[index], R.id.flContent)
    }

    private fun tabView(index: Int) {
        tabViews.mapIndexed { i, view ->
            view.isSelected = i == index
        }
    }
}