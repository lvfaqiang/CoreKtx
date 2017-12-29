package com.lvfq.kotlinbase.di.module

import dagger.Module

/**
 * AppModule
 * @author FaQiang on 2017/12/27 上午11:09
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *  当前类用于生产一些 全局的静态类
 */

@Module(includes = [AppModuleAbs::class])
class AppModule {

    // 生成方式如下：
//    @Singleton
//    @Provides
//    fun method(params:XX):XX{}

}
