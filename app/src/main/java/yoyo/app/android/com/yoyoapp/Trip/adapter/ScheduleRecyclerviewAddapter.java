package yoyo.app.android.com.yoyoapp.Trip.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import yoyo.app.android.com.yoyoapp.DataModels.Schedule;
import yoyo.app.android.com.yoyoapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ScheduleRecyclerviewAddapter extends RecyclerView.Adapter<ScheduleRecyclerviewAddapter.ScheduleViewholder> {
    private ArrayList<Schedule> schedules;
    private Context context;
    private OnItemSelected onItemSelected;

    public ScheduleRecyclerviewAddapter(ArrayList<Schedule> schedules, Context context, OnItemSelected onItemSelected) {
        this.schedules = schedules;
        this.context = context;
        this.onItemSelected = onItemSelected;
    }

    @NonNull
    @Override
    public ScheduleViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_schedule,parent,false);
        return new ScheduleViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewholder holder, final int position) {
        holder.bindTravellerCompanionItem(schedules.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Schedule schedule = schedules.get(position);

                onItemSelected.onSendResult(schedule);
            }
        });
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class ScheduleViewholder extends RecyclerView.ViewHolder
    {
        private TextView startDateTextview;
        private TextView priceTextview;
        private TextView remainingCapacityTextview;


        public ScheduleViewholder(@NonNull View itemView) {
            super(itemView);
            startDateTextview = itemView.findViewById(R.id.tv_scheduleItem_startDate1);
            priceTextview = itemView.findViewById(R.id.tv_schedule_price1);
            remainingCapacityTextview = itemView.findViewById(R.id.tv_scheduleItem_remainingCapacity1);
        }

        public void bindTravellerCompanionItem(Schedule schedule) {
            startDateTextview.setText(getDayFormat(schedule.getStartTimeStamp()));
            priceTextview.setText(schedule.getPrice()+"$");
            remainingCapacityTextview.setText(String.valueOf(schedule.getRemainingCapacity()));
        }
    }

    // callback for when user click on one item of traveller list
    public interface OnItemSelected
    {
        void onSendResult(Schedule schedule);
    }

    private String getDayFormat(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        SimpleDateFormat dayFormat = new SimpleDateFormat("E", Locale.getDefault());
        String dayOfWeekNameFrom = dayFormat.format(calendar.getTime());
        String monthNameFrom = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());

        return "" + dayOfWeekNameFrom + ", "
                + calendar.get(Calendar.DAY_OF_MONTH)
                + " " + monthNameFrom;
    }
}
