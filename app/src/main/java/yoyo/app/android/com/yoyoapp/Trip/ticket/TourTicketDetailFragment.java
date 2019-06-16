package yoyo.app.android.com.yoyoapp.Trip.ticket;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yoyo.app.android.com.yoyoapp.R;

public class TourTicketDetailFragment extends Fragment {


    private TextView orderDateTextView, customerNameTextView, orderStatusTextView, paymentStatusTextView,
                     startDateTextView, endDateTextView, totalPriceTextView, quantityTextView, downloadTicketTextView;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tour_ticket_detail, container, false);
        init();
        onClick();
        return view;
    }

    private void onClick() {

    }

    private void init() {
        orderDateTextView = view.findViewById(R.id.tv_tour_ticket_detail_order_date);
        customerNameTextView = view.findViewById(R.id.tv_tour_ticket_detail_customer_name);
        orderStatusTextView = view.findViewById(R.id.tv_tour_ticket_detail_order_status);
        paymentStatusTextView = view.findViewById(R.id.tv_tour_ticket_detail_payment_status);
        endDateTextView = view.findViewById(R.id.tv_tour_ticket_detail_end_date);
        totalPriceTextView = view.findViewById(R.id.tv_tour_ticket_detail_total_price);
        quantityTextView = view.findViewById(R.id.tv_tour_ticket_detail_quantity);
        downloadTicketTextView = view.findViewById(R.id.tv_tour_ticket_detail_download_ticket);

    }

}
