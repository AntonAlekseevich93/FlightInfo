package com.example.flightinfo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flightinfo.R
import com.example.flightinfo.api.pojo.Flights
import com.example.flightinfo.ui.listeners.ISelectedFlight
import com.example.flightinfo.viewmodel.FlightsViewModel

class ListFlightsFragment : Fragment(), ISelectedFlight {
    private var listOfFlights: List<Flights> = ArrayList()
    private lateinit var viewModel: FlightsViewModel
    private lateinit var flightsAdapter: FlightsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(FlightsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_flights_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewListFlights)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        flightsAdapter = FlightsAdapter(listOfFlights, view.context, this)
        recyclerView.adapter = flightsAdapter
        val liveDataListFlights: LiveData<List<Flights>> = viewModel.getData()
        liveDataListFlights.observe(viewLifecycleOwner, { flightsAdapter.setData(it) })
    }

    override fun setFlightPosition(position: Int) {
        getFragmentWithArguments(position)
            .show(parentFragmentManager, SelectedFlightFragment.TAG_DIALOG_FRAGMENT_FLIGHT_INFO)
    }

    private fun getFragmentWithArguments(position: Int): SelectedFlightFragment {
        val bundle = Bundle()
        bundle.putInt(SelectedFlightFragment.TAG_POSITION_SELECTED_FLIGHT, position)
        val selectedFlightFragment = SelectedFlightFragment()
        selectedFlightFragment.arguments = bundle
        return selectedFlightFragment
    }

}