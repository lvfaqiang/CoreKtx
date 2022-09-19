package io.douwan.network

import android.content.Context
import android.net.ParseException
import com.google.gson.Gson
import com.google.gson.JsonParseException
import io.douwan.network.config.CustomException
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
class ApiException(e: Exception) {
    private val mContext: Context
        get() = NetworkProvider.mContext

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
                val error = NetworkProvider.get().getExceptionHandling()?.invoke(e)
                if (error != null) {
                    code = "${error.code}"
                    message = error.message
                } else {
                    // 演示  Demo   主要依赖自定义异常处理
                    val string = e.response()?.errorBody()?.string() ?: ""
                    try {
                        errorBody = Gson().fromJson(string, HttpErrorBody::class.java)
                    } catch (e: Exception) {
                        println("ApiException : ${e.message}")
                    }

                    errorBody?.let {
                        code = "${it.code ?: -1}"

                        message = it.metadata?.REASON ?: ""
                        if (message.isEmpty()) {
                            message = mContext.getString(R.string.unknown_exception)
                        }
                    }

                    when (httpCode) {
                        401 -> {
                        }
                        503 -> {
                            // 服务器正在维护
//                            message =
//                                mContext.getString(R.string.str_exception_server_maintance)
                        }
                        in 500 until 600 -> {
                            // 服务器异常
//                            message =
//                                mContext.getString(R.string.str_exception_server)
                        }
                        else -> {
                            if (message.isEmpty()) {
                                val msg = e.message() ?: ""
                                message = "$msg : $httpCode"
                            }
                        }
                    }
                }
            }
            is JsonParseException,
            is ParseException,
            is JSONException -> {
                message = mContext.getString(R.string.parse_exception)
            }
            is ConnectException -> {
                message = mContext.getString(R.string.connect_exception)
            }
            is SocketTimeoutException -> {
                message = mContext.getString(R.string.request_timeout)
            }
            is CustomException -> {
                code = e.code ?: "-1"
                message = e.message ?: ""
            }
            else -> {
                message = mContext.getString(R.string.unknown_exception)
//                if (!NetUtil.hasNetWork()) {
//                    message = mContext.getString(R.string.str_exception_network)
//                } else {
//                    message = mContext.getString(R.string.str_exception_unknown)
//                }
                println("ApiException unknown Exception : ${e.message}")
            }
        }
    }
}

data class HttpErrorBody(
    var code: Int?,
    var id: String?,
    var domain: String?,
    var retryable: Boolean?,
    var metadata: HttpMetaData
)

data class HttpMetaData(
    val REASON: String?
)
//data class HttpErrorBody(var error: CustomError?)

class CustomError(var code: Int, var message: String = "")