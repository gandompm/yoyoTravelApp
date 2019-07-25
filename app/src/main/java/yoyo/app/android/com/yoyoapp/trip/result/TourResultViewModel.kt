package yoyo.app.android.com.yoyoapp.trip.result

import android.app.Application
import androidx.lifecycle.*
import yoyo.app.android.com.yoyoapp.DataModels.Trip
import yoyo.app.android.com.yoyoapp.DataModels.TripQuery

class TourResultViewModel(application: Application) : AndroidViewModel(application) {

    private var tripArraylist: MutableLiveData<List<Trip>>? = null
    private var tripCountMutableLiveData: MutableLiveData<Int>? = null
    private val tourResultRepository = TourResultRepository(getApplication())

    val tripList: LiveData<List<Trip>>?
        get() = tripArraylist

    fun initTripList(page: Int, tripQuery: TripQuery) {
        tripArraylist = MutableLiveData()
        tripCountMutableLiveData = MutableLiveData()
        tripArraylist = tourResultRepository.getTripList(page, tripQuery)
    }


}
