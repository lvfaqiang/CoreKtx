package com.lvfq.kotlinbase.ui

import android.os.Bundle
import com.lvfq.kotlinbase.R
import com.lvfq.kotlinbase.base.BaseHasChildActivity

class MainActivity : BaseHasChildActivity() {

    override fun getLayoutId(): Int = R.layout.activity_main


    override fun create(savedInstanceState: Bundle?) {
    }

}
