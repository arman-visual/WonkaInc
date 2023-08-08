package com.aquispe.wonkainc.ui.util

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aquispe.wonkainc.R
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
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .skipMemoryCache(false)
        .into(this)
}

fun Throwable.getMessage(context: Context): String {
    return when (this) {
        is IOException -> {
            context.getString(R.string.network_error)
        }
        is HttpException -> {
            when (this.code()) {
                401 -> context.getString(R.string.unauthorized_error)
                404 -> context.getString(R.string.resource_error_message)
                500 -> context.getString(R.string.server_error_message)
                else -> "HTTP Error ${this.code()}: ${this.message()}"
            }
        }
        else -> {
            context.getString(R.string.unknow_error_message)
        }
    }
}
