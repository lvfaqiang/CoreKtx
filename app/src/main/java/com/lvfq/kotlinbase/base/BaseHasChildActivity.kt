package com.lvfq.kotlinbase.base

import android.support.v4.app.Fragment
import com.ekoo.haidaicrm.base.BaseActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * BaseHasChildActivity
 * @author FaQiang on 2018/1/2 下午11:19
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
abstract class BaseHasChildActivity : BaseActivity(), HasSupportFragmentInjector {
    @Inject
    protected lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector
}