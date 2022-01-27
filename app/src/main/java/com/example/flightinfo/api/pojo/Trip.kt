package com.example.flightinfo.api.pojo

import com.google.gson.annotations.SerializedName

data class Trip(
    @SerializedName("from") val from: String,
    @SerializedName("to") val to: String,
)
