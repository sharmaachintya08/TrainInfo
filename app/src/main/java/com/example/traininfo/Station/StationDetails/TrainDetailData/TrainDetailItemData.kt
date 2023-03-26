package com.example.traininfo.Station.StationDetails.TrainDetailData

data class TrainDetailItemData(
    val train_item_id : Number,
    val image : String?,
    val rating : Number?,
    val train_name : String,
    val seat_type : String,
    val starting_station : String,
    val time_to_start : String,
    val travel_time : String,
    val destination_station : String,
    val time_to_reach : String
)