package com.example.flightinfo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.flightinfo.R
import com.example.flightinfo.api.pojo.Flights
import com.example.flightinfo.support.AirportsName
import com.example.flightinfo.viewmodel.FlightsViewModel

class SelectedTypeFlightFragment : Fragment() {
    private lateinit var viewModel: FlightsViewModel
    private var positionSelectedFlight = -1
    private var positionSelectedFlightClass = -1
    private val airportsName: AirportsName = AirportsName()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(FlightsViewModel::class.java)
        setArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.info_selected_data_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val liveData = viewModel.getSelectedFlight(positionSelectedFlight)
        liveData.observe(viewLifecycleOwner, Observer {
            setData(view, it)
        })
    }

    private fun setArguments() {
        positionSelectedFlightClass =
            arguments?.getInt(SelectedFlightFragment.TAG_POSITION_SELECTED_FLIGHT_CLASS)!!
        positionSelectedFlight =
            arguments?.getInt(SelectedFlightFragment.TAG_POSITION_SELECTED_FLIGHT)!!
    }

    private fun setData(view: View, it: Flights) {
        val amountFlights = it.listTrips.size
        val tvInfoDataSelectedFlightClass: TextView =
            view.findViewById(R.id.tvInfoDataSelectedFlightClass)!!
        val tvInfoDataSelectedFlightCost: TextView =
            view.findViewById(R.id.tvInfoDataSelectedFlightCost)!!
        val tvInfoDataSelectedFlightAmount: TextView =
            view.findViewById(R.id.tvInfoDataSelectedFlightAmount)!!
        val tvInfoDataSelectedFlightFrom: TextView =
            view.findViewById(R.id.tvInfoDataSelectedFlightFrom)!!
        val tvInfoDataSelectedFlightTo: TextView =
            view.findViewById(R.id.tvInfoDataSelectedFlightTo)!!
        val tvInfoDataSelectedFlightFrom2: TextView =
            view.findViewById(R.id.tvInfoDataSelectedFlightFrom2)!!
        val tvInfoDataSelectedFlightTo2: TextView =
            view.findViewById(R.id.tvInfoDataSelectedFlightSecond)!!
        val transferLayout: ConstraintLayout = view.findViewById(R.id.transferConstraintLayout)!!

        tvInfoDataSelectedFlightClass.text = it.listPrices[positionSelectedFlightClass].type
        tvInfoDataSelectedFlightCost.text =
            it.listPrices[positionSelectedFlightClass].amount.toString()
        tvInfoDataSelectedFlightAmount.text = amountFlights.toString()
        tvInfoDataSelectedFlightFrom.text = airportsName.getNameAirports(it.listTrips[0].from)
        tvInfoDataSelectedFlightTo.text = airportsName.getNameAirports(it.listTrips[0].to)
        if (amountFlights > 1) {
            tvInfoDataSelectedFlightFrom2.text = airportsName.getNameAirports(it.listTrips[1].from)
            tvInfoDataSelectedFlightTo2.text = airportsName.getNameAirports(it.listTrips[1].to)
        } else {
            transferLayout.visibility = View.GONE
        }
    }
}