package yoyo.app.android.com.yoyoapp.trip.schedule.request

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.json.JSONObject

class RequstViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RequestRepository = RequestRepository(application)
    var isSuccessful: MutableLiveData<Boolean> =  MutableLiveData()


    fun initRequest(tripId: String, jsonObject: JSONObject) {
        repository.getResult(tripId, jsonObject){
            isSuccessful.value = it
        }
    }
}
