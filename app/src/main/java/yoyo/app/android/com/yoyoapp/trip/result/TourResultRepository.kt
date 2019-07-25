package yoyo.app.android.com.yoyoapp.trip.result

import android.content.Context
import androidx.core.util.Consumer
import yoyo.app.android.com.yoyoapp.trip.ApiService
import yoyo.app.android.com.yoyoapp.DataModels.Trip
import yoyo.app.android.com.yoyoapp.DataModels.TripQuery

class TourResultRepository(val context: Context) {

    private val apiService: ApiService = ApiService(context)

    fun getTripList(page: Int, tripQuery: TripQuery, consumer: Consumer<List<Trip>>){
        apiService.getTripListRequest(page, tripQuery) { tripList -> consumer.accept(tripList) }
    }
}
