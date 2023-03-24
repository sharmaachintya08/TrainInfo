package com.example.traininfo.Station

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.traininfo.R
import com.example.traininfo.Station.StationDetails.StationBooking

class StationDetailHolder : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_station_detail_holder)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<StationBooking>(R.id.fragment_container_view)
            }
        }
    }
}