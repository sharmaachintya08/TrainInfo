package com.example.traininfo.Station.StationDetails.TrainDetailsItems

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.traininfo.R
import com.example.traininfo.Station.StationDetails.TrainDetailData.TrainDetailItemData

class TrainDetailItem(private val detailItemList : ArrayList<TrainDetailItemData>) : RecyclerView.Adapter<TrainDetailItem.TrainDetailItemInnerClass>() {
    inner class TrainDetailItemInnerClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init{
            //give views their layout object
        }
    }

    override fun onCreateViewHolder(viewGroup : ViewGroup, viewType: Int): TrainDetailItemInnerClass {
        return TrainDetailItemInnerClass(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.train_fare_detail_item,false)
        )
    }

    override fun getItemCount(): Int {
        return detailItemList.size
    }

    override fun onBindViewHolder(holder: TrainDetailItemInnerClass, position: Int) {

    }
}
