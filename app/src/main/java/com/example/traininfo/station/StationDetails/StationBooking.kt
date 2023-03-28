package com.example.traininfo.station.StationDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.traininfo.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText


class StationBooking : Fragment() {
    private lateinit var currentLocationTextView : TextView
    private lateinit var startingStationEditText : TextInputEditText
    private lateinit var destinationEditText : TextInputEditText
    private lateinit var proceedButton : MaterialButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_station_booking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
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
            fragmentManager?.commit {
                replace<TrainDetails>(R.id.fragment_container_view)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        })
    }
}