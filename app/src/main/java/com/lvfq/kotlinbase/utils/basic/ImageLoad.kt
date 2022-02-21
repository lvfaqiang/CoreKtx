package com.lvfq.kotlinbase.utils.basic

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.lvfq.kotlinbase.GlideApp
import com.lvfq.kotlinbase.GlideRequest
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
    isCenterCrop: Boolean = true
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

fun loadBuild(
    inGlide: GlideRequest<Drawable>,
    imageView: ImageView,
    @DrawableRes placeHolder: Int = 0,
    @DrawableRes error: Int = 0,
    roundCorner: Int = 0,
    isCircle: Boolean = false,
    isCenterCrop: Boolean = true
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
            glide.transform(RoundedCorners(roundCorner), CenterCrop())
        } else {
            glide.transform(RoundedCorners(roundCorner))
        }
    } else {
        glide.transform(CenterCrop())
    }
    glide.into(imageView)
}

