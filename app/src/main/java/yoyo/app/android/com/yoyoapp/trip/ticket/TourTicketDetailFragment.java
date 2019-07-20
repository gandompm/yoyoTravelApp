package yoyo.app.android.com.yoyoapp.trip.ticket;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yoyo.app.android.com.yoyoapp.R;

import java.util.Calendar;
import java.util.Locale;

public class TourTicketDetailFragment extends Fragment {


    private TextView orderDateTextView, customerNameTextView, orderStatusTextView, paymentStatusTextView,
                     startDateTextView, endDateTextView, totalPriceTextView, quantityTextView, downloadTicketTextView;
    private String orderId;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tour_ticket_detail, container, false);
        init();
        getBundle();
        onClick();
        return view;
    }

    private void getBundle() {

        Bundle bundle = getArguments();
        orderId = bundle.getString("order_id");
        orderDateTextView.setText(bundle.getString("date"));
        customerNameTextView.setText(bundle.getString("name"));
        paymentStatusTextView.setText(bundle.getString("payment_status"));
        endDateTextView.setText(getStandardDate(bundle.getLong("end_datetime")));
        startDateTextView.setText(getStandardDate(bundle.getLong("start_datetime")));
        quantityTextView.setText(String.valueOf(bundle.getInt("qty")));
        totalPriceTextView.setText(String.valueOf(bundle.getFloat("price")));
        if (bundle.getBoolean("order_status")){
            orderStatusTextView.setText("Paid");
        }
        else
            orderStatusTextView.setText("not Paid");
    }

    private void onClick() {

    }

    private void init() {
        orderDateTextView = view.findViewById(R.id.tv_tour_ticket_detail_order_date);
        customerNameTextView = view.findViewById(R.id.tv_tour_ticket_detail_customer_name);
        orderStatusTextView = view.findViewById(R.id.tv_tour_ticket_detail_order_status);
        paymentStatusTextView = view.findViewById(R.id.tv_tour_ticket_detail_payment_status);
        startDateTextView = view.findViewById(R.id.tv_tour_ticket_detail_start_date);
        endDateTextView = view.findViewById(R.id.tv_tour_ticket_detail_end_date);
        totalPriceTextView = view.findViewById(R.id.tv_tour_ticket_detail_total_price);
        quantityTextView = view.findViewById(R.id.tv_tour_ticket_detail_quantity);
        downloadTicketTextView = view.findViewById(R.id.tv_tour_ticket_detail_download_ticket);
    }

    private String getStandardDate(long timestamp) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp * 1000);
        return DateFormat.format("dd-MM-yyyy", cal).toString();
    }
}
