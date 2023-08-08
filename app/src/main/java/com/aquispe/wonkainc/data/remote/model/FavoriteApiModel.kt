package com.aquispe.wonkainc.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FavoriteApiModel(
    val color: String,
    val food: String,
    @Json(name="random_string")
    val randomString: String,
    val song: String
)
