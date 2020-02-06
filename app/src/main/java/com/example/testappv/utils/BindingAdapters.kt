package com.example.testappv.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("setHtmlText")
fun TextView.setHtmlText(text: String?) {
    text?.let {
        this.text = Util.htmlTextFrom(text)
    }
}

@BindingAdapter("setImage")
fun ImageView.setImage(imageUrl: String?) {
    if (imageUrl != null && imageUrl.isNotEmpty()) {
        Glide.with(this).load(imageUrl).into(this)
    }
}