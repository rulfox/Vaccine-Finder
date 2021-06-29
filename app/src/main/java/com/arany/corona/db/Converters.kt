package com.arany.corona.db

import androidx.room.TypeConverter
import com.arany.corona.Utilities.parseJson
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromListOfString(value: List<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun toListOfString(value: String) = Gson().parseJson<List<String>?>(value)
}