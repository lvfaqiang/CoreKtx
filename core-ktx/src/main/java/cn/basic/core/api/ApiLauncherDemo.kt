package cn.basic.core.api

import cn.basic.core.api.config.CustomException


object ApiLauncherDemo {

    suspend fun <T> launchCommon(call: suspend () -> T): Results<T> {
        return try {
            val t = call.invoke()   // 这里不需要再次用 异步包裹，调用接口时，自动会切换异步。
            Results.success(t)
        } catch (e: Exception) {
            Results.failure(e)
        }
    }

    suspend fun <T> launch(call: suspend () -> BaseResp<T>): Results<T> {
        return try {
            val respData = call.invoke()     // 这里不需要再次用 异步包裹，调用接口时，自动会切换异步。
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
            Results.failure(e)
        }
    }

}
