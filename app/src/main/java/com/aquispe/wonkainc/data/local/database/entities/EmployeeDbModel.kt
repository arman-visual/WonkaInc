package com.aquispe.wonkainc.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EmployeeDbModel(
    val age: Int,
    val country: String,
    val email: String,
    val favorite: FavoriteDbModel,
    val firstName: String,
    val gender: String,
    val height: Int,
    @PrimaryKey
    val id: Int,
    val image: String,
    val lastName: String,
    val profession: String
)
