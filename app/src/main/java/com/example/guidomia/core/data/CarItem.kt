package com.example.guidomia.core.data

data class CarItem(
    val consList: List<String>,
    val customerPrice: Double,
    val make: String,
    val marketPrice: Double,
    val model: String,
    val prosList: List<String>,
    val rating: Int,
    var isExpanded: Boolean = false
)