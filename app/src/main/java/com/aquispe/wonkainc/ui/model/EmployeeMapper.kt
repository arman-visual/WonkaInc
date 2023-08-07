package com.aquispe.wonkainc.ui.model

import com.aquispe.wonkainc.domain.model.Employee
import com.aquispe.wonkainc.domain.model.Favorite

fun Employee.toUiModel() = EmployeeUiModel(
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
    favorite = favorite.toUiModel()
)

fun Favorite.toUiModel() = FavoriteUiModel(
    color = getColor(),
    food = getFood(),
    randomString = randomString,
    song = song
)

private fun Favorite.getFood() = when (food) {
    "Chocolat" -> Food.Chocolat
    "cocoa nuts" -> Food.CocoaNut
    else -> throw IllegalArgumentException("Valor desconocido para Food: $food")
}

private fun Favorite.getColor() = when (color) {
    "blue" -> Color.Blue
    "red" -> Color.Red
    else -> throw IllegalArgumentException("Valor desconocido para Color: $color")
}
