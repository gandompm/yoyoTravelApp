package yoyo.app.android.com.yoyoapp.Trip.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import yoyo.app.android.com.yoyoapp.R;


public class DayPlanRecyclerviewAddaptor extends RecyclerView.Adapter<DayPlanRecyclerviewAddaptor.DayPlanViewPager> {

    private Context context;
    private int days;
    private int selectedItem = 0;
    private OnDayClickedListner onDayClickedListner;

    public DayPlanRecyclerviewAddaptor(Context context, int days, OnDayClickedListner onDayClickedListner)
    {
        this.context = context;
        this.days = days;
        this.onDayClickedListner = onDayClickedListner;
    }

    @NonNull
    @Override
    public DayPlanViewPager onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_day_plan,parent,false);
        return new DayPlanViewPager(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DayPlanViewPager holder, final int position) {

        holder.bindItem(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDayClickedListner.onClicked(position);
                selectedItem = position;
                notifyDataSetChanged();
            }
        });
        if (selectedItem == position) {
            holder.dayPlanButton.setBackgroundColor(context.getResources().getColor(R.color.grey));
        }
        else {
            holder.dayPlanButton.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return days;
    }

    public class DayPlanViewPager extends RecyclerView.ViewHolder
    {
        private Button dayPlanButton;
        public DayPlanViewPager(View itemView) {
            super(itemView);

            dayPlanButton = itemView.findViewById(R.id.button_dayplan);
        }

        public void bindItem(int position)
        {
            dayPlanButton.setText("Day "+ position);
        }
    }

    public interface OnDayClickedListner
    {
        void onClicked(int position);
    }
}
