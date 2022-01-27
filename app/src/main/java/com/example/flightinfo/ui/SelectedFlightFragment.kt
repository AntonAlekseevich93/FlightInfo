package com.example.flightinfo.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flightinfo.R
import com.example.flightinfo.api.pojo.Flights
import com.example.flightinfo.api.pojo.Price
import com.example.flightinfo.ui.listeners.ISelectedFlightClass
import com.example.flightinfo.viewmodel.FlightsViewModel

class SelectedFlightFragment : DialogFragment(), ISelectedFlightClass {
    private lateinit var viewModel: FlightsViewModel
    private lateinit var liveData: LiveData<Flights>
    private var positionSelectedFlight = -1
    private var positionSelectedFlightClass = -1
    private lateinit var selectedFlightClassAdapter: SelectedFlightClassAdapter
    private var listPrice: List<Price> = ArrayList()
    private var isSelected: Boolean = false
    private lateinit var alertDialog: AlertDialog

    companion object {
        const val TAG_DIALOG_FRAGMENT_FLIGHT_INFO = "createNewDialogInfo"
        const val TAG_POSITION_SELECTED_FLIGHT = "tag_position_selected_flight"
        const val TAG_POSITION_SELECTED_FLIGHT_CLASS = "tag_selected_position_flight_class"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        positionSelectedFlight =
            arguments?.getInt(TAG_POSITION_SELECTED_FLIGHT)!!
        viewModel = ViewModelProvider(requireActivity()).get(FlightsViewModel::class.java)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = requireActivity()
            .layoutInflater.inflate(R.layout.selected_flight_layout, null)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewSelected)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        selectedFlightClassAdapter = SelectedFlightClassAdapter(listPrice, view.context, this)
        recyclerView.adapter = selectedFlightClassAdapter
        liveData = viewModel.getSelectedFlight(positionSelectedFlight)
        liveData.observe(this, Observer {
            selectedFlightClassAdapter.setData(it.listPrices)
        })

        alertDialog = AlertDialog.Builder(requireContext())
            .setView(view)
            .setPositiveButton(getString(R.string.apply), null)
            .create()

        alertDialog.setOnShowListener {
            val b: Button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            b.setOnClickListener {
                if (!isSelected) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.it_is_necessary_to_choose_a_flight_class),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.containerFragment, getFragmentWithArguments())
                        .addToBackStack(null)
                        .commit()
                    dismiss()
                }
            }
        }
        return alertDialog
    }

    override fun setPositionSelectedFlightClass(position: Int) {
        if (!isSelected) {
            isSelected = true
        }
        positionSelectedFlightClass = position
    }

    private fun getFragmentWithArguments(): SelectedTypeFlightFragment {
        val bundle = Bundle()
        bundle.putInt(TAG_POSITION_SELECTED_FLIGHT, positionSelectedFlight)
        bundle.putInt(TAG_POSITION_SELECTED_FLIGHT_CLASS, positionSelectedFlightClass)
        val fragment = SelectedTypeFlightFragment()
        fragment.arguments = bundle
        return fragment
    }
}