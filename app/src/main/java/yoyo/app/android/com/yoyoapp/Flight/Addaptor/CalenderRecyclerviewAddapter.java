package yoyo.app.android.com.yoyoapp.Flight.Addaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.MyCalender;
import yoyo.app.android.com.yoyoapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CalenderRecyclerviewAddapter extends RecyclerView.Adapter<CalenderRecyclerviewAddapter.CalenderViewholder> {
    private static final String TAG = "CalenderRecyclerviewAdd";
    private ArrayList<MyCalender> calenders;
    private Context context;
    private OnItemClicked onItemClicked;
    private int selectedItem;
    private boolean isPerisanCalender;
    private DecimalFormat decimalFormat;

    public CalenderRecyclerviewAddapter(ArrayList<MyCalender> calenders, Context context, boolean isPerisanCalender, int position , OnItemClicked onItemClicked) {
        this.calenders = calenders;
        this.context = context;
        this.onItemClicked = onItemClicked;
        selectedItem = position;
        Log.d(TAG, "CalenderRecyclerviewAddapter: tttttttttt   " + position);
        this.isPerisanCalender = isPerisanCalender;
        decimalFormat = new DecimalFormat("#,###,###");
    }

    @NonNull
    @Override
    public CalenderViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_calender,parent,false);
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

                onItemClicked.onClicked(calenders.get(position).getStandardDate(),position);
            }
        });

        if (selectedItem == position) {
            holder.bottomImageview.setVisibility(View.VISIBLE);
            holder.dayOfWeekTextview.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            holder.dayTextview.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            if(!calenders.get(position).isMin())
              holder.priceTextview.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    public int getItemCount() {
        return calenders.size();
    }

    public class CalenderViewholder extends RecyclerView.ViewHolder
    {
        private TextView dayTextview;
        private TextView dayOfWeekTextview;
        private TextView priceTextview;
        private ImageView bottomImageview;

        public CalenderViewholder(@NonNull View itemView) {
            super(itemView);
            dayTextview = itemView.findViewById(R.id.tv_calenderitem_day);
            dayOfWeekTextview = itemView.findViewById(R.id.tv_calenderitem_day_of_week);
            priceTextview = itemView.findViewById(R.id.tv_calenderitem_price);
            bottomImageview = itemView.findViewById(R.id.iv_calenderitem);
        }

        public void bindCalenderItem(MyCalender calender) {

            if (isPerisanCalender)
            {
                dayTextview.setText(String.valueOf(calender.getDayFarsi()));
                dayOfWeekTextview.setText(calender.getDayOfWeekFarsi());
            }
            else
            {
                dayTextview.setText(String.valueOf(calender.getDay()));
                dayOfWeekTextview.setText(calender.getDayOfTheWeek());
            }

            if (calender.isMin())
            {
                itemView.setBackground(context.getDrawable((R.drawable.green_gradient)));
                dayOfWeekTextview.setTextColor(context.getResources().getColor(R.color.white));
                dayTextview.setTextColor(context.getResources().getColor(R.color.white));
            }
            else
            {
                dayOfWeekTextview.setTextColor(context.getResources().getColor(R.color.black2));
                dayTextview.setTextColor(context.getResources().getColor(R.color.black3));
                itemView.setBackgroundColor(context.getResources().getColor(R.color.white));
            }
            priceTextview.setTextColor(context.getResources().getColor(R.color.black2));
            if (calender.getMinPrice()!=null)
            {
                String minPrice = decimalFormat.format(Double.valueOf(calender.getMinPrice()));
                priceTextview.setText(minPrice);
            }
            bottomImageview.setVisibility(View.GONE);
        }
    }
    // callback for when user click on one item of calender list
    public interface OnItemClicked
    {
        void onClicked(String standardDate, int position);
    }

}
