package cn.basic.core.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * ApiBaseRsp
 * @author FaQiang on 2018/9/27 下午6:51
 * @desc :
 *
 */

open class BasicResp(
    val code: String? = "",
    val message: String? = null,
    val success: Boolean = false
)

class BaseResp<T>(val data: T?) : BasicResp()


@Parcelize
class EmptyBean : Parcelable