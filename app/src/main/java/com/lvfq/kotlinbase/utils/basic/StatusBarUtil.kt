package com.lvfq.kotlinbase.utils.basic

import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager

/**
 * Created by jingting on 2017/9/19.
 */

object StatusBarUtil {

    @TargetApi(19)
    private fun setTranslucentStatus(activity: Activity, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        val bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    /**
     * 状态栏亮色模式，设置状态栏黑色文字、图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    fun StatusBarLightMode(activity: Activity): Int {
        var result = 0
        if (MIUISetStatusBarLightMode(activity, true)) {
            result = 1
        } else if (FlymeSetStatusBarLightMode(activity.window, true)) {
            result = 2
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            result = 3
        }
        return result
    }


    /**
     * 状态栏亮色模式，设置状态栏黑色文字、图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param window
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    fun StatusBarLightMode(window: Window): Int {
        var result = 0
        if (MIUISetStatusBarLightMode(window, true)) {
            result = 1
        } else if (FlymeSetStatusBarLightMode(window, true)) {
            result = 2
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            result = 3
        }
        return result
    }


    /**
     * 状态栏亮色模式，设置状态栏黑色文字、图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    fun StatusBarDarkMode(activity: Activity): Int {
        var result = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUISetStatusBarLightMode(activity, false)) {
                result = 1
            } else if (FlymeSetStatusBarLightMode(activity.window, false)) {
                result = 2
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                result = 3
            }
        }
        return result
    }

    /**
     * 已知系统类型时，设置状态栏黑色文字、图标。
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @param type     1:MIUUI 2:Flyme 3:android6.0
     */
    fun StatusBarLightMode(activity: Activity, type: Int) {
        if (type == 1) {
            MIUISetStatusBarLightMode(activity, true)
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(activity.window, true)
        } else if (type == 3) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

    }

    /**
     * 状态栏暗色模式，清除MIUI、flyme或6.0以上版本状态栏黑色文字、图标
     */
    fun StatusBarDarkMode(activity: Activity, type: Int) {
        if (type == 1) {
            MIUISetStatusBarLightMode(activity, false)
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(activity.window, false)
        } else if (type == 3) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
    }


    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    fun FlymeSetStatusBarLightMode(window: Window?, dark: Boolean): Boolean {
        var result = false
        if (window != null) {
            try {
                val lp = window.attributes
                val darkFlag = WindowManager.LayoutParams::class.java
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meizuFlags = WindowManager.LayoutParams::class.java
                        .getDeclaredField("meizuFlags")
                darkFlag.isAccessible = true
                meizuFlags.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = meizuFlags.getInt(lp)
                if (dark) {
                    value = value or bit
                } else {
                    value = value and bit.inv()
                }
                meizuFlags.setInt(lp, value)
                window.attributes = lp
                result = true
            } catch (e: Exception) {

            }

        }
        return result
    }

    fun MIUISetStatusBarLightMode(window: Window?, dark: Boolean): Boolean {
        var result = false
        if (window != null) {
            val clazz = window.javaClass
            try {
                var darkModeFlag = 0
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag)//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag)//清除黑色字体
                }
                result = true

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (dark) {
                        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    } else {
                        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                    }
                }
            } catch (e: Exception) {

            }

        }
        return result
    }

    /**
     * 需要MIUIV6以上
     *
     * @param activity
     * @param dark     是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    fun MIUISetStatusBarLightMode(activity: Activity, dark: Boolean): Boolean {
        return MIUISetStatusBarLightMode(activity.window, dark)
    }

    /**
     * 隐藏底部导航栏、全屏显示、隐藏状态栏
     *
     * @param activity
     */
    fun hideNavigationBar(activity: Activity, hasFocus: Boolean) {
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            val decorView = activity.window.decorView
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    fun hideStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 19) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            val decorView = activity.window.decorView
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    fun showStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 19) {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)//显示状态栏
        }
    }
}
