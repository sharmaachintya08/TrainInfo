package com.example.traininfo.station.StationDetails.TrainDetailsItems

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.traininfo.R
import com.example.traininfo.station.StationDetails.TrainDetailData.TrainDetailData

class TrainDetailItem(private val detailItemList : ArrayList<TrainDetailData>) : RecyclerView.Adapter<TrainDetailItem.TrainDetailItemInnerClass>() {
    inner class TrainDetailItemInnerClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name_TextView : TextView
        val seatType_TextView : TextView
        val fare_TextView : TextView
        val startingStation_TextView : TextView
        val startingStationTime_TextView : TextView
        val journeyTime_TextView : TextView
        val destinationStation_TextView : TextView
        val destinationStationTime_TextView : TextView
        init{
            //give views their layout object
            name_TextView = itemView.findViewById(R.id.train_name_textview)
            seatType_TextView = itemView.findViewById(R.id.train_seattype_textview)
            fare_TextView = itemView.findViewById(R.id.train_fare_textview)
            startingStationTime_TextView = itemView.findViewById(R.id.train_startingstation_time_textview)
            startingStation_TextView = itemView.findViewById(R.id.train_startingstation_textview)
            journeyTime_TextView = itemView.findViewById(R.id.train_journeytime_textview)
            destinationStationTime_TextView = itemView.findViewById(R.id.train_destinationstaton_time_textview)
            destinationStation_TextView = itemView.findViewById(R.id.train_destinationstaton_textview)
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType: Int): TrainDetailItemInnerClass {
        return TrainDetailItemInnerClass(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.train_fare_detail_item,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return detailItemList.size
    }

    override fun onBindViewHolder(holder: TrainDetailItemInnerClass, position: Int) {
        holder.name_TextView.setText(detailItemList.get(position).train_name)
        holder.startingStation_TextView.setText(detailItemList.get(position).starting_station)
        holder.startingStationTime_TextView.setText(detailItemList.get(position).time_to_start)
        holder.journeyTime_TextView.setText(detailItemList.get(position).travel_time)
        holder.destinationStation_TextView.setText(detailItemList.get(position).destination_station)
        holder.destinationStationTime_TextView.setText(detailItemList.get(position).time_to_reach)
    }
}
