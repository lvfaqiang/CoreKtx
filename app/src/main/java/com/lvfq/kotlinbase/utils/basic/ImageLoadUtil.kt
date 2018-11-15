package com.lvfq.kotlinbase.utils.basic

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

/**
 * ImageLoadUtil
 * @author FaQiang on 2018/9/26 下午1:56
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
object ImageLoadUtil {

    fun loadImg(imageView: ImageView, url: String?) {
        url?.let {
            loadImage(imageView, url)
        }
    }

    fun loadImgCircle(imageView: ImageView, url: String?) {
        url?.let {
            loadImage(imageView, url, isCircle = true)
        }
    }

    fun loadImgRound(imageView: ImageView, url: String?, round: Int) {
        url?.let {
            loadImage(imageView, url, round = round)
        }
    }

    private fun loadImage(imageView: ImageView, url: String, placeHolder: Int = 0, round: Int = 0, isCircle: Boolean = false) {

        val context = imageView.context

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

}