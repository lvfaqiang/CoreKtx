package com.lvfq.kotlinbase.http

/**
 * Resource
 * @author FaQiang on 2018/1/3 下午5:06
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
class Resource<T> constructor(val status: Status, val data: T?, message: String?, throwable: Throwable?) {

    companion object {
        enum class Status {
            LOADING, ERROR, SUCCESS
        }

        fun <T> success(data: T?): Resource<T> {
            return Resource<T>(Status.SUCCESS, data, "", null)
        }

        fun <T> error(message: String?, data: T?, throwable: Throwable?): Resource<T> {
            return Resource<T>(Status.ERROR, data, message, throwable)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource<T>(Status.LOADING, data, "", null)
        }
    }

}