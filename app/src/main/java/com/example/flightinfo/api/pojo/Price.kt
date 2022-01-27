package com.example.flightinfo.api.pojo

import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("type") val type: String,
    @SerializedName("amount") val amount: Int
) {
    var isChecked: Boolean = false
}