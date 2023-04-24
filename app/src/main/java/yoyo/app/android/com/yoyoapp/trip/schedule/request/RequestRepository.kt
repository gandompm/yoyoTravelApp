package yoyo.app.android.com.yoyoapp.trip.schedule.request

import android.content.Context
import androidx.lifecycle.MutableLiveData
import org.json.JSONObject
import yoyo.app.android.com.yoyoapp.trip.ApiService

class RequestRepository(private val context: Context) {
    private val apiService: ApiService = ApiService(context)

    fun getResult(tripId: String, jsonObject: JSONObject, f: (Boolean) -> Unit) {
        apiService.sendTripRequest(tripId, jsonObject) { f(it) }
    }

    private fun setResult(tripId: String, jsonObject: JSONObject) {

    }

}
