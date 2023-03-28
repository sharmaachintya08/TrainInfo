package com.example.traininfo.Station.StationDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traininfo.R
import com.example.traininfo.Station.StationDetails.TrainDetailData.TrainDetailData
import com.example.traininfo.Station.StationDetails.TrainDetailsItems.TrainDetailItem


class TrainDetails : Fragment() {
    private lateinit var backButtonImageView : ImageView
    private lateinit var startingStationTextView : TextView
    private lateinit var destinationStationTextView : TextView
    private lateinit var trainDetailRecyclerView : RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_train_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
    }
    private fun initViews(view : View){
        backButtonImageView = view.findViewById(R.id.backbuttonimageview)
        startingStationTextView = view.findViewById(R.id.startingStationTextView)
        destinationStationTextView = view.findViewById(R.id.destinationedittext)
        trainDetailRecyclerView = view.findViewById(R.id.traindetailsrecyclerview)
    }
    private fun createDummyData() : ArrayList<TrainDetailData>{
        val trainList = ArrayList<TrainDetailData>()
        trainList.add(TrainDetailData(1,"",4,"express","121AC","Delhi","9:45","6:00:00","bangalore","6:45"))
        return trainList
    }
    private fun implementRecyclerView(){
        trainDetailRecyclerView.layoutManager = LinearLayoutManager(context)
        //add arraylist here
        trainDetailRecyclerView.adapter = TrainDetailItem(createDummyData())
    }
}