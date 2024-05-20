package com.example.guidomia.core.extensions

import android.content.Context
import java.io.IOException

fun Context.readFromJson(fileName: String): String? {
    var jsonString: String? = null
    try {
        jsonString = assets.open("json/$fileName").bufferedReader().use { it.readText() }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return jsonString
}
