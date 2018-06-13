package com.lvfq.kotlinbase.http

import android.net.ParseException
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.lvfq.kotlinbase.App
import com.lvfq.kotlinbase.R
import com.lvfq.library.utils.LvLog
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException

/**
 * ApiException
 * @author FaQiang on 2018/6/6 下午1:26
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
class ApiException(e: Throwable) {

    private var code: Int = 0
    private var message: String = ""
    private var errorBody: HttpErrorBody? = null


    init {
        when (e) {
            is HttpException -> {
                code = e.code()
                val string = e.response().errorBody()?.string() ?: ""
                try {
                    errorBody = Gson().fromJson(string, HttpErrorBody::class.java)
                } catch (e: Exception) {
                    LvLog.i("ApiException : ${e.message}")
                }

                errorBody?.let { message = it.error?.message ?: "" }

                when (code) {
                    401 -> {
//                        val errorCode = errorBody?.error?.code ?: 0
//                        if (errorCode == 40001 || errorCode == 42001) {
//                            UserExt.loginOut()
//                        }
                    }
                    503 -> {
                        message = App.mContext.getString(R.string.str_exception_server_maintance)
                    }
                    in 500 until 600 -> {
                        message = App.mContext.getString(R.string.str_exception_server)
                    }
                }

                errorBody?.let { message = it.error?.message ?: "" }

            }
            is JsonParseException,
            is ParseException,
            is JSONException -> {
                message = App.mContext.getString(R.string.str_exception_parse)
            }
            is ConnectException -> {
                message = App.mContext.getString(R.string.str_exception_network)
            }
            else -> {
                message = App.mContext.getString(R.string.str_exception_unknown)
                LvLog.i("ApiException unknown Exception : ${e.message}")
            }
        }
    }

    fun getCode() = code

    fun getMessage() = message


}