package com.lvfq.kotlinbase.api.net

import android.net.ParseException
import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.lvfq.kotlinbase.App
import com.lvfq.kotlinbase.R
import com.lvfq.kotlinbase.utils.basic.NetUtil
import kotlinx.android.parcel.Parcelize
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * ApiException
 * @author FaQiang on 2018/8/28 上午11:26
 * @desc :
 *
 */
class ApiException(e: Throwable) {

    var code: String = ""
        private set
    var httpCode: Int = 0
        private set
    var message: String = ""
        private set

    private var errorBody: HttpErrorBody? = null

    init {
        when (e) {
            is HttpException -> {
                httpCode = e.code()
                val string = e.response()?.errorBody()?.string() ?: ""
                try {
                    errorBody = Gson().fromJson(string, HttpErrorBody::class.java)
                } catch (e: Exception) {
                    println("ApiException : ${e.message}")
                }
                errorBody?.let {
                    code = it.error?.code?.toString() ?: code
                    message = it.error?.message ?: message
                }

                when (httpCode) {
                    401 -> {
                        if (code == "40001" || code == "6001") {
                        }
                    }
                    503 -> {
                        // 服务器正在维护
                        message = App.mContext.getString(R.string.str_exception_server_maintance)
                    }
                    in 500 until 600 -> {
                        // 服务器异常
                        message = App.mContext.getString(R.string.str_exception_server)
                    }
                    else -> {
                        if (message.isEmpty()) {
                            val msg = e.message() ?: ""
                            message = "$msg : $httpCode"
                        }
                    }
                }


            }
            is JsonParseException,
            is ParseException,
            is JSONException -> {
                message = App.mContext.getString(R.string.str_exception_parse)
            }
            is ConnectException -> {
                message = App.mContext.getString(R.string.str_exception_network)
            }
            is SocketTimeoutException -> {
                message = App.mContext.getString(R.string.str_request_timeout)
            }
            is CustomException -> {
                code = e.code ?: "-1"
                message = e.message ?: ""
            }
            else -> {
                if (!NetUtil.hasNetWork()) {
                    message = App.mContext.getString(R.string.str_exception_network)
                } else {
                    message = App.mContext.getString(R.string.str_exception_unknown)
                }
                println("ApiException unknown Exception : ${e.message}")
            }
        }
    }
}

data class HttpErrorBody(var error: Error?)

@Parcelize
data class Error(var code: Int, var message: String = "") : Parcelable