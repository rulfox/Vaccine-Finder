package com.arany.corona

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Utilities {
    inline fun <reified T> Gson.parseJson(json: String) = fromJson<T>(json, object: TypeToken<T>() {}.type)
}