package com.example.traininfo.station.StationDetails.TrainDetailData.REST

import android.util.Log
import kotlinx.coroutines.*
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
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
        GlobalScope.launch {
            doNetworkRequest()
        }
    }
    suspend fun doNetworkRequest(){
        try {

        }catch(e : java.lang.Exception) {

        }
    }

    private fun printJSON(response: Response) {

    }
}