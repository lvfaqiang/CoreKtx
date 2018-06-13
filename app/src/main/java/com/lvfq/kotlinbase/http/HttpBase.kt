package com.lvfq.kotlinbase.http

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * HttpBase
 * @author FaQiang on 2018/6/6 下午1:38
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
@SuppressLint("ParcelCreator")
@Parcelize
open class HttpBase<T>() : Parcelable {
    var data: T? = null
    var error: Error? = null

    constructor(data: T?, error: Error?) : this() {
        this.data = data
        this.error = error
    }
}