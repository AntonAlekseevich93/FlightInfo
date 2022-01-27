package com.example.flightinfo.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.flightinfo.R
import com.example.flightinfo.api.pojo.Flights
import com.example.flightinfo.api.pojo.Price
import com.example.flightinfo.support.AirportsName
import com.example.flightinfo.ui.listeners.ISelectedFlight

class FlightsAdapter(
    private var listOfFlights: List<Flights>,
    private val context: Context,
    private val listeners: ISelectedFlight
) :
    RecyclerView.Adapter<FlightsAdapter.FlightsViewHolder>() {

    private val airportsName: AirportsName = AirportsName()

    fun setData(list: List<Flights>) {
        listOfFlights = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.flight_item, parent, false)
        return FlightsViewHolder(view, listeners)
    }

    override fun onBindViewHolder(holder: FlightsViewHolder, position: Int) {
        holder.tvAmountTransfers.text = listOfFlights[position].listTrips.size.toString()
        holder.tvCost.text = getMinimalCost(listOfFlights[position].listPrices)
        holder.tvFromFlights.text =
            airportsName.getNameAirports(listOfFlights[position].listTrips[0].from)
        val indexLastAirports = listOfFlights[position].listTrips.size - 1
        holder.tvToFlights.text =
            airportsName.getNameAirports(listOfFlights[position].listTrips[indexLastAirports].to)
    }

    override fun getItemCount(): Int {
        return listOfFlights.size
    }

    private fun getMinimalCost(list: List<Price>): String {
        var minCost = list[0].amount;
        if (list.size > 1) {
            for (i in list.listIterator()) {
                if (i.amount < minCost) minCost = i.amount
            }
        }
        return minCost.toString();
    }


    class FlightsViewHolder(itemView: View, listeners: ISelectedFlight) :
        RecyclerView.ViewHolder(itemView) {
        val tvFromFlights: TextView = itemView.findViewById(R.id.tvFromFlight)
        val tvToFlights: TextView = itemView.findViewById(R.id.tvToFlight)
        val tvAmountTransfers: TextView = itemView.findViewById(R.id.tvAmountTransfers)
        val tvCost: TextView = itemView.findViewById(R.id.tvCost)
        private val cardItem: CardView = itemView.findViewById(R.id.cardViewItem)

        init {
            cardItem.setOnClickListener {
                listeners.setFlightPosition(layoutPosition)
            }
        }
    }
}