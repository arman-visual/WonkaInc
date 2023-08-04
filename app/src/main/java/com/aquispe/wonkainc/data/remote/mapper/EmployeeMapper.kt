package com.aquispe.wonkainc.data.remote.mapper

import com.aquispe.wonkainc.data.local.database.entities.EmployeeDbModel
import com.aquispe.wonkainc.data.local.database.entities.FavoriteDbModel
import com.aquispe.wonkainc.data.remote.model.EmployeeApiModel
import com.aquispe.wonkainc.data.remote.model.FavoriteApiModel
import com.aquispe.wonkainc.domain.model.Employee
import com.aquispe.wonkainc.domain.model.Favorite

fun EmployeeApiModel.toDomain() = Employee(
    age = age,
    country = country,
    email = email,
    favorite = favorite.toDomain(),
    firstName = firstName,
    gender = gender,
    height = height,
    id = id ?: 0,
    image = image,
    lastName = lastName,
    profession = profession
)

fun FavoriteApiModel.toDomain() = Favorite(
    color = color,
    food = food,
    randomString = randomString,
    song = song
)

fun EmployeeApiModel.toDbModel() = EmployeeDbModel(
    age = age,
    country = country,
    email = email,
    favorite = favorite.toDbModel(),
    firstName = firstName,
    gender = gender,
    height = height,
    id = requireNotNull(id),
    image = image,
    lastName = lastName,
    profession = profession
)

fun FavoriteApiModel.toDbModel() = FavoriteDbModel(
    color = color,
    food = food,
    randomString = randomString,
    song = song
)
