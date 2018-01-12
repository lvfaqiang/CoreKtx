package com.lvfq.kotlinbase.ext

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

/**
 * ImageLoad
 * @author FaQiang on 2018/1/9 下午5:15
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
/**
 * 加载图片，默认方式
 */
fun loadImageDef(context: Context, url: String, placeHolder: Int, imageView: ImageView) {
    loadImage(context, url, placeHolder, 0, imageView, false)
}

fun loadImageDef(context: Context, url: String, imageView: ImageView) {
    loadImage(context, url, 0, 0, imageView, false)
}

/**
 * 加载圆角图
 */
fun loadImageRound(context: Context, url: String, placeHolder: Int, round: Int, imageView: ImageView) {
    loadImage(context, url, placeHolder, round, imageView, false)
}

/**
 * 加载圆角图 ，默认圆角为 4
 */
fun loadImageRound(context: Context, url: String, placeHolder: Int, imageView: ImageView) {
    loadImage(context, url, placeHolder, 4, imageView, false)
}


@SuppressLint("CheckResult")
private fun loadImage(context: Context, url: String, placeHolder: Int, round: Int, imageView: ImageView, isCircle: Boolean) {
    val option = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
    if (placeHolder != 0) {
        option.placeholder(placeHolder)
    }
    if (round > 0) {
        option.transforms(CenterCrop(), RoundedCorners(round))
    } else {
        // 是否是圆形图片
        if (isCircle) {
            option.transforms(CenterCrop(), CircleCrop())
        } else {
            option.transforms(CenterCrop())
        }
    }
    Glide.with(context)
            .load(url)
            .apply(option)
            .transition(DrawableTransitionOptions.withCrossFade(800))
            .into(imageView)
}