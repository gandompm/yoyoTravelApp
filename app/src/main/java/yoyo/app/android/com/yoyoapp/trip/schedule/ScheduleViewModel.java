package yoyo.app.android.com.yoyoapp.trip.schedule;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import yoyo.app.android.com.yoyoapp.DataModels.Schedule;

import java.util.ArrayList;

public class ScheduleViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Schedule>> scheduleMutableLiveData;
    private ScheduleRepository scheduleRepository;

    public ScheduleViewModel(@NonNull Application application) {
        super(application);
        scheduleRepository = ScheduleRepository.getInstance(getApplication());
    }

    public void initSchedule(String tripId, long startDate, long endDate)
    {
        scheduleMutableLiveData = new MutableLiveData<>();
        scheduleMutableLiveData = scheduleRepository.getSchedule(tripId,startDate,endDate);
    }

    public LiveData<ArrayList<Schedule>> getScheduleList()
    {
        return scheduleMutableLiveData;
    }


}
