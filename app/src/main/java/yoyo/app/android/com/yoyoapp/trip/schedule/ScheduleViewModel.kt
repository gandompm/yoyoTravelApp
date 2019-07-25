package yoyo.app.android.com.yoyoapp.trip.schedule

import android.app.Application
import androidx.core.util.Consumer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import yoyo.app.android.com.yoyoapp.DataModels.Schedule

import java.util.ArrayList

class ScheduleViewModel(application: Application) : AndroidViewModel(application) {

    private var scheduleMutableLiveData: MutableLiveData<ArrayList<Schedule>>? = null
    private val scheduleRepository: ScheduleRepository = ScheduleRepository(getApplication())

    val scheduleList: LiveData<ArrayList<Schedule>>?
        get() = scheduleMutableLiveData

    fun initSchedule(tripId: String, startDate: Long, endDate: Long) {
        scheduleMutableLiveData = MutableLiveData()
        scheduleRepository.getSchedule(tripId, startDate, endDate, Consumer {
            scheduleMutableLiveData?.value = it
        })
    }


}
