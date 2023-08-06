package com.aquispe.wonkainc.ui.util

import android.widget.ImageView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

fun <T> LifecycleOwner.launchAndCollect(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    body: (T) -> Unit
) {
    lifecycleScope.launch {
        this@launchAndCollect.repeatOnLifecycle(state) {
            flow.collect(body)
        }
    }
}

fun ImageView.loadUrlWithCircleCrop(url: String) {
    Glide.with(context)
        .load(url)
        .circleCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL) // Usar caché de disco para todas las imágenes
        .skipMemoryCache(false) // No ignorar la caché de memoria
        .into(this)
}

fun Throwable.getMessage(): String {
    return when (this) {
        is IOException -> {
            "Network error occurred. Please check your internet connection and try again."
        }
        is HttpException -> {
            when (this.code()) {
                401 -> "Unauthorized: Please login to access this resource."
                404 -> "Resource not found."
                500 -> "Server error occurred. Please try again later."
                else -> "HTTP Error ${this.code()}: ${this.message()}"
            }
        }
        else -> {
            "An unknown error occurred. Please try again later."
        }
    }
}
