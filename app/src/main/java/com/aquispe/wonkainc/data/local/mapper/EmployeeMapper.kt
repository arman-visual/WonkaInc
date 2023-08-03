package com.aquispe.wonkainc.data.local.mapper

import com.aquispe.wonkainc.data.local.database.entities.EmployeeDbModel
import com.aquispe.wonkainc.data.local.database.entities.FavoriteDbModel
import com.aquispe.wonkainc.domain.model.Employee
import com.aquispe.wonkainc.domain.model.Favorite

fun EmployeeDbModel.toDomain() = Employee(
    id = id,
    image = image,
    firstName = firstName,
    lastName = lastName,
    age = age,
    country = country,
    gender = gender,
    email = email,
    height = height,
    profession = profession,
    favorite = favorite.toDomain()
)

fun FavoriteDbModel.toDomain() = Favorite(
    color = color,
    food = food,
    randomString = randomString,
    song = song
)

fun Employee.toDbModel() = EmployeeDbModel(
    id = id,
    image = image,
    firstName = firstName,
    lastName = lastName,
    age = age,
    country = country,
    gender = gender,
    email = email,
    height = height,
    profession = profession,
    favorite = favorite.toDbModel()
)

fun Favorite.toDbModel() = FavoriteDbModel(
    color = color,
    food = food,
    randomString = randomString,
    song = song
)
