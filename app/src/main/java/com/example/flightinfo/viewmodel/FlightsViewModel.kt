package com.example.flightinfo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flightinfo.Repository
import com.example.flightinfo.api.pojo.Flights
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FlightsViewModel() : ViewModel() {
    private var liveData: MutableLiveData<List<Flights>>? = null
    private var liveDataSelectedFlight: MutableLiveData<Flights>? = null
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val repository: Repository = Repository()

    fun getData(): LiveData<List<Flights>> {
        if (liveData == null) {
            getDataFromApi()
        }
        return liveData as MutableLiveData<List<Flights>>
    }

    fun getDataFromApi() {
        if (liveData == null) liveData = MutableLiveData()
        compositeDisposable.add(
            repository.getData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    liveData!!.value = it
                }, { Log.d("ERROR", "ViewModel.getData: ${it.message}") })
        )
    }

    fun getSelectedFlight(position: Int): LiveData<Flights> {
        if (liveDataSelectedFlight == null) liveDataSelectedFlight = MutableLiveData()
        liveDataSelectedFlight!!.value = liveData?.value?.get(position)
        return liveDataSelectedFlight as MutableLiveData<Flights>
    }

    override fun onCleared() {
        repository.dispose()
        compositeDisposable.dispose()
        super.onCleared()
    }


}