package com.example.traininfo.station.StationDetails.TrainDetailData.REST

class RESTTrainData {
    private lateinit var startingStation : String
    private lateinit var desintationStation : String
    init{

    }
    fun setStartingStation(station : String){
        startingStation = station
    }
    fun setDestinationStation(station : String){
        desintationStation = station
    }
}