## 用法
### 创建 Dialog
```
    val dialogBinding = DialogxxxxLayoutBinding.inflate(LayoutInflater.from(context))
    val dialog = DialogBuilder[this]
        .setCancelable(cancelable)
        .setCanceledOnTouchOutside(false)
        .build(dialogBinding.root)

```
#### 属性介绍
    - get(Context)      构建 DialogBuilder
    - setWidth(Int)     设置 Dialog 具体宽度值，
    - setWidth(Float)   设置 Dialog 宽度比例，对标屏幕宽度
    - setHeight(Int)    设置 Dialog 具体高度值，
    - setHeight(Float)  设置 Dialog 高度比例，对标屏幕高度，
    - setGravity(Int)   设置 Dialog 显示的位置，Gravity.Center、Gravity.Bottom、Gravity.Top等等
    - setCancelable(Boolean)    Dialog 点击返回键是否可以关闭
    - setCanceledOnTouchOutside(Boolean)    Dialog 点击阴影区域，是否被关闭
    - setOnDismissListener(DialogInterface.OnDismissListener)
        添加 Dialog 被关闭后监听事件
    - setOnCancelListener(DialogInterface.OnCancelListener)
        添加 Dialog 被取消后监听事件
    - setStyleRes(Int)  设置 Dialog 样式，默认不需要配置.

### 创建 PopupWindow
```
    val binding = PopupxxxxLayoutBinding.inflate(LayoutInflater.from(context))
    val dialog = PopupWindowBuilder[this]
        .setOutsideTouchable(cancelable)
        .build(binding.root)

```
#### 属性介绍
    - get(Context)      构建 PopupWindowBuilder
    - setWidth(Int)     设置 PopupWindow 具体宽度值，
    - setHeight(Int)    设置 PopupWindow 具体高度值，
    - setAnimStyle(Int)    PopupWindow 设置动画属性
    - setOutsideTouchable(Boolean)    PopupWindow 点击其他区域，是否被关闭

### 扩展属性
#### Activity 扩展
    - startActivity(Int, action: Intent.() -> Unit = {}) 跳转目标 Activity
    ```
        startActivity<TargetActivity>(999) {
            putExtra("param1", value1)
        }
        // 999 为自定义的 Activity Result Code，用于回调 onActivityForResult 方法， 默认可不添加。
    ```
    - startAction(String, Int, action: Intent.() -> Unit = {}) 隐式跳转
    ```
        startAction(Intent.ACTION_VIEW) {
            data = Uri.parse("https://www.baidu.com")
        }
    ```

#### Fragment 扩展
    - startActivity(Int, action: Intent.() -> Unit = {}) 跳转目标 Activity
        ```
            startActivity<TargetActivity>(999) {
                putExtra("param1", value1)
            }
            // 999 为自定义的 Activity Result Code，用于回调 onActivityForResult 方法， 默认可不添加。
        ```
    - startAction(String, Int, action: Intent.() -> Unit = {}) 隐式跳转
    ```
        startAction(Intent.ACTION_VIEW) {
            data = Uri.parse("https://www.baidu.com")
        }
    ```

#### Context 扩展
    - appDisplayMetrics 获取 APP DisplayMetrics 属性
    - packageInfo       获取App PackageInfo 属性
    - versionName       当前 App 版本名称
    - versionCode       当前 App 版本号
    - dp2px(Float)      dp 转换为 px(像素)
    - sp2px(Float)      sp 转换为 px
    - px2dp(Float)      px 转换为 dp(Android尺寸单位)
    - px2sp(Float)      px 转换为 sp(Android字体单位)
    - getColorById(Int) 获取颜色
    - getDrawableById(Int) 根据 drawResourceId 获取 Drawable对象
    - screenHeight      当前屏幕高度
    - screenWidth       当前屏幕宽度
    - readAssetsFile(String)    读取本地文件
        ```
        context.readAssetsFile("test.json") // 文件存放路径 main - assets - test.json
        ```


#### Coroutine 扩展
    - launchUI(block: suspend CoroutineScope.() -> Unit): Job
        ```
        //在主线程中加载
        lifecycleScope.launchUI{
            ...
        }
        ```
    - default()
        LiveData 添加默认值

#### LocalImageKtx 扩展
    - File.copyToAlbum
        复制图片文件到相册的Pictures文件夹
    - saveToAlbum
        保存图片Stream到相册的Pictures文件夹
    - saveToAlbum
        保存Bitmap到相册的Pictures文件夹

    ```
    示例用法
    private fun saveImage(bitmap: Bitmap) {
            lifecycleScope.launchUI {
                val ioResult = withContext(Dispatchers.IO) {
                    val uri = bitmap.saveToAlbum(
                        context,
                        "${System.currentTimeMillis()}.png"
                    )
                    uri != null
                }

                if (ioResult) {
                    toastSuccess("保存成功")
                } else {
                    toastSuccess("保存失败")
                }
            }
        }
    ```

#### Log
    - String.i(String)      info 日志打印，用法： "message".i("tag")
    - String.d(String)      debug 日志打印，用法： "message".d("tag")
    - String.v(String)      verbose 日志打印，用法： "message".v("tag")
    - String.w(String)      warning 日志打印，用法： "message".w("tag")
    - String.e(String)      error 日志打印，用法： "message".e("tag")

#### String
    字符串运算
    - add(String)                   两个字符串相加，加法，有异常则为 0
    - sub(String)                   两个字符串相减，减法，有异常则为 0
    - mul(String)                   两个字符串相乘，乘法，有异常则为 0
    - div(String?)                  两个字符串相除，除法，有异常则为 0
    - between(String,String)        是否在传入的两个值的范围内， 不包含传入的两个值
    - betweenAnd(String,String)     是否在传入的两个值的范围内， 包含传入的两个值
    - betweenEnd(String,String)     是否在传入的两个值的范围内， 包含第二个传入的值
    - betweenStart(String,String)   是否在传入的两个值的范围内， 包含第一个传入的值
    - compare(String,String)        比较两个字符类型的数字大小，0 是相等，-1 是第一个小于第二个， 1 是第一个大于第二个
    - copyValue(Context)            复制字符串到粘贴板
    - keep(Int)                     根据传入的Int值，来确定保留几位小数位，超出部分四舍五入
    - keepDown(Int)                 根据传入的Int值，来确定保留几位小数位，超出部分截取
    - keepToInt(Int)                转换为Int整型
    - keepTwo()                     保留两位小数，超出部分四舍五入
    - keepTwoDown()                 保留两位小数，超出部分截取
    - maxKeep(Int)                  根据入参进行保留小数位，超出部分四舍五入
    - maxKeepDown(Int)              根据入参进行保留小数位，超出部分截取
    - maxKeepTwoDown()              最多保留两位小数，超出部分截取


#### View
    - enables(Boolean,Views)    设置 Views 的 isEnable 状态
    - gones(Views)              隐藏 Views,  isVisible = false
    - visibles(Views)           显示 Views,  isVisible = true
    - invisibles(Views)         隐藏 Views,  isInvisible = true
    - onClicks(Views, clickListener)    批量设置 Views 的点击事件
    - onClicksInterval(Long, Views, ClickListener)
        批量设置 Views 的点击事件，并且校验多少毫秒内不可重复点击
    - View.clickInterval(Long, ClickListener) 设置 View的点击时间，并且根据入参，校验不可重复点击
    - click(Long，ClickListener)     设置View点击事件，并且不可重复点击，默认 500ms

#### FragmentUtil
    - showFragment(Fragment, Int)   添加并显示 Fragment
    - replaceFragment(Fragment, Int) 直接替换显示 Fragment
    - clear()                       清理当前页面的所有 Fragment

    ```
    // 日常用法, 在 Activity 中：
    protected val fragmentUtil by lazy {
        FragmentUtil(supportFragmentManager)
    }

    fun method(){
        fragmentUtil.showFragment(fragment, RootId)
        or
        fragmentUtil.replaceFragment(fragment, RootId)
    }

    ```

#### IntentUtils
    - gotoSystemSettings(Context)   直接跳转到当前 App 的系统设置页面

#### LanguageUtil
    - setDefaultLanguage(Context, String)   设置系统默认语言

   ```
   // 一般用法：
   // Application 中：
   override fun onCreate() {
        ...
        super.onCreate()
        LanguageUtil.setDefaultLanguage(this, Locale.ENGLISH.language)
        ...
   }

   // BaseActivity 中：
  override fun onCreate() {
       ...
       super.onCreate(savedInstanceState)
       LanguageUtil.setDefaultLanguage(this, Locale.ENGLISH.language)
       ...
  }
   ```

#### ToastUtil
    - showToast 显示 Toast 弹窗

#### Decimal
    - add(String, String)               两个字符串相加，加法，有异常则为 0
    - sub(String, String)               两个字符串相减，减法，有异常则为 0
    - mul(String, String)               两个字符串相乘，乘法，有异常则为 0
    - div(String, String)               两个字符串相除，除法，有异常则为 0
    - compareTo(String String)          比较两个字符类型的数字大小，0 是相等，-1 是第一个小于第二个， 1 是第一个大于第二个
    - keepTwoNumber(Long)               保留两位整数，不够往前补0，  keepTwoNumber(9) = "09"
    - maxKeepTwoDecimal(String?)        最多保留小数点后两位，自动舍0（四舍五入）
    - maxKeepTwoDecimalDown(String?)    最多保留小数点后两位，自动舍0（四舍五入）
    - keepTwoDecimal(String?)           始终保留小数点后两位（超出四舍五入）
    - keepTwoDecimalDown(String?)       始终保留小数点后两位（超出部分截取）
    - maxKeepDecimal(String, Int)       最多保存小数点后 Int 位，自动舍0（超出四舍五入）
    - maxKeepDecimalDown(String, Int)   最多保存小数点后 Int 位，自动舍0（超出部分截取）
    - keepDecimal(String, Int)          保存小数点后count位（超出四舍五入）
    - keepDecimalDown(String, Int)      保存小数点后count位（超出截取）
    - keepDecimalRange(String, Int, Int) 保留小数位数区间（四舍五入）
    - keepDecimalRangeDown(String, Int, Int) 保留小数位数区间（超出截取）

#### KeyBoardUtils
    - openKeybord(EditText, Context)    打开软键盘
    - closeKeybord(EditText, Context)   关闭软键盘
    - closeKeybord(Context)             关闭软键盘
    - setupUISoftKeyBoardHideSystem(View,Boolean)  设置键盘隐藏机制
    - hideKeyboard(View)                隐藏键盘
    - showSoftInputFromWindow(Context, EditText) EditText获取焦点并显示软键盘

#### LogUtil
    - setLogEnable(Boolean)         控制 Log 是否输出
    - i(String, String)             info 日志打印，用法： LogUtil.i("tag", "message")
    - i(String)                     info 日志打印，用法： LogUtil.i("message")
    - d(String, String)             Debug 日志打印，用法： LogUtil.d("tag", "message")
    - d(String)                     Debug 日志打印，用法： LogUtil.d("message")
    - w(String, String)             warn 日志打印，用法： LogUtil.w("tag", "message")
    - w(String)                     warn 日志打印，用法： LogUtil.w("message")
    - v(String, String)             verbose 日志打印，用法： LogUtil.v("tag", "message")
    - v(String)                     verbose 日志打印，用法： LogUtil.v("message")
    - e(String, String)             error 日志打印，用法： LogUtil.e("tag", "message")
    - e(String)                     error 日志打印，用法： LogUtil.e("message")


#### dimens.xml
    - dp_1 - dp_375    有 1dp 到 375dp 的常用整数配置
    - sp_1 - sp_50      1sp 到 50sp 常用整数配置
    - px_1 - px_375     1px 到 375px 常用整数配置

#### FileUtil
    - convertBase64ToPic        Base64 转换为 Bitmap
    - convertBitmapToBase64     Bitmap 转换为 Base64
    - saveViewAsBitmap(View)    保存 View 内容为 Bitmap

#### ShareUtil
    - shareText         分享文本
    - shareImage        分享图片