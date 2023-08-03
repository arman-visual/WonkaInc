package com.aquispe.wonkainc.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmployeeApiModel(
    val age: Int,
    val country: String,
    val email: String,
    val favorite: FavoriteApiModel,
    @Json(name="first_name")
    val firstName: String,
    val gender: String,
    val height: Int,
    val id: Int,
    val image: String,
    @Json(name="last_name")
    val lastName: String,
    val profession: String
)
