package yoyo.app.android.com.yoyoapp.Trip.ticket;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import androidx.cardview.widget.CardView;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import yoyo.app.android.com.yoyoapp.DataModels.Order;
import yoyo.app.android.com.yoyoapp.FragmentTransaction.BaseFragment;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.ApiService;

import java.util.ArrayList;


public class TourTicketFragment extends BaseFragment {

    private static final String TAG = "TourTicketFragment";
    private CardView tourCardview, hotelCardview, flightCardview;
    private FragmentManager fragmentManager;

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tour_ticket,container,false);
        init();
        onClick();
        return view;
    }

    private void init() {
//        ApiService apiService = new ApiService(getContext());
//        apiService.getOrderRequest(new Consumer<ArrayList<Order>>() {
//            @Override
//            public void accept(ArrayList<Order> orders) {
//                Log.d(TAG, "accept: "+ orders);
//            }
//        });
    }

    private void onClick() {

    }


}
