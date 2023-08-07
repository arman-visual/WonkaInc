package com.aquispe.wonkainc.ui.model

data class EmployeeUiModel(
    val age: Int,
    val country: String,
    val email: String,
    val favorite: FavoriteUiModel,
    val firstName: String,
    val gender: String,
    val height: Int,
    val id: Int,
    val image: String,
    val lastName: String,
    val profession: String
)
