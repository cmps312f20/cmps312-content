package qu.cmps312.lingosnacks.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import coil.load
import qu.cmps312.lingosnacks.R

/**
 * Binding Adapter that hides a view if the number is zero.
 */
@BindingAdapter("app:hideIfZero")
fun View.hideIfZero(value: Int) {
    this.visibility = if (value == 0) View.GONE else View.VISIBLE
}

/**
 * Binding Adapter that hides a view if the value is false.
 */
@BindingAdapter("app:hideIfFalse")
fun View.hideIfFalse(value: Boolean) {
    this.visibility = if (!value) View.GONE else View.VISIBLE
}

    // Load an image into an ImageView from a Url
// Uses Coil Lib for downloading images https://coil-kt.github.io/coil/
// This requires adding <uses-permission android:name="android.permission.INTERNET" /> to the manifest
@BindingAdapter("app:imageUrl")
fun ImageView.loadImage(url: String) {
    if (url.isEmpty()) {
        val defaultImg = ContextCompat.getDrawable(this.context, R.drawable.img_default_package_img)
        this.setImageDrawable(defaultImg)
    } else
        this.load(url)
}