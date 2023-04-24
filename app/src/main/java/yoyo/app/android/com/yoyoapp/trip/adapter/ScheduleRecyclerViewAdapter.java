package yoyo.app.android.com.yoyoapp.trip.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import yoyo.app.android.com.yoyoapp.DataModels.Schedule;
import yoyo.app.android.com.yoyoapp.MainActivity;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.trip.Utils.UserSharedManager;
import yoyo.app.android.com.yoyoapp.trip.booking.firstPage.BookingFirstFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ScheduleRecyclerViewAdapter extends RecyclerView.Adapter<ScheduleRecyclerViewAdapter.ScheduleViewHolder> {
    private static final String TAG = "ScheduleRecyclerviewAdd";
    private ArrayList<Schedule> schedules;
    private String tripTitle;
    private Context context;
    private OnItemSelected onItemSelected;
    private String monthName;
    private Fragment currentFragment;
    private UserSharedManager userSharedManager;
    private int dayOfMonth;

    public ScheduleRecyclerViewAdapter(ArrayList<Schedule> schedules, String tripTitle, Fragment currentFragment, Context context, OnItemSelected onItemSelected) {
        this.currentFragment = currentFragment;
        userSharedManager = new UserSharedManager(context);
        this.schedules = schedules;
        this.tripTitle = tripTitle;
        this.context = context;
        this.onItemSelected = onItemSelected;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_schedule,parent,false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, final int position) {
        holder.bindTravellerCompanionItem(schedules.get(position));
        holder.itemView.setOnClickListener(v -> {
            Schedule schedule = schedules.get(position);
            onItemSelected.onSendResult(schedule);
        });

        holder.bookButton.setOnClickListener(v -> {
//            if (userSharedManager.getToken().isEmpty())
//            {
//                ((MainActivity)context).popUpSignInSignUpActivity();
//            }
//            else
//            {
                BookingFirstFragment bookingFirstFragment = new BookingFirstFragment();
                Bundle bundle = new Bundle();
                bundle.putString("scheduleId",schedules.get(position).getId());
                bundle.putDouble("price", schedules.get(position).getPrice());
                bundle.putInt("minCapacity",schedules.get(position).getMinCapacity());
                bookingFirstFragment.setArguments(bundle);
                ((MainActivity)context).showFragment(currentFragment, bookingFirstFragment, "booking_first",false );
//            }
        });
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder
    {
        private TextView titleTextview;
        private TextView startDateTextview;
        private TextView startDayTextview;
        private TextView endDayTextview;
        private TextView startMonthTextview;
        private TextView endMonthTextview;
        private TextView priceTextview;
        private TextView remainingCapacityTextview;
        private Button   bookButton;

        private ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextview = itemView.findViewById(R.id.tv_schedule_title);
            startDateTextview = itemView.findViewById(R.id.tv_scheduleItem_start_time);
            startDayTextview = itemView.findViewById(R.id.tv_scheduleItem_startDate1);
            startMonthTextview =  itemView.findViewById(R.id.tv_schedule_start_month);
            endMonthTextview =  itemView.findViewById(R.id.tv_schedule_end_month);
            endDayTextview =  itemView.findViewById(R.id.tv_scheduleItem_startDate2);
            priceTextview = itemView.findViewById(R.id.tv_schedule_price1);
            remainingCapacityTextview = itemView.findViewById(R.id.tv_scheduleItem_remainingCapacity1);
            bookButton = itemView.findViewById(R.id.button_schedule_book);
        }

        private void bindTravellerCompanionItem(Schedule schedule) {
            titleTextview.setText(tripTitle);
            startDateTextview.setText(getDayFormat(schedule.getStartTimeStamp()));
            priceTextview.setText(schedule.getPrice()+" $");
            remainingCapacityTextview.setText(String.valueOf(schedule.getRemainingCapacity()));
            setupMonthAndDay(schedule.getStartTimeStamp());
            startDayTextview.setText(String.valueOf(dayOfMonth));
            startMonthTextview.setText(String.valueOf(monthName));
            setupMonthAndDay(schedule.getEndTimeStamp());
            endDayTextview.setText(String.valueOf(dayOfMonth));
            endMonthTextview.setText(String.valueOf(monthName));
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

    private void setupMonthAndDay(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp* 1000);

        Log.d(TAG, "setupMonthAndDay: "+ timeStamp);

        monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        Log.d(TAG, "setupMonthAndDay: aaaa " + monthName + "  " + dayOfMonth + "  " + calendar.getTimeInMillis()+ "    "+ calendar.getTime());
    }
}
