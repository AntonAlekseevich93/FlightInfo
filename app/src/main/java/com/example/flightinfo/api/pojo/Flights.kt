package com.example.flightinfo.api.pojo

import com.google.gson.annotations.SerializedName

data class Flights(
    @SerializedName("currency") val currency: String,
    @SerializedName("prices") val listPrices: List<Price>,
    @SerializedName("trips") val listTrips: List<Trip>
)
