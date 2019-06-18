package yoyo.app.android.com.yoyoapp.Trip.booking;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yoyo.app.android.com.yoyoapp.FragmentTransaction.BaseFragment;
import yoyo.app.android.com.yoyoapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BookingSecondFragment extends BaseFragment {


    private View view;
    private TextView orderDateTextView, customerNameTextView, quantityTextView, totalPriceTextview;
    private int travellerNum;
    private double price;
    private String url,name,date;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_booking_second2, container, false);

        init();
        getBundle();
        calculatePrice();
        setupViews();

        return view;
    }

    private void setupViews() {
        totalPriceTextview.setText(String.valueOf(price));
        customerNameTextView.setText(name);
        quantityTextView.setText(String.valueOf(travellerNum));
        orderDateTextView.setText(date);
    }

    private void calculatePrice() {
        price = price * travellerNum;
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    private void getBundle() {
        Bundle bundle = getArguments();
        travellerNum = bundle.getInt("travellerNumber");
        price = bundle.getDouble("price");
        url = bundle.getString("url");
        name = bundle.getString("name");
    }

    private void init() {
        orderDateTextView = view.findViewById(R.id.tv_booking_second_order_date);
        customerNameTextView = view.findViewById(R.id.tv_booking_second_customer_name);
        quantityTextView = view.findViewById(R.id.tv_booking_second_quantity);
        totalPriceTextview = view.findViewById(R.id.tv_booking_second_total_price);
    }


}
