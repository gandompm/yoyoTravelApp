package yoyo.app.android.com.yoyoapp.Trip.ticket;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import yoyo.app.android.com.yoyoapp.FragmentTransaction.BaseFragment;
import yoyo.app.android.com.yoyoapp.R;


public class TourTicketFragment extends BaseFragment {

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


    }

    private void onClick() {

    }


}
