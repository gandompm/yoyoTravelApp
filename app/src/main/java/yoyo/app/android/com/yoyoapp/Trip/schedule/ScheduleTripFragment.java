package yoyo.app.android.com.yoyoapp.Trip.schedule;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import yoyo.app.android.com.yoyoapp.DataModels.Schedule;
import yoyo.app.android.com.yoyoapp.DataModels.ScheduleCalender;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.adapter.ScheduleCalenderRecyclerviewAddapter;
import yoyo.app.android.com.yoyoapp.Trip.adapter.ScheduleRecyclerviewAddapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ScheduleTripFragment extends Fragment {

    private static final String TAG = "ScheduleTripFragment";
    private ArrayList<ScheduleCalender> myCalenders;
    private RecyclerView calenderRecyclerview, scheduleResultRecyclerview;
    private long startDate, endDate;
    private ArrayList<Schedule> scheduleArrayList;
    private ScheduleViewModel scheduleViewModel;
    private ScheduleRecyclerviewAddapter addapter;
    private int position = 0;
    private String tripId;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_schedule_trip, container, false);

        tripId = getArguments().getString("tripId");
        scheduleArrayList = new ArrayList<>();
        scheduleResultRecyclerview = view.findViewById(R.id.rv_schedule_result);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        scheduleResultRecyclerview.setLayoutManager(linearLayoutManager);
        scheduleArrayList = new ArrayList<>();

//      getSchedules(startDate, endDate);
        setupCalenderRecyclerview();


        return view;
    }

    private void getSchedules(String tripId, long startDate, long endDate) {
        scheduleViewModel = ViewModelProviders.of(getActivity()).get(ScheduleViewModel.class);
        scheduleViewModel.initSchedule(tripId,startDate,endDate);
        scheduleViewModel.getScheduleList().observe(getActivity(), new Observer<ArrayList<Schedule>>() {
            @Override
            public void onChanged(ArrayList<Schedule> schedules) {
                if (schedules!=null)
                {
                    scheduleArrayList.clear();
                    scheduleArrayList.addAll(schedules);

                    if (addapter == null) {
                        addapter = new ScheduleRecyclerviewAddapter(scheduleArrayList, getContext(), new ScheduleRecyclerviewAddapter.OnItemSelected() {
                            @Override
                            public void onSendResult(Schedule schedule) {

                            }
                        });
                        scheduleResultRecyclerview.setAdapter(addapter);
                    }
                    else
                        addapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void setupCalenderRecyclerview() {

        calenderRecyclerview = view.findViewById(R.id.rv_schedule_calender);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        // get a list of dates
        myCalenders = getCalenderData();

        ScheduleCalenderRecyclerviewAddapter addapter =
                new ScheduleCalenderRecyclerviewAddapter(myCalenders, getContext(), position, new ScheduleCalenderRecyclerviewAddapter.OnItemClicked() {
            @Override
            public void onClicked(long startDate, long endDate, int newPosition) {
                getSchedules(tripId ,startDate,endDate);
                if (newPosition > position)
                    linearLayoutManager.scrollToPosition(newPosition + 2);
                else
                    linearLayoutManager.scrollToPosition(newPosition - 2);
                position = newPosition;
            }
        });

        calenderRecyclerview.setLayoutManager(linearLayoutManager);
        calenderRecyclerview.setAdapter(addapter);
    }


    public ArrayList<ScheduleCalender> getCalenderData()
    {
        ArrayList<ScheduleCalender> myCalenders = new ArrayList<>();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_MONTH, 1);
//        today.set(Calendar.HOUR_OF_DAY, 0);
//        today.set(Calendar.MINUTE, 0);
//        today.set(Calendar.SECOND, 0);



        for (int i = 0; i <= 12; i++) {
            myCalenders.add(setupCalender(today));
            today.add(Calendar.MONTH, +1);
            if (i == 0) {
                getSchedules(tripId,startDate,endDate);
            }
        }


        return myCalenders;
    }

    private ScheduleCalender setupCalender(Calendar theDay) {
        int month;
        ScheduleCalender myCalender = new ScheduleCalender();
        month = theDay.get(Calendar.MONTH);
        myCalender.setMonth(month);
        startDate = theDay.getTimeInMillis()/1000;
        myCalender.setDate(startDate);

        // next 30 days
        Calendar next30Days = Calendar.getInstance();
        next30Days.setTime(theDay.getTime());
        next30Days.set(Calendar.DAY_OF_MONTH,next30Days.getActualMaximum(Calendar.DAY_OF_MONTH));
        endDate = next30Days.getTimeInMillis()/1000;
        myCalender.setEndDate(endDate);

        return myCalender;
    }

}
