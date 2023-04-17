package com.example.traininfo.station.StationDetails.TrainDetailData

data class TrainDetailData(
    val train_item_id : Number,
    val train_name : String,
    val starting_station : String,
    val time_to_start : String,
    val travel_time : String,
    val destination_station : String,
    val time_to_reach : String
)