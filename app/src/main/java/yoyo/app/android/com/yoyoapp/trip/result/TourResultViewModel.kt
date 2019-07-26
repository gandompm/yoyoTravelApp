package yoyo.app.android.com.yoyoapp.trip.result

import android.app.Application
import androidx.core.util.Consumer
import androidx.lifecycle.*
import yoyo.app.android.com.yoyoapp.DataModels.Trip
import yoyo.app.android.com.yoyoapp.DataModels.TripQuery

class TourResultViewModel(application: Application) : AndroidViewModel(application) {

    //    private  var tourListMutableLiveData = MutableLiveData<List<Trip>>()
    private var tourResultRepository = TourResultRepository(getApplication())
    private lateinit var tourListMutableLiveData: MutableLiveData<List<Trip>>

    fun initTripList(page: Int, tripQuery: TripQuery) {
        tourListMutableLiveData = MutableLiveData()
        tourResultRepository.getTripList(page, tripQuery, Consumer {
            tourListMutableLiveData.value = it
        })
    }

    fun getTripList(): LiveData<List<Trip>>? = tourListMutableLiveData
}
