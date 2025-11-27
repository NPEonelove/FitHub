package com.kuuuzaa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kuuuzaa.mobile.R
import com.kuuuzaa.mobile.databinding.ListTrainBinding
import com.kuuuzaa.retrofit.Train

class TrainAdapter() : ListAdapter<Train, TrainAdapter.Holder>(Comparator()) {

    class Holder (view: View): RecyclerView.ViewHolder(view){
        private val binding = ListTrainBinding.bind(view)

        fun bind(train:Train) = with(binding){
            listTrainTitle.text = train.title
            listTrainDesc.text = train.description
        }
    }

    class Comparator: DiffUtil.ItemCallback<Train>(){
        override fun areItemsTheSame(oldItem: Train, newItem: Train): Boolean {
            return oldItem.trainId == newItem.trainId
        }

        override fun areContentsTheSame(oldItem: Train, newItem: Train): Boolean {
            return oldItem == newItem
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_train,parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }






}