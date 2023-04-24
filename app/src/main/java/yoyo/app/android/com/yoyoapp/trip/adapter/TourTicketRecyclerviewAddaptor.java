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
import yoyo.app.android.com.yoyoapp.DataModels.TourTicket;
import yoyo.app.android.com.yoyoapp.MainActivity;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.trip.ticket.TourTicketDetailFragment;

import java.util.ArrayList;


public class TourTicketRecyclerviewAddaptor extends RecyclerView.Adapter<TourTicketRecyclerviewAddaptor.TourTicketViewHolder> {

    private static final String TAG = "TourTicketRecyclerviewA";
    private Context context;
    private Fragment currentFragment;
    private ArrayList<TourTicket> tourTickets;


    public TourTicketRecyclerviewAddaptor(Context context, Fragment currentFragment, ArrayList<TourTicket> tourTickets)
    {
        this.context = context;
        this.currentFragment = currentFragment;
        this.tourTickets = tourTickets;
    }

    @NonNull
    @Override
    public TourTicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_tour_ticket,parent,false);
        return new TourTicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TourTicketViewHolder holder, final int position) {

        holder.bindItem(tourTickets.get(position));
        holder.detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                TourTicket tourTicket = tourTickets.get(position);
                bundle.putString("order_id", tourTicket.getOrderId());
                bundle.putLong("start_datetime", tourTicket.getProducts().get(0).getStartDatetime());
                bundle.putLong("end_datetime", tourTicket.getProducts().get(0).getEndDatetime());
                bundle.putInt("qty", tourTicket.getProducts().get(0).getQty());
                bundle.putFloat("price", tourTicket.getProducts().get(0).getPrice());
                bundle.putString("payment_status", tourTicket.getStatus());
                bundle.putBoolean("order_status", tourTicket.getIsPaid());
                bundle.putString("name", tourTicket.getReservatoreName());
                bundle.putString("date", tourTicket.getOrderDatetime());
                Log.d(TAG, "onClick: "+ tourTicket.getOrderDatetime());

                TourTicketDetailFragment tourTicketDetailFragment = new TourTicketDetailFragment();
                tourTicketDetailFragment.setArguments(bundle);
                ((MainActivity)context).showFragment(currentFragment, tourTicketDetailFragment, "",false );
            }
        });

    }

    @Override
    public int getItemCount() {
        return tourTickets.size();
    }

    public class TourTicketViewHolder extends RecyclerView.ViewHolder
    {
        private TextView titleTextview;
        private Button   detailsButton;
        private TextView dateTextview;
        private TextView statusTextview;
        private TextView isPaidTextview;
        private TextView priceTextview;

        public TourTicketViewHolder(View itemView) {
            super(itemView);
            titleTextview = itemView.findViewById(R.id.tv_tourticket_title);
            detailsButton = itemView.findViewById(R.id.button_tourticket_details);
            dateTextview = itemView.findViewById(R.id.tv_touritem_date);
            isPaidTextview = itemView.findViewById(R.id.tv_ticketitem_ispaid);
            priceTextview = itemView.findViewById(R.id.tv_tourticket_price);
            statusTextview = itemView.findViewById(R.id.tv_tourticket_status);
        }

        public void bindItem(TourTicket tourTicket)
        {
            titleTextview.setText(tourTicket.getProducts().get(0).getName());
            priceTextview.setText(String.valueOf(tourTicket.getProducts().get(0).getPrice()));
            statusTextview.setText(tourTicket.getStatus());
            dateTextview.setText(tourTicket.getOrderDatetime());

            Log.d(TAG, "bindItem: aaaaa" + tourTicket.getIsPaid());
            if (tourTicket.getIsPaid())
            {
                isPaidTextview.setTextColor(context.getResources().getColor(R.color.colorAccent));
                isPaidTextview.setText("Paid");
            }
            else
            {
                isPaidTextview.setText("Not Paid");
            }
        }
    }
}
