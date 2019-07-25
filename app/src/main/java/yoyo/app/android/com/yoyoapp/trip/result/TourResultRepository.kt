package yoyo.app.android.com.yoyoapp.trip.result

import android.content.Context
import androidx.lifecycle.MutableLiveData
import yoyo.app.android.com.yoyoapp.trip.ApiService
import yoyo.app.android.com.yoyoapp.DataModels.Trip
import yoyo.app.android.com.yoyoapp.DataModels.TripQuery

class TourResultRepository(private val context: Context) {
    private val apiService: ApiService
    private var tripListMutableLiveData: MutableLiveData<List<Trip>>? = null
    private var tripCountMutableLiveData: MutableLiveData<Int>? = null

    init {
        apiService = ApiService(context)
    }

    fun getTripList(page: Int, tripQuery: TripQuery): MutableLiveData<List<Trip>> {
        tripListMutableLiveData = MutableLiveData()
        tripCountMutableLiveData = MutableLiveData()
        setTripList(page, tripQuery)
        return tripListMutableLiveData
    }

    private fun setTripList(page: Int, tripQuery: TripQuery) {
        apiService.getTripListRequest(page, tripQuery) { tripList -> tripListMutableLiveData!!.postValue(tripList) }
    }

    companion object {
        private var instance: TourResultRepository? = null

        fun getInstance(context: Context): TourResultRepository {
            if (instance == null) {
                instance = TourResultRepository(context)
            }
            return instance
        }
    }


}
