package com.example.traininfo.station.StationDetails

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.NetworkResponse
import com.android.volley.Request.Method
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.traininfo.R
import com.example.traininfo.station.StationDetails.TrainDetailData.TrainDetailData
import com.example.traininfo.station.StationDetails.TrainDetailsItems.TrainDetailItem
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalQueries.localDate


class TrainDetails : Fragment() {
    private lateinit var backButtonImageView : ImageView
    private lateinit var startingStationTextView : TextView
    private lateinit var destinationStationTextView : TextView
    private lateinit var trainDetailRecyclerView : RecyclerView
    private lateinit var context : Context
    private lateinit var startingStationCode : String
    private lateinit var destinationStationCode : String
    private lateinit var startingStation : String
    private lateinit var destination : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        context = container!!.context
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_train_details, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreateUtil(view)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun onViewCreateUtil(view : View) {
        setStartingAndDestinationCodes()
        initViews(view)
        setStartingAndDestinationStations()
        BackButtonHandler()
        NetworkRequestAndSettingUpAdapter()
    }

    private fun setStartingAndDestinationCodes() {
        startingStationCode = arguments?.getString("startingStationCode").toString()
        destinationStationCode = arguments?.getString("destinationCode").toString()
        startingStation = arguments?.getString("startingStation").toString()
        destination = arguments?.getString("destinationStation").toString()
    }
    private fun initViews(view : View){
        backButtonImageView = view.findViewById(R.id.backbuttonimageview)
        startingStationTextView = view.findViewById(R.id.startingStationTextView)
        destinationStationTextView = view.findViewById(R.id.destinationstationtextview)
        trainDetailRecyclerView = view.findViewById(R.id.traindetailsrecyclerview)
    }
    private fun setStartingAndDestinationStations() {
        startingStationTextView.setText(startingStation)
        destinationStationTextView.setText(destination)
    }
    private fun BackButtonHandler() {
        backButtonImageView.setOnClickListener(View.OnClickListener {
            parentFragmentManager.commit {
                replace<StationBooking>(R.id.fragment_container_view)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        })
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun NetworkRequestAndSettingUpAdapter(){

        val progressDialog = ProgressDialog(context)
        progressDialog.show()
        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val inflater = LayoutInflater.from(context)
        val dialogView: View = inflater.inflate(R.layout.progress_dialog_layout, null)
        progressDialog.setContentView(dialogView)

        val requestQueue = Volley.newRequestQueue(context)
        val currentDate = LocalDate.now()
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd") // Specify the desired date format
        val currentDateStr = currentDate.format(dateFormatter)
        val url = "https://irctc1.p.rapidapi.com/api/v3/trainBetweenStations?fromStationCode=${startingStationCode}&toStationCode=${destinationStationCode}&dateOfJourney=${currentDateStr}"

        val jsonRequest = object : JsonObjectRequest(Method.GET, url, null,
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
                            val item = TrainDetailData(trainItemId, trainName, starting_station, timeToStart, travelTime, destination_station, timeToReach)
                            items.add(item)
                        }
                    }
                }

                // Set up RecyclerView with the fetched data
                val recyclerView = trainDetailRecyclerView
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = TrainDetailItem(items)
                progressDialog.dismiss()
            },
            { error ->
                // Error occurred while fetching JSON data
                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }) {
            // Override the parseNetworkResponse() method to handle the response manually
            override fun parseNetworkResponse(response: NetworkResponse?): Response<JSONObject> {
                val jsonString = String(response?.data ?: ByteArray(0), Charset.forName(
                    HttpHeaderParser.parseCharset(response?.headers)))
                return Response.success(JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response))
            }

            // Override the getHeaders() method to include headers for authentication or authorization
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["X-RapidAPI-Key"] = "2b52c313b2msh26a780c5132d231p13a985jsne54b2cb597e7" // Replace with your RapidAPI key
                headers["X-RapidAPI-Host"] = "irctc1.p.rapidapi.com"
                return headers
            }
        }

        requestQueue.add(jsonRequest)

    }

}