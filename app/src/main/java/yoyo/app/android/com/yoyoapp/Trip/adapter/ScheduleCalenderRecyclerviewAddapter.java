package yoyo.app.android.com.yoyoapp.Trip.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import yoyo.app.android.com.yoyoapp.DataModels.ScheduleCalender;
import yoyo.app.android.com.yoyoapp.R;

import java.util.ArrayList;

public class ScheduleCalenderRecyclerviewAddapter extends RecyclerView.Adapter<ScheduleCalenderRecyclerviewAddapter.CalenderViewholder> {

    private ArrayList<ScheduleCalender> calenders;
    private Context context;
    private OnItemClicked onItemClicked;
    private int selectedItem;

    public ScheduleCalenderRecyclerviewAddapter(ArrayList<ScheduleCalender> calenders, Context context, int position , OnItemClicked onItemClicked) {
        this.calenders = calenders;
        this.context = context;
        this.onItemClicked = onItemClicked;
        selectedItem = position;
    }

    @NonNull
    @Override
    public CalenderViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_calender_schedule,parent,false);
        return new CalenderViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CalenderViewholder holder, final int position) {
        holder.bindCalenderItem(calenders.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int previousItem = selectedItem;
                selectedItem = position;
                notifyItemChanged(previousItem);
                notifyItemChanged(position);

                onItemClicked.onClicked(calenders.get(position).getStartDate(),calenders.get(position).getEndDate(),position);
            }
        });

        if (selectedItem == position) {
            holder.bottomImageview.setVisibility(View.VISIBLE);
            holder.monthTextview.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    public int getItemCount() {
        return calenders.size();
    }

    public class CalenderViewholder extends RecyclerView.ViewHolder
    {
        private TextView monthTextview;
        private ImageView bottomImageview;

        public CalenderViewholder(@NonNull View itemView) {
            super(itemView);
            monthTextview = itemView.findViewById(R.id.tv_calenderitem_month);
            bottomImageview = itemView.findViewById(R.id.iv_calenderitem);
        }

        public void bindCalenderItem(ScheduleCalender calender) {
            monthTextview.setText(getMonthName(calender.getMonth()));
            bottomImageview.setVisibility(View.GONE);
            monthTextview.setTextColor(context.getResources().getColor(R.color.black2));
        }
    }
    // callback for when user click on one item of calender list
    public interface OnItemClicked
    {
        void onClicked(long startDate, long endDate, int position);
    }

    private String getMonthName(int month) {

        switch (month) {
            case 0:
                return "January";
            case 1:
                return "February";
            case 2:
                return "March";
            case 3:
                return "April";
            case 4:
                return "May";
            case 5:
                return "June";
            case 6:
                return "July";
            case 7:
                return "August";
            case 8:
                return "September";
            case 9:
                return "October";
            case 10:
                return "November";
            case 11:
                return "December";
            default:
                return "December";
        }
    }
}
