package com.example.flightinfo

import android.util.Log
import com.example.flightinfo.api.ApiFactory
import com.example.flightinfo.api.ApiService
import com.example.flightinfo.api.pojo.Flights
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class Repository {
    private val apiService: ApiService = ApiFactory.apiService
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getData(): Single<List<Flights>> {
        return Single.create { _it: SingleEmitter<List<Flights>> ->
            compositeDisposable.add(
                apiService.getFlights().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        _it.onSuccess(it)
                    },
                        { Log.d("ERROR", "Repository.getData: ${it.message}") })
            )
        }
    }

    fun dispose() {
        compositeDisposable.dispose()
    }
}