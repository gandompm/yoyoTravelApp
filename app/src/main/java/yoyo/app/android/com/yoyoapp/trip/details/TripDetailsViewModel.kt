package yoyo.app.android.com.yoyoapp.trip.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import yoyo.app.android.com.yoyoapp.DataModels.Trip

class TripDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val tripMutableLiveData: MutableLiveData<Trip>? = null
    private val tripDetailsRepository: TripDetailsRepository

    init {
        tripDetailsRepository = TripDetailsRepository(getApplication())
    }


}
