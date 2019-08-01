package yoyo.app.android.com.yoyoapp.trip.schedule

import android.content.Context
import androidx.core.util.Consumer
import androidx.lifecycle.MutableLiveData
import yoyo.app.android.com.yoyoapp.DataModels.Schedule
import yoyo.app.android.com.yoyoapp.trip.ApiService

import java.util.ArrayList

class ScheduleTourRepository(val context: Context) {
    private val apiService: ApiService = ApiService(context)

    fun getSchedule(tripId: String, startDate: Long, endDate: Long, consumer: Consumer<ArrayList<Schedule>>) {
        apiService.getScheduleRequest(tripId, startDate, endDate) { schedules ->
            consumer.accept(schedules)
        }
    }



}
