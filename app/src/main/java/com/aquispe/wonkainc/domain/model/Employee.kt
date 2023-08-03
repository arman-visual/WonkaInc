package com.aquispe.wonkainc.domain.model

data class Employee(
    val age: Int,
    val country: String,
    val email: String,
    val favorite: Favorite,
    val firstName: String,
    val gender: String,
    val height: Int,
    val id: Int,
    val image: String,
    val lastName: String,
    val profession: String
)
