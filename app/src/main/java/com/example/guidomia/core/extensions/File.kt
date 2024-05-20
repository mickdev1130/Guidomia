package com.example.guidomia.core.extensions

import android.content.Context
import com.example.guidomia.core.data.CarItem
import com.google.gson.Gson


const val FILE_NAME_CAR_LIST = "car_list.json"

fun Context.getCarsList(): List<CarItem> {
    return Gson().fromJson(this.readFromJson(FILE_NAME_CAR_LIST) ?: "")
}
