package yoyo.app.android.com.yoyoapp.trip.schedule

import android.content.Context
import androidx.lifecycle.MutableLiveData
import yoyo.app.android.com.yoyoapp.DataModels.Schedule
import yoyo.app.android.com.yoyoapp.trip.ApiService

import java.util.ArrayList

class ScheduleRepository(private val context: Context) {
    private val apiService: ApiService
    private var scheduleMutabaleList: MutableLiveData<ArrayList<Schedule>>? = null

    init {
        apiService = ApiService(context)
    }

    fun getSchedule(tripId: String, startDate: Long, endDate: Long): MutableLiveData<ArrayList<Schedule>> {
        scheduleMutabaleList = MutableLiveData()
        setScheduleList(tripId, startDate, endDate)
        return scheduleMutabaleList
    }

    private fun setScheduleList(tripId: String, stardDate: Long, endDate: Long) {
        apiService.getScheduleRequest(tripId, stardDate, endDate) { schedules ->
            scheduleMutabaleList!!.postValue(
                schedules
            )
        }
    }

    companion object {
        private var instance: ScheduleRepository? = null

        fun getInstance(context: Context): ScheduleRepository {
            if (instance == null) {
                instance = ScheduleRepository(context)
            }
            return instance
        }
    }


}
