### 用法
在 Application 中调用
```
        NetworkProvider.get()
            .baseUrl("")
            .configApiLog {
                it?.d("HTTP")
            }.init(this)
```
### NetworkProvider 相关配置说明

- baseUrl(String)
    `配置默认的API请求地址`

- setCustomExceptionHandling
    ```
        设置自定义的错误处理，
        setCustomExceptionHandling { e ->
            val errorString = e.response()?.errorBody()?.string() ?: ""
            //实现异常处理,这里需要自己使用 Gson 转换 ErrorMode 格式，

            return@setCustomExceptionHandling CustomError(0, "")    // 最终返回结果需要是 CustomError
        }
    ```

- setApiInterceptors
    ```
    配置 Api 拦截器，用于添加公共请求参数，

        // 添加公共请求头
        val headerInterceptor = Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("client", "android")
                .addHeader("appVersion", CoreKtxProvider.get().appVersion)
                .build()
            chain.proceed(request)
        }

        setApiInterceptors(headerInterceptor,...)
    ```

- configApiLog
    ```
    输出 HTTP 的请求日志，
    configApiLog { httpMessage ->
           // 执行打印操作
    }
    ```

### NetWorkApiClient
    ```
    Retrofit 具体配置实现，如有需要，可自行在 app 模块下创建一个 ApiClient, 参照 NetWorkApiClient 实现即可。
    ```

### NetWorkApiService
    ```
    NetWorkApiService 是 NetWorkApiClient 中默认配置的，如果有自定义 ApiClient，则可自定义 APIService，

    需要注意的是： APIService 中的方法，都要以 suspend 来修饰. 例如：

    @GET("v1/shoes")
    suspend fun userInfo(): UserData
    ```

### ApiLauncher
    ```
    接口调用执行，
    ```
- launchCommon
    ```
    该方法默认是直接返回 Api 请求的所有数据，如果需要依据某些通用配置来进行处理成功或失败，可参照 launchDemo 方法.
    ```