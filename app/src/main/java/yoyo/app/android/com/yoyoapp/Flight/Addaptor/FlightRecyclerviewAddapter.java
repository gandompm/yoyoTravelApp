package yoyo.app.android.com.yoyoapp.Flight.Addaptor;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.Flight;
import yoyo.app.android.com.yoyoapp.Flight.FlightDetails.FlightDetailsFragment;
import yoyo.app.android.com.yoyoapp.Flight.FlightSearch.FlightSearchFragment;
import yoyo.app.android.com.yoyoapp.Flight.Utils.ItemAnimation;
import yoyo.app.android.com.yoyoapp.MainActivity;
import yoyo.app.android.com.yoyoapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FlightRecyclerviewAddapter extends RecyclerView.Adapter<FlightRecyclerviewAddapter.FlightViewholder> {

    private ArrayList<Flight> flights;
    private Context context;
    private DecimalFormat decimalFormat;
    private OnFlightClicked onFlightClicked;
    private boolean on_attach = true;
    private int animation_type = 1;
    private int lastPosition = -1;

    public FlightRecyclerviewAddapter(Context context ,ArrayList<Flight> flights, OnFlightClicked onFlightClicked) {
        this.flights = flights;
        this.context = context;
        this.onFlightClicked = onFlightClicked;
        decimalFormat = new DecimalFormat("#,###,###");
    }

    @NonNull
    @Override
    public FlightViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_flight,parent,false);
        return new FlightViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightViewholder holder, final int position) {
        holder.bindFlightItem(flights.get(position));
        setAnimation(holder.itemView, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFlightClicked.onClicked(flights.get(position).getFlightId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return  flights == null ? 0 : flights.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                on_attach = false;
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        super.onAttachedToRecyclerView(recyclerView);
    }

    private void setAnimation(View view, int position) {
        if (position > lastPosition) {
            ItemAnimation.animate(view, on_attach ? position : -1, animation_type);
            lastPosition = position;
        }
    }

    public class FlightViewholder extends RecyclerView.ViewHolder
    {
        private TextView departureTimeTextview;
        private TextView originTextview;
        private TextView destinationTextview;
        private TextView priceTextview;
        private TextView cabinClassTextview;
        private TextView comanyNameTextview;
        private ImageView companyLogoImageview;
        private TextView capacityTextview;
        private TextView aircraftTextview;



        public FlightViewholder(@NonNull View itemView) {
            super(itemView);
            departureTimeTextview = itemView.findViewById(R.id.tv_flightitem_start_time);
            originTextview = itemView.findViewById(R.id.tv_flightitem_from);
            destinationTextview = itemView.findViewById(R.id.tv_flightitem_to);
            priceTextview = itemView.findViewById(R.id.tv_flightitem_price);
            cabinClassTextview = itemView.findViewById(R.id.tv_flightitem_cabin_class);
            comanyNameTextview = itemView.findViewById(R.id.tv_flightitem_company);
            companyLogoImageview = itemView.findViewById(R.id.iv_flightitem_company_logo);
            capacityTextview = itemView.findViewById(R.id.tv_flightitem_capacity);
            aircraftTextview = itemView.findViewById(R.id.tv_flightitem_aircraft);

        }

        public void bindFlightItem(Flight flight) {
            departureTimeTextview.setText(flight.getDepartureTime());
            originTextview.setText(flight.getOriginIataCode());
            destinationTextview.setText(flight.getDestinationIataCode());
            capacityTextview.setText(String.valueOf(flight.getCapacity()));
            aircraftTextview.setText(flight.getAirCraftName());


            if (flight.getAdultPrice()!=null) {
                String adultPrice = "95$";
                priceTextview.setText(adultPrice);
            }
            if (flight.getType().equals(""))
            {
                cabinClassTextview.setText("charter");
            }
            else
            {
                cabinClassTextview.setText(flight.getType());
            }
            comanyNameTextview.setText(flight.getAirline());
            if (flight.getAirlineLogo()!=null && !flight.getAirlineLogo().equals(""))
            Picasso.with(context).load(flight.getAirlineLogo()).placeholder(context.getResources().getDrawable(R.drawable.airline_default_logo))
                    .error(context.getResources().getDrawable(R.drawable.airline_default_logo)).into(companyLogoImageview);
        }
    }
    public interface OnFlightClicked
    {
        void onClicked(int flightId);
    }
}
