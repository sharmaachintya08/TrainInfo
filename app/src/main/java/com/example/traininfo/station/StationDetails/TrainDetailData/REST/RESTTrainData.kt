package com.example.traininfo.station.StationDetails.TrainDetailData.REST

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.traininfo.station.StationDetails.TrainDetailData.REST.stationcodes.StationCodes

class RESTTrainData(val context : Context) {
    private var TAG = "networkrequest"
    private lateinit var startingStation : String
    private lateinit var desintationStation : String
    private lateinit var currentDate : String


    fun setStartingStation(station : String){
        startingStation = station
    }
    fun setDestinationStation(station : String){
        desintationStation = station
    }
    fun setCurrentDate(date : String){
        currentDate = date
    }
    fun getStartingStationCode() : String? {
        val stationCodes = enumValues<StationCodes>().find { it ->
            it.name == startingStation
        }
        return stationCodes?.stationCode
    }
    fun getDestinationStationCode() : String? {
        val stationCodes = enumValues<StationCodes>().find { it ->
            it.name == desintationStation
        }
        return stationCodes?.stationCode
    }
    fun getTrainData(){
        try {
            doNetworkRequest()
        }catch (e : Exception) {
            Log.d(TAG,"exception : ${e}")
        }
    }
    fun doNetworkRequest(){
        val url = "https://irctc1.p.rapidapi.com/api/v3/trainBetweenStations?fromStationCode=${getStartingStationCode()}&toStationCode=${getDestinationStationCode()}&dateOfJourney=${currentDate}"
        val request = object : JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
            // Handle successful response
            val message = response.toString()
            Log.d(TAG, message)
        }, Response.ErrorListener { error ->
            // Handle error response
            val errorMessage = error.localizedMessage
            Log.e(TAG, "Error: $errorMessage")
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["X-RapidAPI-Key"] = "2b52c313b2msh26a780c5132d231p13a985jsne54b2cb597e7"
                headers["X-RapidAPI-Host"] = "irctc1.p.rapidapi.com"
                return headers
            }
        }
        Volley.newRequestQueue(context).add(request)
    }
}