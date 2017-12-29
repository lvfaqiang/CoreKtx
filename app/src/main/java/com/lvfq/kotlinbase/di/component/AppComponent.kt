package com.lvfq.kotlinbase.di.component

import com.lvfq.kotlinbase.App
import com.lvfq.kotlinbase.di.module.AppModuleAbs
import com.lvfq.kotlinbase.di.module.ClientModule
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * AppComponent
 *
 * @author FaQiang on 2017/12/27 下午2:25
 * @Github: [](https://github.com/lvfaqiang)
 * @Blog: [](http://blog.csdn.net/lv_fq)
 * @desc :
 */

@Singleton
@Component(modules = [
    (AppModuleAbs::class),
    (ClientModule::class)
])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}