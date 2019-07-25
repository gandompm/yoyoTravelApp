package yoyo.app.android.com.yoyoapp.trip.details

import android.content.Context
import androidx.lifecycle.MutableLiveData
import yoyo.app.android.com.yoyoapp.trip.ApiService
import yoyo.app.android.com.yoyoapp.DataModels.Trip

class TripDetailsRepository(private val context: Context) {

    private val tripMutableLiveData: MutableLiveData<Trip>? = null
    private val apiService: ApiService

    init {
        apiService = ApiService(context)
    }

    companion object {
        private val instance: TripDetailsRepository? = null

        fun getInstance(context: Context): TripDetailsRepository {
            return instance ?: TripDetailsRepository(context)
        }
    }


}
