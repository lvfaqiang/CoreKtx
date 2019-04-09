package com.lvfq.kotlinbase.base

import android.view.View

/**
 * SimpleObserver
 * @author FaQiang on 2018/9/27 下午7:00
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
abstract class SimpleObserver<T>
@JvmOverloads constructor(override var iBase: IBase?,
                          override var errorMsg: String = "",
                          override var view: View?)
    : BaseObserver<ApiBaseRsp<T>>(iBase, errorMsg, view) {

//    // -------------   构造函数     -----------
//    constructor(iBase: IBase?, errorMsg: String, view: View?) : super(iBase, errorMsg, view)
//
//    constructor(iBase: IBase?, errorMsg: String) : super(iBase, errorMsg, null)
//
//    constructor(iBase: IBase?, view: View?) : super(iBase, "", view)
//
//    constructor(iBase: IBase?) : super(iBase, "", null)
//
//    constructor() : super(null, "", null)
//    // ---------------  End         ----------


    abstract fun onSuccess(t: T?, metaRsp: MetaRsp?)

    override fun onSuccess(t: ApiBaseRsp<T>) {
        onSuccess(t.data, t.meta)
    }

}