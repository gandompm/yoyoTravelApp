package yoyo.app.android.com.yoyoapp.Trip.ticket.order;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import yoyo.app.android.com.yoyoapp.DataModels.TourTicket;
import yoyo.app.android.com.yoyoapp.DataModels.Trip;
import yoyo.app.android.com.yoyoapp.FragmentTransaction.BaseFragment;
import yoyo.app.android.com.yoyoapp.OrdersFragment;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.adapter.TourTicketRecyclerviewAddaptor;

import java.util.ArrayList;


public class TourTicketFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "TourTicketFragment";
    private TextView backTextview;
    private ImageView backImageview;
    private TourTicketViewModel tourTicketViewModel;
    private TourTicketRecyclerviewAddaptor addaptor;
    private ArrayList<TourTicket> tourTicketsList;
    private RecyclerView recyclerView;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tour_ticket,container,false);

        init();
        backTextview.setOnClickListener(this);
        backImageview.setOnClickListener(this);
        getTourTickets();


        return view;
    }

    private void getTourTickets() {
        tourTicketViewModel.initTours();
        tourTicketViewModel.getTourTickets().observe(getActivity(), new Observer<ArrayList<TourTicket>>() {
            @Override
            public void onChanged(ArrayList<TourTicket> tourTickets) {
                if (tourTickets != null){
                    tourTicketsList.clear();
                    tourTicketsList.addAll(tourTickets);
                    if (addaptor == null) {
                        addaptor = new TourTicketRecyclerviewAddaptor(getContext(),tourTicketsList);
                        recyclerView.setAdapter(addaptor);
                    }
                    else
                        addaptor.notifyDataSetChanged();
                }
            }
        });
    }

    private void init() {
        backTextview = view.findViewById(R.id.tv_tour_ticket_back);
        backImageview = view.findViewById(R.id.iv_tour_ticket_back);
        tourTicketsList = new ArrayList<>();
        tourTicketViewModel = ViewModelProviders.of(getActivity()).get(TourTicketViewModel.class);
        recyclerView = view.findViewById(R.id.rv_tour_ticket_result);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == backImageview.getId() || v.getId() == backTextview.getId()){
            getFragmentManager().popBackStack();
        }
    }
}
