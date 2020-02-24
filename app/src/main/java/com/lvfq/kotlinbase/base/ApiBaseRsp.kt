package com.lvfq.kotlinbase.base

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * ApiBaseRsp
 * @author FaQiang on 2018/9/27 下午6:51
 * @desc :
 *
 */

class ApiBaseRsp<T>(var data: T?, var meta: MetaRsp?)


@Parcelize
class MetaRsp(var page_number: Int = 0,
              var page_size: Int = 0,
              var page_count: Int = 0
) : Parcelable


@Parcelize
class EmptyBean : Parcelable