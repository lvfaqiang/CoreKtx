package com.lvfq.kotlinbase.api.net

/**
 * Result2021/3/3 12:10 AM
 * @desc :
 *
 */
sealed class Results<out T> {

    companion object {
        fun <T> success(result: T): Results<T> =
            Success(result)

        fun <T> failure(error: Throwable): Results<T> =
            Failure(error)
    }

}

data class Failure(val throwable: Throwable) : Results<Nothing>() {
    val exception = ApiException(throwable)
}

data class Success<out T>(val data: T) : Results<T>() {
}
