package com.example.level21.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.level21.R

fun ImageView.loadCircleImage(url: String?) {
    Glide.with(this)
        .load(url)
        .circleCrop()
        .placeholder(R.drawable.ic_android_black)
        .into(this)
}

