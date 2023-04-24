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
//        apiService.getScheduleRequest(tripId, startDate, endDate) { schedules ->
//            consumer.accept(schedules)
//        }
        consumer.accept(getFakeScheduleData())
    }

    private fun getFakeScheduleData(): ArrayList<Schedule> {
        val scheduleArrayList = ArrayList<Schedule>()
            for (i in 0 until 3) {
                val schedule = Schedule()

                schedule.id = "dfadsfd"
                schedule.price = 99.00
                schedule.minCapacity = 1
                schedule.maxCapacity = 25
                schedule.remainingCapacity = 9
                schedule.startTimeStamp = 1565222400
                schedule.endTimeStamp = 1566691200

                scheduleArrayList.add(schedule)
            }
        return scheduleArrayList
    }


}
