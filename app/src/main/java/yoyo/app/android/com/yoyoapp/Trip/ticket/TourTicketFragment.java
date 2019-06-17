package yoyo.app.android.com.yoyoapp.Trip.ticket;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
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


public class TourTicketFragment extends BaseFragment implements View.OnClickListener {

    private TextView backTextview;
    private ImageView backImageview;

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tour_ticket,container,false);
        init();

        backTextview.setOnClickListener(this);
        backImageview.setOnClickListener(this);

        return view;
    }

    private void init() {
        backTextview = view.findViewById(R.id.tv_tour_ticket_back);
        backImageview = view.findViewById(R.id.iv_tour_ticket_back);

    }



    @Override
    public void onClick(View v) {

        if (v.getId() == backImageview.getId() || v.getId() == backTextview.getId()){

            getFragmentManager().popBackStack();


        }
    }
}
