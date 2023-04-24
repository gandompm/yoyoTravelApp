package yoyo.app.android.com.yoyoapp.trip.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import yoyo.app.android.com.yoyoapp.DataModels.TourRequest;
import yoyo.app.android.com.yoyoapp.R;

import java.util.ArrayList;


public class TourRequestsRecyclerviewAddaptor extends RecyclerView.Adapter<TourRequestsRecyclerviewAddaptor.TourTicketViewHolder> {

    private static final String TAG = "TourTicketRecyclerviewA";
    private Context context;
    private ArrayList<TourRequest> tourRequestArrayList;


    public TourRequestsRecyclerviewAddaptor(Context context, ArrayList<TourRequest> tourRequests)
    {
        this.context = context;
        this.tourRequestArrayList = tourRequests;
    }

    @NonNull
    @Override
    public TourTicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_tour_request,parent,false);
        return new TourTicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TourTicketViewHolder holder, final int position) {

        holder.bindItem(tourRequestArrayList.get(position));
        holder.completeInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return tourRequestArrayList.size();
    }

    public class TourTicketViewHolder extends RecyclerView.ViewHolder
    {
        private TextView titleTextview;
        private Button completeInfoButton;
        private TextView dateTextview;
        private TextView statusTextview;
        private TextView nameTextview;
        private TextView passengerNumberTextview;

        public TourTicketViewHolder(View itemView) {
            super(itemView);
            titleTextview = itemView.findViewById(R.id.tv_triprequest_title);
            completeInfoButton = itemView.findViewById(R.id.button_triprequest_complete_info);
            dateTextview = itemView.findViewById(R.id.tv_triprequest_date);
            nameTextview = itemView.findViewById(R.id.tv_triprequest_name);
            passengerNumberTextview = itemView.findViewById(R.id.tv_triprequest_passenger_number);
            statusTextview = itemView.findViewById(R.id.tv_triprequest_status);
        }

        public void bindItem(TourRequest tourRequest)
        {
            titleTextview.setText(tourRequest.getTitle());
            passengerNumberTextview.setText(String.valueOf(tourRequest.getPassengers()));
            statusTextview.setText(tourRequest.getStatus());
            nameTextview.setText(tourRequest.getBuyer());
            dateTextview.setText(tourRequest.getCreatedAt());
        }
    }
}
