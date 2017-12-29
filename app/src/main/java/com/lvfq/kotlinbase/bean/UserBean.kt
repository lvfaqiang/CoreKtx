package com.lvfq.kotlinbase.bean

import android.annotation.SuppressLint


/**
 * UserBean
 * @author lvfq
 * @Github: https://github.com/lvfaqiang
 * @Blog: http://blog.csdn.net/lv_fq
 * @date 2017/12/20 下午2:14
 * @desc :
 *
 */

@SuppressLint("ParcelCreator")
data class UserBean(var name: String = "") : BaseBean() {
}