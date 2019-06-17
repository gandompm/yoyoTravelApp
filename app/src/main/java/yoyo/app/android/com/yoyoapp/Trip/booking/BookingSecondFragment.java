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

public class BookingSecondFragment extends BaseFragment {


    private View view;
    private TextView orderDateTextView, customerNameTextView, quantityTextView, totalPriceTextview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_booking_second2, container, false);

        init();

        return view;
    }

    private void init() {
        orderDateTextView = view.findViewById(R.id.tv_booking_second_order_date);
        customerNameTextView = view.findViewById(R.id.tv_booking_second_customer_name);
        quantityTextView = view.findViewById(R.id.tv_booking_second_quantity);
        totalPriceTextview = view.findViewById(R.id.tv_booking_second_total_price);
    }


}
