package com.example.basic.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lvfq.kotlinbase.R
import com.lvfq.kotlinbase.feature.main.home.HomeViewModel
import cn.basic.core.util.LogUtil

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
}