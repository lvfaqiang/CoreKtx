package com.faqiang.core.api

import retrofit2.http.GET

/**
 * ApiService2023/1/13 15:56
 * @desc :
 *
 */
interface ApiService {

    @GET("/demo")
    suspend fun getDemoDatas(): BaseResp<String>

}