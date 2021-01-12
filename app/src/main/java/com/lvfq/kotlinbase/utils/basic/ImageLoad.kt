package com.lvfq.kotlinbase.utils.basic

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.load.engine.DiskCacheStrategy
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
        @DrawableRes placeHolder: Int = 0, @DrawableRes error: Int = 0,
        roundCorner: Int = 0, isCircle: Boolean = false
) {
    val glide = GlideApp.with(this).load(url)
    loadBuild(glide, this, placeHolder, error, roundCorner, isCircle)
}

fun ImageView.load(
        bitmap: Bitmap?,
        @DrawableRes placeHolder: Int = 0, @DrawableRes error: Int = 0,
        roundCorner: Int = 0, isCircle: Boolean = false
) {
    val glide = GlideApp.with(this).load(bitmap)
    loadBuild(glide, this, placeHolder, error, roundCorner, isCircle)
}

fun ImageView.load(
        drawable: Drawable?,
        @DrawableRes placeHolder: Int = 0, @DrawableRes error: Int = 0,
        roundCorner: Int = 0, isCircle: Boolean = false
) {
    val glide = GlideApp.with(this).load(drawable)
    loadBuild(glide, this, placeHolder, error, roundCorner, isCircle)
}

fun ImageView.load(
        uri: Uri?,
        @DrawableRes placeHolder: Int = 0, @DrawableRes error: Int = 0,
        roundCorner: Int = 0, isCircle: Boolean = false
) {
    val glide = GlideApp.with(this).load(uri)
    loadBuild(glide, this, placeHolder, error, roundCorner, isCircle)
}

fun ImageView.load(
        file: File?,
        @DrawableRes placeHolder: Int = 0, @DrawableRes error: Int = 0,
        roundCorner: Int = 0, isCircle: Boolean = false
) {
    val glide = GlideApp.with(this).load(file)
    loadBuild(glide, this, placeHolder, error, roundCorner, isCircle)
}

fun loadBuild(
        inGlide: GlideRequest<Drawable>,
        imageView: ImageView,
        @DrawableRes placeHolder: Int = 0, @DrawableRes error: Int = 0,
        roundCorner: Int = 0, isCircle: Boolean = false
) {
    var glide = inGlide.diskCacheStrategy(DiskCacheStrategy.ALL)
    if (placeHolder != 0) {
        glide = glide.placeholder(placeHolder)
    }
    if (error != 0) {
        glide = glide.error(error)
    }
    if (roundCorner != 0 && !isCircle) {
        glide = glide.transform(RoundedCorners(roundCorner))
    }
    if (isCircle) {
        glide = glide.transform(CircleCrop())
    }
    glide.centerCrop()
            .into(imageView)
}

