package cn.basic.core.tablayout.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.util.*

class FragmentChangeManager(
    private val mFragmentManager: FragmentManager,
    private val mContainerViewId: Int,
    /** Fragment切换数组  */
    private val mFragments: ArrayList<Fragment>
) {

    /** 当前选中的Tab  */
    var currentTab = 0
        private set

    /** 初始化fragments  */
    private fun initFragments() {
        for (fragment in mFragments) {
            mFragmentManager.beginTransaction().add(mContainerViewId, fragment).hide(fragment)
                .commit()
        }
        setFragments(0)
    }

    /** 界面切换控制  */
    fun setFragments(index: Int) {
        for (i in mFragments.indices) {
            val ft = mFragmentManager.beginTransaction()
            val fragment = mFragments[i]
            if (i == index) {
                ft.show(fragment)
            } else {
                ft.hide(fragment)
            }
            ft.commitAllowingStateLoss()
        }
        currentTab = index
    }

    val currentFragment: Fragment
        get() = mFragments[currentTab]

    init {
        initFragments()
    }
}