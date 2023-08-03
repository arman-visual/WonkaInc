package com.aquispe.wonkainc.data.local.database

import androidx.room.TypeConverter
import com.aquispe.wonkainc.data.local.database.entities.FavoriteDbModel
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun stringToFavorite(s: String): FavoriteDbModel = s.toObject()

    @TypeConverter
    fun favoriteToString(favorite: FavoriteDbModel): String = favorite.fromObject()

    @TypeConverter
    fun fromString(string: String?): List<String> {
        if (string == null) {
            return emptyList()
        }
        return string.split(",")
    }

    @TypeConverter
    fun toString(list: List<String>?): String {
        if (list == null) {
            return ""
        }
        return list.joinToString(",")
    }

    private inline fun <reified T> String.toObject(): T {
        return Gson().fromJson(
            this,
            object : com.google.gson.reflect.TypeToken<T>() {}.type
        )
    }

    private inline fun <reified T> T.fromObject(): String {
        return Gson().toJson(this)
    }
}
