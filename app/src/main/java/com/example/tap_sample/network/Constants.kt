package com.example.tap_sample.network

class Constants {
    companion object {

        private var BASE_URL: String = "https://mpvtest.metro.net/rest"
        var TEST_BASE_URL: String = "$BASE_URL/api/"

        var DOWNLOAD_URL ="http://metrotransit.us:8080/ecitation/downloadCitationDB"
        val Database_Name = "metrotap.db"

    }
}