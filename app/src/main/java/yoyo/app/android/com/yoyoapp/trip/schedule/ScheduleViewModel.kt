package yoyo.app.android.com.yoyoapp.trip.schedule

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import yoyo.app.android.com.yoyoapp.DataModels.Schedule

import java.util.ArrayList

class ScheduleViewModel(application: Application) : AndroidViewModel(application) {

    private var scheduleMutableLiveData: MutableLiveData<ArrayList<Schedule>>? = null
    private val scheduleRepository: ScheduleRepository

    val scheduleList: LiveData<ArrayList<Schedule>>?
        get() = scheduleMutableLiveData

    init {
        scheduleRepository = ScheduleRepository.Companion.getInstance(getApplication<T>())
    }

    fun initSchedule(tripId: String, startDate: Long, endDate: Long) {
        scheduleMutableLiveData = MutableLiveData()
        scheduleMutableLiveData = scheduleRepository.getSchedule(tripId, startDate, endDate)
    }


}
