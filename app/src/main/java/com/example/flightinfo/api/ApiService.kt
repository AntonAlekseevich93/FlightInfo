package com.example.flightinfo.api

import com.example.flightinfo.api.pojo.Flights
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {
    @GET("ott/search")
    fun getFlights(): Single<List<Flights>>
}