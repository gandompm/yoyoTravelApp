package yoyo.app.android.com.yoyoapp.trip.schedule;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import yoyo.app.android.com.yoyoapp.DataModels.Schedule;
import yoyo.app.android.com.yoyoapp.trip.ApiService;

import java.util.ArrayList;

public class ScheduleRepository {

    private Context context;
    private ApiService apiService;
    private static ScheduleRepository instance;
    private MutableLiveData<ArrayList<Schedule>> scheduleMutabaleList;

    public ScheduleRepository(Context context) {
        this.context = context;
        apiService = new ApiService(context);
    }

    public static ScheduleRepository getInstance(Context context) {
        if (instance == null)
        {
            instance = new ScheduleRepository(context);
        }
        return instance;
    }

    public MutableLiveData<ArrayList<Schedule>> getSchedule(String tripId, long startDate, long endDate)
    {
        scheduleMutabaleList = new MutableLiveData<>();
        setScheduleList(tripId,startDate ,endDate);
        return scheduleMutabaleList;
    }

    private void setScheduleList(String tripId, long stardDate, long endDate) {
        apiService.getScheduleRequest(tripId,stardDate,endDate,schedules -> {
            scheduleMutabaleList.postValue(schedules);
        });
    }


}
