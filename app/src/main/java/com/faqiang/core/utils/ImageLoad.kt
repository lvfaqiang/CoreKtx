package com.faqiang.core.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.faqiang.core.GlideApp
import com.faqiang.core.GlideRequest
import java.io.File

/**
 * ImageLoad2020-01-02 12:21
 * @desc :
 *
 */

fun ImageView.load(
    url: String?,
    @DrawableRes placeHolder: Int = 0,
    @DrawableRes error: Int = 0,
    roundCorner: Int = 0,
    isCircle: Boolean = false,
    isCenterCrop: Boolean = false
) {
    val glide = GlideApp.with(this).load(url)
    loadBuild(glide, this, placeHolder, error, roundCorner, isCircle, isCenterCrop)
}

fun ImageView.load(
    bitmap: Bitmap?,
    @DrawableRes placeHolder: Int = 0,
    @DrawableRes error: Int = 0,
    roundCorner: Int = 0,
    isCircle: Boolean = false,
    isCenterCrop: Boolean = true
) {
    val glide = GlideApp.with(this).load(bitmap)
    loadBuild(glide, this, placeHolder, error, roundCorner, isCircle, isCenterCrop)
}

fun ImageView.load(
    drawable: Drawable?,
    @DrawableRes placeHolder: Int = 0,
    @DrawableRes error: Int = 0,
    roundCorner: Int = 0,
    isCircle: Boolean = false,
    isCenterCrop: Boolean = true
) {
    val glide = GlideApp.with(this).load(drawable)
    loadBuild(glide, this, placeHolder, error, roundCorner, isCircle, isCenterCrop)
}

fun ImageView.load(
    uri: Uri?,
    @DrawableRes placeHolder: Int = 0,
    @DrawableRes error: Int = 0,
    roundCorner: Int = 0,
    isCircle: Boolean = false,
    isCenterCrop: Boolean = true
) {
    val glide = GlideApp.with(this).load(uri)
    loadBuild(glide, this, placeHolder, error, roundCorner, isCircle, isCenterCrop)
}

fun ImageView.load(
    file: File?,
    @DrawableRes placeHolder: Int = 0,
    @DrawableRes error: Int = 0,
    roundCorner: Int = 0,
    isCircle: Boolean = false,
    isCenterCrop: Boolean = true
) {
    val glide = GlideApp.with(this).load(file)
    loadBuild(glide, this, placeHolder, error, roundCorner, isCircle, isCenterCrop)
}

fun ImageView.loadAny(
    any: Any,
    @DrawableRes placeHolder: Int = 0,
    @DrawableRes error: Int = 0,
    roundCorner: Int = 0,
    isCircle: Boolean = false,
    isCenterCrop: Boolean = true
) {
    val glide = GlideApp.with(this).load(any)
    loadBuild(glide, this, placeHolder, error, roundCorner, isCircle, isCenterCrop)
}


fun ImageView.loadGif(
    @DrawableRes gifUrl: Int?,
    loopCount: Int,
    callBack: (GifDrawable?) -> Unit = {}
) {
    GlideApp.with(this.context)
        .load(gifUrl)
        .addListener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any,
                target: Target<Drawable?>,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any,
                target: Target<Drawable?>,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                if (resource is GifDrawable) {
                    resource.setLoopCount(loopCount)
                    callBack(resource)
                }
                return false
            }
        }).into(this)
}

/**
 * 适用于每次都需要重新加载的GIF,
 */
fun ImageView.loadGifNoCache(
    @DrawableRes gifUrl: Int?,
    loopCount: Int,
    callBack: (GifDrawable?) -> Unit = {}
) {
    GlideApp.with(this.context)
        .load(gifUrl)
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .addListener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any,
                target: Target<Drawable?>,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any,
                target: Target<Drawable?>,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                if (resource is GifDrawable) {
                    resource.setLoopCount(loopCount)
                    callBack(resource)
                }
                return false
            }
        }).into(this)
}


fun loadBuild(
    inGlide: GlideRequest<Drawable>,
    imageView: ImageView,
    @DrawableRes placeHolder: Int = 0,
    @DrawableRes error: Int = 0,
    roundCorner: Int = 0,
    isCircle: Boolean = false,
    isCenterCrop: Boolean = false
) {
    var glide = inGlide.diskCacheStrategy(DiskCacheStrategy.ALL)
    if (placeHolder != 0) {
        glide = glide.placeholder(placeHolder)
    }
    if (error != 0) {
        glide = glide.error(error)
    }
    glide = if (isCircle) {
        if (isCenterCrop) {
            glide.transform(CircleCrop(), CenterCrop())
        } else {
            glide.transform(CircleCrop())
        }
    } else if (roundCorner > 0) {
        if (isCenterCrop) {
            val roundOptions = RequestOptions().transform(RoundedCorners(roundCorner))
            roundOptions.transform(CenterCrop(), RoundedCorners(roundCorner))
            glide.apply(roundOptions)
        } else {
            glide.transform(RoundedCorners(roundCorner))
        }
    } else {
        if (isCenterCrop) {
            glide.transform(CenterCrop())
        } else {
            glide
        }
    }
    glide.into(imageView)
}

