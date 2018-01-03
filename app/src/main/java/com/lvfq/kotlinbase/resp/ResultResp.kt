package com.lvfq.kotlinbase.resp

import android.annotation.SuppressLint
import com.lvfq.kotlinbase.base.BaseBean

/**
 * ResultResp
 * @author lvfq
 * @Github: https://github.com/lvfaqiang
 * @Blog: http://blog.csdn.net/lv_fq
 * @date 2017/11/7 下午2:04
 * @desc :
 *
 */
@SuppressLint("ParcelCreator")
data class ResultResp<out T : BaseBean>(val resultData: T) : BaseResp()