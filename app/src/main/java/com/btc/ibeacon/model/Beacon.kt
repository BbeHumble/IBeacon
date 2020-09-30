package com.btc.ibeacon.model

import java.io.Serializable

data class Beacon(
    val mac: String,
    val distance: Double,
    var rssi: Int,
    val UUID: String,
    val major: String,
    val minor: String
) : Serializable