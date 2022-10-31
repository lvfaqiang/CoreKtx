## 用法
### 沉浸式状态栏
[官方文档](https://github.com/gyf-dev/ImmersionBar)
- 导入依赖
```
// 基础依赖包，必须要依赖   状态栏
implementation 'com.geyifeng.immersionbar:immersionbar:3.2.2'
// kotlin扩展（可选）
implementation 'com.geyifeng.immersionbar:immersionbar-ktx:3.2.2'
```

- 布局文件
注意`statusBarView` 需要在最顶部
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical">

    <View
        android:id="@+id/statusBarView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

        ....

</LinearLayout>
```
- 常规代码配置
    通常写在 initView 方法中，
```
    immersionBar {
        statusBarDarkFont(true, 0.2f)
        statusBarView(binding.statusBarView)
    }
```

### 隐私政策弹窗
示例代码类： PrivateProxyDialog.kt
- 添加依赖
[官方文档](https://github.com/iwgang/SimplifySpan)
```
    implementation "com.github.iwgang:simplifyspan:2.2"
```
- Dialog 弹窗配置
```
fun Context.privateProxyDialog(ok: () -> Unit = {}, cancel: () -> Unit = {}) {
    val binding = DialogPrivateProxyBinding.inflate(LayoutInflater.from(this))

    val dialog = DialogBuilder.get(this)
        .setCancelable(false)
        .setCanceledOnTouchOutside(false)
        .setWidth(0.8f)
        .build(binding.root)

    val userAgreementText = SpecialClickableUnit(binding.tvContent) { _, _ ->
        WebActivity.startUserAgreement()
    }
    userAgreementText.normalTextColor = ContextCompat.getColor(this, R.color.c_597c9d)
    userAgreementText.text = "《用户协议》"

    val privacyAgreementText = SpecialClickableUnit(binding.tvContent) { _, _ ->
        WebActivity.startPrivacyPolicy()
    }
    val appName = "项目名称"
    privacyAgreementText.normalTextColor = ContextCompat.getColor(this, R.color.c_597c9d)
    privacyAgreementText.text = "《隐私协议》"

    val content = SimplifySpanBuild()
        .append("欢迎使用\"$appName\"我们非常重视您的隐私和个人信息保护，请您在使用前认真阅读")
        .append(userAgreementText)
        .append("及")
        .append(privacyAgreementText)
        .append("您同意并接受全部条款后方可使用\"$appName\"")
        .build()

    binding.tvContent.text = content

    binding.tvCancel.click {
        cancel()
        dialog.dismiss()
    }
    binding.tvNext.click {
        ok()
        dialog.dismiss()
    }
}
```
注：
    tvContent 是显示内容的 TextView
    tvCancel 是取消或者不同意 TextView
    tvOk 是同意的 TextView


### 本地缓存 MMKV
[官方文档](https://github.com/Tencent/MMKV)
- 添加依赖
```
implementation 'com.tencent:mmkv:1.2.14'
```
- 本地工具类文件为 **MMKVUtil.kt**

### 图片加载 (Glide)
- loadGifNoCache
    加载 GIF 动画资源图片，

- loadGif
    加载 GIF 动画资源图片

- load
    常规加载图片方法。
