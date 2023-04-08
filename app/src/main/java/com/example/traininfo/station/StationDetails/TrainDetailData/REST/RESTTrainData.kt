package com.example.traininfo.station.StationDetails.TrainDetailData.REST

import android.util.Log
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.Date

class RESTTrainData {
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
    fun getTrainData(){
        GlobalScope.launch(newSingleThreadContext("thread1")) {
            val networkRequest = doNetworkRequest()
            Log.d(TAG, networkRequest.toString())
        }
    }
    suspend fun doNetworkRequest(): Response {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://indian-railway-irctc.p.rapidapi.com/getTrainId?trainno=1205")
            .get()
            .addHeader("X-RapidAPI-Key", "2b52c313b2msh26a780c5132d231p13a985jsne54b2cb597e7")
            .addHeader("X-RapidAPI-Host", "indian-railway-irctc.p.rapidapi.com")
            .build()
        return client.newCall(request).execute()
    }
}