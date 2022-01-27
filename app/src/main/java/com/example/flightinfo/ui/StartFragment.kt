package com.example.flightinfo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.flightinfo.R
import com.example.flightinfo.viewmodel.FlightsViewModel

class StartFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity()).get(FlightsViewModel::class.java)
        viewModel.getDataFromApi()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.start_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonSearchFlights: Button = view.findViewById(R.id.buttonSearchFlights)
        buttonSearchFlights.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.containerFragment, ListFlightsFragment())
                .commit()
        }
    }
}