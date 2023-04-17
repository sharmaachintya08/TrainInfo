package com.example.traininfo.station.StationDetails

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.traininfo.R
import com.example.traininfo.station.StationDetails.TrainDetailData.TrainDetailData
import com.example.traininfo.station.StationDetails.TrainDetailsItems.TrainDetailItem
import org.json.JSONArray


class TrainDetails : Fragment() {
    private lateinit var backButtonImageView : ImageView
    private lateinit var startingStationTextView : TextView
    private lateinit var destinationStationTextView : TextView
    private lateinit var trainDetailRecyclerView : RecyclerView
    private lateinit var context : Context
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        context = container!!.context
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_train_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        NetworkRequestAndSettingUpAdapter()
    }
    private fun initViews(view : View){
        backButtonImageView = view.findViewById(R.id.backbuttonimageview)
        startingStationTextView = view.findViewById(R.id.startingStationTextView)
        destinationStationTextView = view.findViewById(R.id.destinationstationtextview)
        trainDetailRecyclerView = view.findViewById(R.id.traindetailsrecyclerview)
    }
    private fun NetworkRequestAndSettingUpAdapter(){
        val requestQueue = Volley.newRequestQueue(context)
        val url = "https://irctc1.p.rapidapi.com/api/v3/trainBetweenStations?fromStationCode=BVI&toStationCode=NDLS&dateOfJourney=2023-04-18" // Replace with your JSON URL

        val jsonRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                // JSON data fetched successfully
                val items = ArrayList<TrainDetailData>()

                // Loop through all keys in the JSON response
                val keys = response.keys()
                while (keys.hasNext()) {
                    val key = keys.next()

                    // Check if the value associated with the key is a JSON array
                    if (response.get(key) is JSONArray) {
                        val jsonArray = response.getJSONArray(key)
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val trainItemId = jsonObject.getInt("train_number")
                            val trainName = jsonObject.getString("train_name")
                            val starting_station = jsonObject.getString("from_station_name")
                            val timeToStart = jsonObject.getString("from_sta")
                            val travelTime = jsonObject.getString("duration")
                            val destination_station = jsonObject.getString("to_station_name")
                            val timeToReach = jsonObject.getString("to_sta")
                            val item = TrainDetailData (trainItemId,trainName,starting_station,timeToStart,travelTime,destination_station,timeToReach)
                            items.add(item)
                        }
                    }
                }

                // Set up RecyclerView with the fetched data
                val recyclerView = trainDetailRecyclerView
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = TrainDetailItem(items)
            },
            { error ->
                // Error occurred while fetching JSON data
                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            })

        requestQueue.add(jsonRequest)

    }

}