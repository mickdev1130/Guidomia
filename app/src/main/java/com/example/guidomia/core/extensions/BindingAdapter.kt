package com.example.guidomia.core.extensions

import android.view.View
import android.widget.ImageView
import java.text.DecimalFormat
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.guidomia.R
import com.example.guidomia.core.data.CarItem

fun Double.toShortString(): String {
    return when {
        this >= 1_000_000 -> "${DecimalFormat("#.#").format(this / 1_000_000)}M"
        this >= 1_000 -> "${DecimalFormat("#.#").format(this / 1_000)}k"
        else -> this.toString()
    }
}

@BindingAdapter("android:textFormatted")
fun setFormattedText(view: TextView, value: Double?) {
    view.text = value?.toShortString() ?: ""
}

@BindingAdapter("loadImageFromDrawable")
fun ImageView.setImageFromDrawable(source: String) {
    source?.let {
        val path = resources.getIdentifier(source, "drawable", context.packageName)
        Glide.with(context)
            .load(path)
            .into(this)
    }
}

@BindingAdapter("imageResource")
fun setImageResource(imageView: ImageView, car: CarItem) {
    val imageResId = when (car.make to car.model) {
        "Land Rover" to "Range Rover" -> R.drawable.img_range_rover
        "Alpine" to "Roadster" -> R.drawable.img_alpine_roadster
        "BMW" to "3300i" -> R.drawable.img_bmw_330i
        "Mercedes Benz" to "GLE coupe" -> R.drawable.img_mercedez_benz_glc
        else -> null
    }
    if (imageResId != null) {
        imageView.setImageResource(imageResId)
        imageView.visibility = View.VISIBLE
    } else {
        imageView.visibility = View.INVISIBLE
    }
}