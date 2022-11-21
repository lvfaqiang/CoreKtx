package io.douwan.network

import io.douwan.network.Failure
import io.douwan.network.Results
import io.douwan.network.Success

/**
 * CallBackData2022/10/26 22:48
 * @desc :
 *
 */

class CallBackData<T> constructor(val results: Results<T>) {

    val error: ErrorData?
        get() {
            if (results is Failure) {
                return ErrorData(results.exception.code, results.exception.message)
            }
            return null
        }

    val data: T?
        get() {
            if (results is Success) {
                return results.data
            }
            return null
        }
}

class ErrorData(val code: String, val message: String)