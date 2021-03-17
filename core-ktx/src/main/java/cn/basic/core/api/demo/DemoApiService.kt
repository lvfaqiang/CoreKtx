package cn.basic.core.api.demo

import cn.basic.core.api.BaseResp
import cn.basic.core.api.EmptyBean
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * ApiService
 * @author FaQiang on 2018/11/14 下午3:21
 * @desc :
 *
 */
interface DemoApiService {

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("authCode") authCode: String? = null,
        @Field("authKey") authKey: String? = null
    ): BaseResp<EmptyBean>
}