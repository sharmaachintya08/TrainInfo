package com.example.traininfo.station.StationDetails.TrainDetailData.REST.stationcodes

enum class StationCodes(val stationName : String,
                        val stationCode : String) {
    CHENNAI("Chennai Central", "MAS"),
    MUMBAI("Mumbai CST", "CSTM"),
    HOWRAH_JUNCTION("Howrah Junction", "HWH"),
    DELHI("New Delhi", "NDLS"),
    BENGALURU("Bengaluru City", "SBC"),
    HYDERABAD("Hyderabad / Secunderabad", "SC"),
    JAIPUR("Jaipur", "JP"),
    CHANDIGARH("Chandigarh", "CDG"),
    KOLKATA("Kolkata", "KOAA"),
    AHMEDABAD("Ahmedabad", "ADI")
}