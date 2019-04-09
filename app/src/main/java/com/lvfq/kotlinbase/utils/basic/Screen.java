package com.lvfq.kotlinbase.utils.basic;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * Created by xianrui on 15/4/29.
 */
public class Screen {

    private static int mScreenWidth = 0;
    private static int mScreenHeight = 0;
    private static int mStatusBarHeight = 0;
    private static int mDPI = 0;
    private static float mScale = 1;
    private static float mFontScale = 1;
    private static Context mContext;

    public static void initScreen(Context context) {
        mContext = context;
        DisplayMetrics displaymetrics = new DisplayMetrics();
        WindowManager window = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
        window.getDefaultDisplay().getMetrics(displaymetrics);
        mScreenWidth = displaymetrics.widthPixels;
        mScreenHeight = displaymetrics.heightPixels;
        mDPI = displaymetrics.densityDpi;

        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        mStatusBarHeight = sbar;

        mScale = context.getResources().getDisplayMetrics().density;
        mFontScale = context.getResources().getDisplayMetrics().scaledDensity;
    }

    public static int getStatusBarHeight() {
        return mStatusBarHeight;
    }

    public static int getScreenWidth() {
        initScreen(mContext);
        return mScreenWidth;
    }

    public static int getScreenHeight() {
        return mScreenHeight;
    }

    public static int getDPI() {
        return mDPI;
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(float pxValue) {
        return (int) (pxValue / mScale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue) {
        return (int) (dipValue * mScale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(float pxValue) {
        return (int) (pxValue / mFontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        return (int) (spValue * mFontScale + 0.5f);
    }


}
