package yoyo.app.android.com.yoyoapp.Flight.Addaptor;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.Ticket;
import yoyo.app.android.com.yoyoapp.R;

import java.util.ArrayList;

public class TicketRecyclerviewAddapter extends RecyclerView.Adapter<TicketRecyclerviewAddapter.ReservationViewhilder> {


    private static final String TAG = "TicketRecyclerviewAddap";
    private Context context;
    private ArrayList<Ticket> tickets;

    public TicketRecyclerviewAddapter(Context context, ArrayList<Ticket> tickets) {
        this.context = context;
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public ReservationViewhilder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_ticket,parent,false);
        return new ReservationViewhilder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewhilder holder, int position) {


        holder.bindReservation(tickets.get(position));
        holder.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(tickets.get(position).getDownloadUrl());
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  tickets == null ? 0 : tickets.size();
    }

    public class ReservationViewhilder extends RecyclerView.ViewHolder
    {
        private TextView adultnumTextview;
        private TextView childnumTextview;
        private TextView infantnumTextview;
        private TextView priceTextview;
        private TextView voucherNumberTextview;
        private TextView departureTextview;
        private TextView destinationTextview;
        private TextView dateTextview;
        private Button downloadButton;


        public ReservationViewhilder(@NonNull View itemView) {
            super(itemView);
            adultnumTextview = itemView.findViewById(R.id.tv_itemticket_adultnum);
            childnumTextview = itemView.findViewById(R.id.tv_itemticket_childnum);
            infantnumTextview = itemView.findViewById(R.id.tv_itemticket_infantnum);
            priceTextview = itemView.findViewById(R.id.tv_itemticket_price);
            voucherNumberTextview = itemView.findViewById(R.id.tv_itemticket_voucher);
            departureTextview = itemView.findViewById(R.id.tv_itemticket_departure);
            destinationTextview = itemView.findViewById(R.id.tv_itemticket_destination);
            dateTextview = itemView.findViewById(R.id.tv_itemticket_date);
            downloadButton = itemView.findViewById(R.id.button_itemticket_download);
        }

        public void bindReservation(Ticket ticket)
        {
            adultnumTextview.setText(String.valueOf(ticket.getAdultNum()));
            childnumTextview.setText(String.valueOf(ticket.getChildNum()));
            infantnumTextview.setText(String.valueOf(ticket.getInfantNum()));
            priceTextview.setText(ticket.getTotalPrice());
            voucherNumberTextview.setText(ticket.getVoucherNumber());
            destinationTextview.setText(ticket.getDestination());
            departureTextview.setText(ticket.getDeparture());
            dateTextview.setText(ticket.getDate());
        }
    }
}
