package com.lvfq.kotlinbase.feature.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lvfq.kotlinbase.R
import com.lvfq.kotlinbase.base.BaseFragment
import com.lvfq.kotlinbase.builder.MagicBuilder
import com.lvfq.kotlinbase.databinding.FragmentTest2Binding
import com.lvfq.kotlinbase.factory.PageAdapterFactory
import com.lvfq.kotlinbase.utils.basic.LogUtil

/**
 * TestFragment2021/1/17 12:58 AM
 * @desc :
 *
 */
class Test2Fragment : BaseFragment<FragmentTest2Binding>() {

    private val TAG = Test2Fragment::class.java.simpleName
    var content = ""

    override fun bindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): FragmentTest2Binding {
        return FragmentTest2Binding.inflate(inflater, container, attachToRoot)
    }


    override fun init(savedInstanceState: Bundle?) {

        val fragments = arrayListOf(
            Test3Fragment().apply {
                content = "TestFragment3"
            },
            Test4Fragment().apply {
                content = "TestFragment4"
            }
        )
        binding.viewPager.adapter =
            PageAdapterFactory(childFragmentManager).createPagerAdapter(fragments)
        initMagic()

    }

    private fun initMagic() {
        val titles = arrayListOf("关注", "推荐")
        MagicBuilder.get(requireContext())
            .setDatas(titles)
            .setTitleSelColor(R.color.c_517cff)
            .setTitleNorColor(R.color.c_838291)
            .setIndicatorColor(R.color.c_517cff)
            .setIndicatorHeight(2f)
            .setIndicatorWidth(60f)
            .bindView(binding.magicIndicator, binding.viewPager)
            .build()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        LogUtil.i("----------- hidden : $hidden")
    }


}