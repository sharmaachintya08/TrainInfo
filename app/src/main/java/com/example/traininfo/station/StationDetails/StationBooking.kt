package com.example.traininfo.station.StationDetails

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.traininfo.R
import com.example.traininfo.station.StationDetails.TrainDetailData.REST.stationcodes.StationNamesToCodesConversion
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText


class StationBooking : Fragment() {
    private lateinit var currentLocationTextView : TextView
    private lateinit var startingStationEditText : TextInputEditText
    private lateinit var destinationEditText : TextInputEditText
    private lateinit var proceedButton : MaterialButton

    lateinit var fusedLocationProviderClient : FusedLocationProviderClient

    private lateinit var thiscontext : Context
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thiscontext = container!!.context
        return inflater.inflate(R.layout.fragment_station_booking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreatedUtil(view)
    }
    fun onViewCreatedUtil(view : View){
        initViews(view)
        getLocation()
        proceedToNextFragment()
    }
    private fun initViews(view : View){
        currentLocationTextView = view.findViewById(R.id.currentlocationtextview)
        startingStationEditText = view.findViewById(R.id.startingstationedittext)
        destinationEditText = view.findViewById(R.id.destinationedittext)
        proceedButton = view.findViewById(R.id.proceedmaterialbutton)
    }
    private fun proceedToNextFragment(){
        proceedButton.setOnClickListener(View.OnClickListener {view ->
            if(startingStationEditText.text!!.isNotEmpty() && destinationEditText.text!!.isNotEmpty()) {
                Log.d("check","check")
                setStationData()
                //replacingFragmentOnButtonClick()
            }
        })
    }
    private fun replacingFragmentOnButtonClick() {
            fragmentManager?.commit {
                replace<TrainDetails>(R.id.fragment_container_view)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
    }
    private fun setStationData() {
        val stationData = StationNamesToCodesConversion()
        stationData.setStartingStation("${startingStationEditText.text}")
        stationData.setDestinationStation("${destinationEditText.text}")
    }
    private fun getLocation(){
        fusedLocationProviderClient = getFusedLocationProviderClientInstance()
        val task = fusedLocationProviderClient.lastLocation
        checkLocationPermission()
        task.addOnSuccessListener {
            if(it != null){
                val addressList = Geocoder(thiscontext).getFromLocation(it.latitude,it.longitude,1)
                if(addressList!!.isNotEmpty()){
                    val address = addressList[0]
                    val state = address.adminArea
                    currentLocationTextView.setText(state)
                    startingStationEditText.setText(state)
                }
            }
        }
        task.addOnFailureListener{
            Log.d("location","${it.message}")
        }
    }
    private fun getFusedLocationProviderClientInstance(): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(requireActivity())
    }
    private fun checkLocationPermission(){
        if(isCheckSelfPermission()){
            getRequestPermissions()
        }
    }
    private fun isCheckSelfPermission() : Boolean {
        return (ActivityCompat.checkSelfPermission(
            requireActivity(),
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireActivity(),
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
                != PackageManager.PERMISSION_GRANTED
                )
    }
    private fun getRequestPermissions(){
        return ActivityCompat.requestPermissions(requireActivity(),arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION),101)
    }
}