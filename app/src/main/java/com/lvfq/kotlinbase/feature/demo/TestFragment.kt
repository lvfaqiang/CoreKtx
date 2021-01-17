package com.lvfq.kotlinbase.feature.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lvfq.kotlinbase.base.BaseFragment
import com.lvfq.kotlinbase.databinding.FragmentTestBinding
import com.lvfq.kotlinbase.feature.MainActivity
import com.lvfq.kotlinbase.kotlinx.click
import com.lvfq.kotlinbase.kotlinx.startActivity
import com.lvfq.kotlinbase.utils.basic.LogUtil

/**
 * TestFragment2021/1/17 12:58 AM
 * @desc :
 *
 */
class TestFragment : BaseFragment<FragmentTestBinding>() {
    private val TAG = TestFragment::class.java.simpleName
    var content = ""
    override fun init(savedInstanceState: Bundle?) {
        binding.tvContent.text = content
        binding.tvContent.click {
            startActivity<MainActivity>()
        }
    }

    override fun bindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): FragmentTestBinding {
        return FragmentTestBinding.inflate(inflater, container, attachToRoot)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.i("------- onCreate     --- $TAG")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtil.i("------- onViewCreated     --- $TAG")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.i("------- onResume     --- $TAG")
    }

    override fun onFragmentFirstVisible() {
        LogUtil.i("------- onFragmentFirstVisible     --- ${isFragmentVisible()}")
    }

    override fun onFragmentVisibleChange(isVisible: Boolean) {
        LogUtil.i("------- onFragmentVisibleChange     --- $isVisible")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        LogUtil.i("----------- hidden : $hidden")
    }


}