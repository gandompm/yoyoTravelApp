package yoyo.app.android.com.yoyoapp;

import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import yoyo.app.android.com.yoyoapp.Flight.FlightTicketFragment;
import yoyo.app.android.com.yoyoapp.FragmentTransaction.BaseFragment;
import yoyo.app.android.com.yoyoapp.Trip.ticket.TourTicketFragment;


public class OrdersFragment extends BaseFragment {


    private CardView tourCardview, hotelCardview, flightCardview;
    private FragmentManager fragmentManager;

    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_orders,container,false);
        init();
        onClick();

        return view;
    }

    private void onClick() {

        tourCardview.setOnClickListener(v -> {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,new TourTicketFragment()).addToBackStack("tour_ticket");
            fragmentTransaction.commit();

        });

        flightCardview.setOnClickListener(v -> {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,new FlightTicketFragment()).addToBackStack("flight_ticket");
            fragmentTransaction.commit();
        });
    }

    private void init() {
        tourCardview = view.findViewById(R.id.cv_orders_tour);
        flightCardview = view.findViewById(R.id.cv_orders_flight);
        hotelCardview = view.findViewById(R.id.cv_orders_hotel);
        fragmentManager = getFragmentManager();

    }


}
