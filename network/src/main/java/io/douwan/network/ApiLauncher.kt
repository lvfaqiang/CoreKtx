package io.douwan.network

import io.douwan.network.config.CustomException

/**
 * ApiLauncher2022/4/16 22:59
 * @desc :
 *
 */
object ApiLauncher {

    suspend fun <T> launchCommon(
        call: suspend () -> T
    ): Results<T> {

        return try {
            val t = call.invoke()
            Results.success(t)
        } catch (e: Exception) {
            Results.failure(e)
        }
    }

//    suspend fun <T> launchDemo(
//        call: suspend () -> BaseResp<T>
//    ): Results<T?> {
//        return try {
//            val t = call.invoke()
//            if (t.success == true) {
//                Results.success(t.data)
//            } else {
//                Results.failure(CustomException(-1, t.message ?: ""))
//            }
//        } catch (e: Exception) {
//            Results.failure(e)
//        }
//    }
}