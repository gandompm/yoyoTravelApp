package yoyo.app.android.com.yoyoapp.trip.ticket;

import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import es.dmoral.toasty.Toasty;
import yoyo.app.android.com.yoyoapp.FragmentTransaction.BaseFragment;
import yoyo.app.android.com.yoyoapp.MainActivity;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.trip.Utils.UserSharedManager;
import yoyo.app.android.com.yoyoapp.trip.ticket.order.TourTicketFragment;


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
        return view;
    }

    private void onClick() {
        tourCardview.setOnClickListener(v -> {
            if (userSharedManager.getToken().isEmpty())
            {
                ((MainActivity)getActivity()).popUpSignInSignUpActivity();
            }
            else {
                ((MainActivity)getActivity()).showFragment(this,new TourTicketFragment(),false);
            }

        });

        flightCardview.setOnClickListener(v -> {
            if (userSharedManager.getToken().isEmpty())
            {
                ((MainActivity)getActivity()).popUpSignInSignUpActivity();
            }
            else {
                Toasty.info(getContext(),"Coming soon...").show();
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
