package yoyo.app.android.com.yoyoapp.trip.schedule

import android.app.Application
import androidx.core.util.Consumer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import yoyo.app.android.com.yoyoapp.DataModels.Schedule
import yoyo.app.android.com.yoyoapp.DataModels.ScheduleCalender
import java.util.*

class ScheduleTourViewModel(application: Application) : AndroidViewModel(application) {

    var scheduleMutableLiveData = MutableLiveData<ArrayList<Schedule>>()
    private val scheduleRepository: ScheduleTourRepository = ScheduleTourRepository(getApplication())
    private var startDate: Long = 0
    private var endDate:Long = 0


    fun initSchedule(tripId: String, startDate: Long, endDate: Long) {
        scheduleRepository.getSchedule(tripId, startDate, endDate, Consumer {
            scheduleMutableLiveData.value = it
        })
    }

    private fun setupCalender(theDay: Calendar): ScheduleCalender {
        val month: Int = theDay.get(Calendar.MONTH)
        val myCalender = ScheduleCalender()
        myCalender.month = month
        startDate = theDay.timeInMillis / 1000
        myCalender.setDate(startDate)

        // next 30 days
        val next30Days = Calendar.getInstance()
        next30Days.time = theDay.time
        next30Days.set(Calendar.DAY_OF_MONTH, next30Days.getActualMaximum(Calendar.DAY_OF_MONTH))
        endDate = next30Days.timeInMillis / 1000
        myCalender.endDate = endDate

        return myCalender
    }

    fun getCalenderData(f: (Long, Long) -> Unit): ArrayList<ScheduleCalender> {
        val myCalenders = ArrayList<ScheduleCalender>()
        val today = Calendar.getInstance()
        today.set(Calendar.DAY_OF_MONTH, 1)
        // today.set(Calendar.HOUR_OF_DAY, 0);
        // today.set(Calendar.MINUTE, 0);
        // today.set(Calendar.SECOND, 0);

        for (i in 0..12) {
            myCalenders.add(setupCalender(today))
            today.add(Calendar.MONTH, +1)
            if (i == 0) {
                f(startDate,endDate)
            }
        }

        return myCalenders
    }

}
