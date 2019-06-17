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
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;
import yoyo.app.android.com.yoyoapp.DataModels.TourRequest;
import yoyo.app.android.com.yoyoapp.DataModels.TourTicket;
import yoyo.app.android.com.yoyoapp.DataModels.Trip;
import yoyo.app.android.com.yoyoapp.FragmentTransaction.BaseFragment;
import yoyo.app.android.com.yoyoapp.OrdersFragment;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.adapter.FoldingCellRecyclerviewAdapter;
import yoyo.app.android.com.yoyoapp.Trip.adapter.TourRequestsRecyclerviewAddaptor;
import yoyo.app.android.com.yoyoapp.Trip.adapter.TourTicketRecyclerviewAddaptor;

import java.util.ArrayList;


public class TourTicketFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "TourTicketFragment";
    private TextView backTextview;
    private ShimmerRecyclerView shimmerRecycler;
    private ImageView backImageview;
    private TourTicketViewModel tourTicketViewModel;
    private TourTicketRecyclerviewAddaptor addaptor;
    private TourRequestsRecyclerviewAddaptor requestAddapter;
    private ArrayList<TourTicket> tourTicketsList;
    private ArrayList<TourRequest> tourRequestArrayList;
    private ToggleSwitch ticketTypeToggleSwitch;
    private RecyclerView recyclerView;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tour_ticket,container,false);

        init();
        backTextview.setOnClickListener(this);
        backImageview.setOnClickListener(this);
        setupToggleButton();
        getTourTickets();


        return view;
    }

    private void setupToggleButton() {
        ticketTypeToggleSwitch.setOnChangeListener(new ToggleSwitch.OnChangeListener() {
            @Override
            public void onToggleSwitchChanged(int i) {
                if(i==0)
                {
                    addaptor = null;
                    getTourTickets();
                }
                else {
                    requestAddapter = null;
                    getTourRequests();
                }
            }
        });
    }

    private void getTourRequests() {
        shimmerRecycler.showShimmerAdapter();
        tourTicketViewModel.initTourRequests();
        tourTicketViewModel.getTourRequests().observe(getActivity(), new Observer<ArrayList<TourRequest>>() {
            @Override
            public void onChanged(ArrayList<TourRequest> tourRequests) {
                if (tourRequests != null) {
                    tourRequestArrayList.clear();
                    tourRequestArrayList.addAll(tourRequests);
                    if (requestAddapter == null) {
                        requestAddapter = new TourRequestsRecyclerviewAddaptor(getContext(),tourRequests);
                        recyclerView.setAdapter(requestAddapter);
                    }
                    else
                        requestAddapter.notifyDataSetChanged();
                    shimmerRecycler.hideShimmerAdapter();
                }
            }
        });
    }

    private void getTourTickets() {
        shimmerRecycler.showShimmerAdapter();
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
                    shimmerRecycler.hideShimmerAdapter();
                }
            }
        });
    }

    private void init() {
        shimmerRecycler = view.findViewById(R.id.shimmer_recycler_view);
        backTextview = view.findViewById(R.id.tv_tour_ticket_back);
        backImageview = view.findViewById(R.id.iv_tour_ticket_back);
        tourTicketsList = new ArrayList<>();
        tourRequestArrayList = new ArrayList<>();
        tourTicketViewModel = ViewModelProviders.of(getActivity()).get(TourTicketViewModel.class);
        recyclerView = view.findViewById(R.id.rv_tour_ticket_result);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ticketTypeToggleSwitch = view.findViewById(R.id.toggleSwitch_trip_search);
        ticketTypeToggleSwitch.setCheckedPosition(0);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == backImageview.getId() || v.getId() == backTextview.getId()){
            getFragmentManager().popBackStack();
        }
    }
}
