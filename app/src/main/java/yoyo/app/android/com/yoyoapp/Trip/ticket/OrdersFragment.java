package yoyo.app.android.com.yoyoapp.Trip.ticket;

import android.content.Intent;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import yoyo.app.android.com.yoyoapp.Flight.FlightTicketFragment;
import yoyo.app.android.com.yoyoapp.FragmentTransaction.BaseFragment;
import yoyo.app.android.com.yoyoapp.MainActivity;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.TripActivity;
import yoyo.app.android.com.yoyoapp.Trip.Utils.UserSharedManager;
import yoyo.app.android.com.yoyoapp.Trip.authentication.AuthenticationActivity;
import yoyo.app.android.com.yoyoapp.Trip.ticket.order.TourTicketFragment;


public class OrdersFragment extends BaseFragment {


    private CardView tourCardview, hotelCardview, flightCardview;
    private FragmentManager fragmentManager;
    private UserSharedManager userSharedManager;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_orders,container,false);
        init();
        onClick();
        if (userSharedManager.getToken().isEmpty())
        {
            startActivity(new Intent(getContext(), AuthenticationActivity.class));
            getActivity().overridePendingTransition(R.anim.slide_up,  R.anim.no_animation);
        }
        return view;
    }

    private void onClick() {

        tourCardview.setOnClickListener(v -> {
            if (userSharedManager.getToken().isEmpty())
            {
                startActivity(new Intent(getContext(), AuthenticationActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_up,  R.anim.no_animation);
            }
            else {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(((MainActivity)getActivity()).getCurrentContainer(),new TourTicketFragment())
                        .addToBackStack(String.valueOf(((MainActivity)getActivity()).getCurrentContainer()));
                fragmentTransaction.commit();
            }

        });

        flightCardview.setOnClickListener(v -> {
            if (userSharedManager.getToken().isEmpty())
            {
                startActivity(new Intent(getContext(), AuthenticationActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_up,  R.anim.no_animation);
            }
            else {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container,new FlightTicketFragment()).addToBackStack("flight_ticket");
                fragmentTransaction.commit();
            }
        });
    }

    private void init() {
        tourCardview = view.findViewById(R.id.cv_orders_tour);
        flightCardview = view.findViewById(R.id.cv_orders_flight);
        hotelCardview = view.findViewById(R.id.cv_orders_hotel);
        fragmentManager = getFragmentManager();
        userSharedManager = new UserSharedManager(getContext());
    }


}
