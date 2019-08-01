package yoyo.app.android.com.yoyoapp.trip.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.util.Consumer;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import yoyo.app.android.com.yoyoapp.DataModels.Traveller;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.trip.adapter.TravellerCompanionRecyclerviewAddapter;
import java.util.ArrayList;
import java.util.List;


public class MyTravellerDialogFragment extends DialogFragment {

    private RecyclerView recyclerView;
    private TravellerCompanionRecyclerviewAddapter travellerRecyclerviewaddapter;
    private ArrayList<Traveller> travellers;
    private TextView messageTextview;
    private Consumer<Traveller> travellerConsumer;
    private BookingViewModel bookingViewModel;
    private View view;


    public MyTravellerDialogFragment(Consumer<Traveller> travellerConsumer) {
        this.travellerConsumer = travellerConsumer;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_traveller_dialog, container, false);

        travellers = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rv_mytraveller);
        messageTextview = view.findViewById(R.id.tv_travellerdialog_message);
        bookingViewModel = ViewModelProviders.of(getActivity()).get(BookingViewModel.class);

        getTravellersCompanions();
        setupRecyclerview();

        return view;
    }

    // get traveller companions if the user has them before added
    private void getTravellersCompanions() {

        bookingViewModel.initGetTravellers();
        bookingViewModel.getTravellers().observe(getActivity(), new Observer<List<Traveller>>() {
            @Override
            public void onChanged(List<Traveller> travellerList) {
                if (travellerList != null)
                {
                    if (travellerList.size()!= 0)
                    messageTextview.setVisibility(View.GONE);
                    travellers.addAll(travellerList);
                    travellerRecyclerviewaddapter.notifyDataSetChanged();
                }
            }
        });

    }

    // recycler view for travellers
    private void setupRecyclerview() {
        travellerRecyclerviewaddapter = new TravellerCompanionRecyclerviewAddapter(travellers, getActivity(), new TravellerCompanionRecyclerviewAddapter.OnItemSelected() {
            @Override
            public void onSendResult(Traveller traveller) {
                travellerConsumer.accept(traveller);
                dismiss();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(travellerRecyclerviewaddapter);
    }
}
