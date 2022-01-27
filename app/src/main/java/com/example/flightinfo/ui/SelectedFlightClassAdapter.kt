package com.example.flightinfo.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flightinfo.R
import com.example.flightinfo.api.pojo.Price
import com.example.flightinfo.ui.listeners.ISelectedFlightClass

class SelectedFlightClassAdapter(
    private var listPrice: List<Price>,
    private val context: Context,
    private val listeners: ISelectedFlightClass
) :
    RecyclerView.Adapter<SelectedFlightClassAdapter.SelectedFlightsViewHolder>() {

    fun setData(list: List<Price>) {
        listPrice = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedFlightsViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.selected_flight_class_item, parent, false)
        return SelectedFlightsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelectedFlightsViewHolder, position: Int) {
        holder.radioButtonClassSelectedItem.text = listPrice[position].type
        holder.tvCostSelectedItem.text = listPrice[position].amount.toString()
        if (listPrice[position].isChecked) holder.radioButtonClassSelectedItem.isChecked
        else holder.radioButtonClassSelectedItem.isChecked = false

        holder.radioButtonClassSelectedItem.setOnClickListener {
            for (price in listPrice) {
                price.isChecked = false
            }
            listPrice[position].isChecked = true
            listeners.setPositionSelectedFlightClass(position)
            notifyDataSetChanged()
        }


    }

    override fun getItemCount(): Int {
        return listPrice.size
    }

    class SelectedFlightsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val radioButtonClassSelectedItem: RadioButton =
            itemView.findViewById(R.id.radioButtonClassSelectedItem)
        val tvCostSelectedItem: TextView = itemView.findViewById(R.id.tvCostSelectedItem)
    }
}