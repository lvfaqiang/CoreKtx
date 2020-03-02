package com.lvfq.kotlinbase.entities

import android.os.Parcelable
import com.lvfq.kotlinbase.entities.base.BaseEntity
import kotlinx.android.parcel.Parcelize

/**
 * LoginData2019-09-19 22:18
 * @desc :
 *
 */
@Parcelize
data class LoginData(
    var authKey: String?,
    var qrCode: String?
) : BaseEntity(), Parcelable