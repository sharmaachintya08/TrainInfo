package com.example.traininfo.station.StationDetails.TrainDetailData.REST.stationcodes

enum class StationCodes(val stationName : String,
                        val stationCode : String) {
    CHENNAI_CENTRAL("Chennai Central", "MAS"),
    MUMBAI_CST("Mumbai CST", "CSTM"),
    HOWRAH_JUNCTION("Howrah Junction", "HWH"),
    NEW_DELHI("New Delhi", "NDLS"),
    BENGALURU_CITY("Bengaluru City", "SBC"),
    HYDERABAD_SECUNDERABAD("Hyderabad / Secunderabad", "SC"),
    JAIPUR("Jaipur", "JP"),
    CHANDIGARH("Chandigarh", "CDG"),
    KOLKATA("Kolkata", "KOAA"),
    AHMEDABAD("Ahmedabad", "ADI")
}