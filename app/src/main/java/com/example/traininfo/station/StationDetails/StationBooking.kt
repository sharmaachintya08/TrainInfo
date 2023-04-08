package com.example.traininfo.station.StationDetails

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.traininfo.R
import com.example.traininfo.station.StationDetails.TrainDetailData.REST.RESTTrainData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.util.*


class StationBooking : Fragment() {
    private lateinit var currentLocationTextView : TextView
    private lateinit var startingStationEditText : TextInputEditText
    private lateinit var destinationEditText : TextInputEditText
    private lateinit var proceedButton : MaterialButton

    lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
        doNetworkCall()
    }
    private fun initViews(view : View){
        currentLocationTextView = view.findViewById(R.id.currentlocationtextview)
        startingStationEditText = view.findViewById(R.id.startingstationedittext)
        destinationEditText = view.findViewById(R.id.destinationedittext)
        proceedButton = view.findViewById(R.id.proceedmaterialbutton)
    }
    private fun proceedToNextFragment(){
        proceedButton.setOnClickListener(View.OnClickListener {view ->
            fragmentManager?.commit {
                replace<TrainDetails>(R.id.fragment_container_view)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        })
    }
    private fun getLocation(){
        fusedLocationProviderClient = getFusedLocationProviderClientInstance()
        val task = fusedLocationProviderClient.lastLocation
        checkLocationPermission()
        task.addOnSuccessListener {
            if(it != null){
                Log.d("location","${it.latitude} ${it.longitude}")
                Toast.makeText(context,"${it.latitude} ${it.longitude}",Toast.LENGTH_LONG).show()
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
    private fun doNetworkCall(){
        val test = RESTTrainData()
        test.getTrainData()
    }
}