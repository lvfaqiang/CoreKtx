package com.lvfq.kotlinbase.base

import android.os.Bundle
import com.lvfq.kotlinbase.databinding.ActivityPlaceholderBinding

/**
 * BaseEmptyActivity2022/1/12 3:46 下午
 * @desc :
 *
 */
open class BaseEmptyActivity : BaseActivity<ActivityPlaceholderBinding>() {
    override fun bindingView(): ActivityPlaceholderBinding {
        return ActivityPlaceholderBinding.inflate(layoutInflater)
    }

    override fun init(savedInstanceState: Bundle?) {

    }
}