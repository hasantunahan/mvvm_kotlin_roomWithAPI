package com.tashteam.reqres_kotlin.app.Extensions

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tashteam.reqres_kotlin.R


//GlideWith Image

fun ImageView.glideWithExtensions(url: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .centerCrop()
        .into(this)
}


fun progressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context)
}

@BindingAdapter("android:bindingImageview")
fun dataBindingImage(view: ImageView, url: String?) {
    view.glideWithExtensions(url, progressDrawable(view.context))
}