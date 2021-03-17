package cn.basic.core.api

import cn.basic.core.api.config.CustomException


object ApiLauncher {

    suspend fun <T> launchCommon(call: suspend () -> T): Results<T> {
        return try {
            val t = call.invoke()
            Results.success(t)
        } catch (e: Exception) {
            Results.failure(
                e.cause ?: CustomException(
                    -1,
                    "Unknow Exception"
                )
            )
        }
    }

    suspend fun <T> launch(call: suspend () -> BaseResp<T>): Results<T> {
        return try {
            val respData = call.invoke()
            if (respData.success || respData.code == "0") {
                if (respData.data != null) {
                    Results.success(respData.data)
                } else {
                    Results.failure(
                        CustomException(
                            respData.code,
                            "Response data is Null"
                        )
                    )
                }
            } else {
                Results.failure(
                    CustomException(
                        respData.code,
                        respData.message
                    )
                )
            }
        } catch (e: Exception) {
            Results.failure(
                e.cause ?: CustomException(
                    -1,
                    "Unknow Exception"
                )
            )
        }
    }

}
