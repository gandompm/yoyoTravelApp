package yoyo.app.android.com.yoyoapp.trip.booking.firstPage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import yoyo.app.android.com.yoyoapp.DataModels.Traveller
import yoyo.app.android.com.yoyoapp.trip.api.BookScheduleRequest

class BookingViewModel(application: Application) : AndroidViewModel(application) {
    private val bookingRepository = BookingRepository(getApplication())
    var paymentUrlMutableLiveData: MutableLiveData<String> = MutableLiveData()
    var travellerCompanionsMutableList: MutableLiveData<List<Traveller>> = MutableLiveData()

    fun initBookingRequest(scheduleId: String, request: BookScheduleRequest) {
        bookingRepository.bookSchedule(scheduleId, request)
        {
            paymentUrlMutableLiveData.value = it.paymentUrl
        }
    }

    fun initGetTravellers() {
        bookingRepository.getCompanionTraveller {
            travellerCompanionsMutableList.value = it
        }
    }


}
