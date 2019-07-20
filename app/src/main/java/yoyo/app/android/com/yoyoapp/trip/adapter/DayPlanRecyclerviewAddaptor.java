package yoyo.app.android.com.yoyoapp.trip.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;
import yoyo.app.android.com.yoyoapp.R;
import java.util.ArrayList;


public class DayPlanRecyclerviewAddaptor extends RecyclerView.Adapter<DayPlanRecyclerviewAddaptor.DayPlanViewPager> {

    private Context context;
    private ArrayList<String> dayPlans;
    private int selectedItem = 0;
    private Consumer<String> dayPlan;

    public DayPlanRecyclerviewAddaptor(Context context, ArrayList<String> dayPlans, androidx.core.util.Consumer<String> dayPlan)
    {
        this.context = context;
        this.dayPlans = dayPlans;
        this.dayPlan = dayPlan;
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
                dayPlan.accept(dayPlans.get(position));
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
        return dayPlans.size();
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
            dayPlanButton.setText("Day "+ ++position);
        }
    }
}
