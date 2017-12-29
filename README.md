## 项目简介

#### 本项目是基于 Kotlin 语言的一个基础的项目架构，目前主要涉及到的一些技术框架如下：
**网络，异步**
 - Retrofit2  - 2.3.0
 - retrofit2:adapter-rxjava2   - 2.3.0
 - Rxandroid - 2.0.1
 
**依赖注入框架**
 - Dagger2 - 2.13
 
**强大的 RecyclerView适配器**
 - BaseRecyclerViewAdapterHelper - 2.9.34 [相关介绍](http://www.jianshu.com/p/b343fcff51b0)

**图片加载**
 - Glide - 4.3.0 ，[相关介绍](https://muyangmin.github.io/glide-docs-cn/)

**权限控制**
 - Rxpermissions - 0.9.4 [相关介绍](https://github.com/tbruyelle/RxPermissions)
 
**内存泄漏监测工具**
 - Leakcanary - 1.5.4 ，[相关介绍](https://www.liaohuqiu.net/cn/posts/leak-canary-read-me/)

**自己整理的常用的工具类库**
 - AndroidUtils - 2.0.2，[相关介绍](https://github.com/lvfaqiang/AndroidUtils) 
 
 
### 基本配置
 依赖导入信息在 config.gradle 文件中，
 请求地址(HttpUrl)在 app-build.gradle 中 通过 productFlavors 来进行配置，避免打包时，不停的来切换请求地址。