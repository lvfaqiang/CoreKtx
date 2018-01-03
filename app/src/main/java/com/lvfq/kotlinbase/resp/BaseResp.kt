package com.lvfq.kotlinbase.resp

import android.annotation.SuppressLint
import com.lvfq.kotlinbase.base.BaseBean

/**
 * BaseResp
 * @author lvfq
 * @Github: https://github.com/lvfaqiang
 * @Blog: http://blog.csdn.net/lv_fq
 * @date 2017/11/7 下午1:35
 * @desc :
 *      success 是服务器返回的用于判断是否响应成功的标识，
 *      message 是响应失败的提示，这里也可以换成其他的错误对象等类型。
 *
 */
@SuppressLint("ParcelCreator")
open class BaseResp(var success: Boolean = false, var message: String = "") : BaseBean()