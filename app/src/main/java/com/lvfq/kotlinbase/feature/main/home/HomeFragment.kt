package com.lvfq.kotlinbase.feature.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cn.basic.core.ktx.dp2px
import cn.basic.core.util.LogUtil
import com.lvfq.kotlinbase.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    var content = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        LogUtil.i("1111111")
//        homeViewModel.data.observe(this, Observer {
//            textView.text = it.toString()
//        })

        textView.text = content

//        homeViewModel.loadData()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtil.i("222222")
    }


    private fun initDotState(position: Int, dotRoot: ViewGroup) {
//        val dotRoot = binding?.llDotRoot ?: return
        if (dotRoot.childCount > position) {
            dotRoot.children.forEachIndexed { index, view ->
                view.isSelected = index == position
            }
        }
    }

    private fun initDots(dotRoot: ViewGroup, count: Int) {
        dotRoot.removeAllViews()
        repeat(count) {
            dotRoot.addView(initDot())
        }
    }

    private fun initDot(): TextView {
        val textView = TextView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                requireContext().dp2px(8f),
                requireContext().dp2px(8f)
            ).apply {
                setMargins(requireContext().dp2px(5f))
            }

            setBackgroundResource(R.drawable.bg_dot_item)
        }
        return textView
    }
}