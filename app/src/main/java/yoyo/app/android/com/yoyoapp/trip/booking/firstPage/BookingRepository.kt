package yoyo.app.android.com.yoyoapp.trip.booking.firstPage

import android.content.Context
import androidx.lifecycle.MutableLiveData
import org.json.JSONObject
import yoyo.app.android.com.yoyoapp.DataModels.TourRequest
import yoyo.app.android.com.yoyoapp.DataModels.Traveller
import yoyo.app.android.com.yoyoapp.trip.ApiService
import yoyo.app.android.com.yoyoapp.trip.ApiService2
import yoyo.app.android.com.yoyoapp.trip.api.BookScheduleRequest
import yoyo.app.android.com.yoyoapp.trip.api.BookScheduleResponse

class BookingRepository (context: Context) {
    private val apiService: ApiService = ApiService(context)
    private val apiService2: ApiService2 = ApiService2(context)

    fun getCompanionTraveller(f: (ArrayList<Traveller>) -> Unit) {
        apiService.getTravellersCompanionsRequest { f(it)}
    }

    fun bookSchedule(scheduleId: String,request: BookScheduleRequest, f: (BookScheduleResponse) -> Unit){
        apiService2.sendBookingRequest(scheduleId,request)
        {
            f(it)
        }
    }
}
