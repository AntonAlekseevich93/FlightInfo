package com.example.flightinfo.support

class AirportsName {
    fun getNameAirports(name: String): String {
        when (name) {
            "SVO" -> return "Шереметьево"
            "HND" -> return "Токио"
            "NRT" -> return "Нарита"
            "EWR" -> return "Ньюарк"
            "DME" -> return "Домодедово"
            "DOH" -> return "Доха"
            "JFK" -> return "Аэропорт Кеннеди"
            "LHR" -> return "Хитроу"
            "FRA" -> return "Франкфурт"
        }
        return "Неизвестно"
    }
}