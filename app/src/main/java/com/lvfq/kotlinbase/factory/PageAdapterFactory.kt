package com.lvfq.kotlinbase.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter


/**
 * PageAdapterFactory2018/12/22 下午12:13
 * @desc :
 *
 */
class PageAdapterFactory constructor(val fragmentManager: FragmentManager) {

    fun createPagerAdapter(fragments: List<Fragment>): PagerAdapter {
        return createTitlePagerAdapter(fragments, null)
    }

    fun createTitlePagerAdapter(fragments: List<Fragment>, titles: List<String>?): PagerAdapter {
        /**
         *  FragmentStatePagerAdapter 适合大量页面，不断重建和销毁
         *  FragmentPagerAdapter 适合少量页面，常驻内存。
         */
        if (fragments.size > 5) {
            return StateAdapter(fragmentManager, fragments, titles)
        } else {
            return PageAdapter(fragmentManager, fragments, titles)
        }
    }

    private inner class StateAdapter(
        fragmentManager: FragmentManager,
        val fragments: List<Fragment>,
        val titles: List<String>? = null
    ) : FragmentStatePagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            if (titles?.isNotEmpty() == true) {
                return titles.get(position)
            }
            return super.getPageTitle(position)
        }
    }

    private inner class PageAdapter(
        fragmentManager: FragmentManager,
        val fragments: List<Fragment>,
        val titles: List<String>? = null
    ) : FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            if (titles?.isNotEmpty() == true) {
                return titles.get(position)
            }
            return super.getPageTitle(position)
        }
    }

}