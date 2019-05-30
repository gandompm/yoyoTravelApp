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
import com.squareup.picasso.Picasso;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.Airline;
import yoyo.app.android.com.yoyoapp.Flight.MainFlightActivity;
import yoyo.app.android.com.yoyoapp.R;

import java.util.ArrayList;

public class AirlineRecyclerviewAddapter extends RecyclerView.Adapter<AirlineRecyclerviewAddapter.AirlineViewholder> {

    private static final String TAG = "AirlineRecyclerviewAdda";
    private ArrayList<Airline> airlines;
    private Context context;
    private OnItemAirlineSelected onItemAirlineSelected;

    public AirlineRecyclerviewAddapter(ArrayList<Airline> airlines, Context context, OnItemAirlineSelected onItemAirlineSelected) {
        this.airlines = airlines;
        this.context = context;
        this.onItemAirlineSelected = onItemAirlineSelected;
    }

    @NonNull
    @Override
    public AirlineViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_airline, parent, false);
        return new AirlineViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AirlineViewholder holder, final int position) {
        final Airline airline = airlines.get(position);
        holder.bindAirlineItem(airline);
        holder.emptyCircleImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                airline.setSelected(!airline.isSelected());
                onItemAirlineSelected.onSelectd(airline.getIataCode(),position);
                if (airline.isSelected())
                holder.greenCircleImageview.setVisibility(View.VISIBLE);
                else
                    holder.greenCircleImageview.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public int getItemCount() {
        return airlines == null ? 0 : airlines.size();
    }

    public class AirlineViewholder extends RecyclerView.ViewHolder
    {
        private TextView airlineTextview;
        private ImageView airlineLogoImageview;
        private ImageView greenCircleImageview;
        private ImageView emptyCircleImageview;

        public AirlineViewholder(@NonNull View itemView) {
            super(itemView);
            airlineTextview = itemView.findViewById(R.id.tv_airlineitem);
            greenCircleImageview = itemView.findViewById(R.id.iv_airlineitem_green_check);
            emptyCircleImageview = itemView.findViewById(R.id.iv_airlineitem_circle);
            airlineLogoImageview = itemView.findViewById(R.id.iv_airlineitem);
        }

        public void bindAirlineItem(Airline airline)
        {
            airlineTextview.setText(airline.getAirline());
            Picasso.with(context).load(airline.getAirlineLogo()).into(airlineLogoImageview);
            if (((MainFlightActivity)context).iataCodeAirlines.contains(airline.getIataCode()))
            {
                Log.d(TAG, "onCheckedChanged: ggggggg2  " + airline.isSelected());
                airline.setSelected(true);
                greenCircleImageview.setVisibility(View.VISIBLE);
            }
            else
                greenCircleImageview.setVisibility(View.GONE);
        }
    }

    // callback for when user click on one item of airline list
    public interface OnItemAirlineSelected
    {
        void onSelectd(String iataCode, int position);
    }


}
