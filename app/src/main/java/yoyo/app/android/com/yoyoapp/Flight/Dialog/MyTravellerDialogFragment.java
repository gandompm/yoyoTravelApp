package yoyo.app.android.com.yoyoapp.Flight.Dialog;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import yoyo.app.android.com.yoyoapp.Flight.Addaptor.TravellerCompanionRecyclerviewAddapter;
import yoyo.app.android.com.yoyoapp.Flight.ApiServiceFlight;
import yoyo.app.android.com.yoyoapp.Booking.BookingActivity;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.Traveller;
import yoyo.app.android.com.yoyoapp.R;

import java.util.ArrayList;

public class MyTravellerDialogFragment extends DialogFragment {

    private RecyclerView recyclerView;
    private TravellerCompanionRecyclerviewAddapter travellerRecyclerviewaddapter;
    private ArrayList<Traveller> travellers;
    private ApiServiceFlight apiServiceFlight;
    private TextView messageTextview;

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_traveller_dialog, container, false);

        travellers = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rv_mytraveller);
        apiServiceFlight = new ApiServiceFlight(getContext());
        messageTextview = view.findViewById(R.id.tv_travellerdialog_message);

        getTravellersCompanions();
        setupRecyclerview();


        return view;
    }

    // get traveller companions if the user has them before added
    private void getTravellersCompanions() {
        apiServiceFlight.getTravellersCompanions(new ApiServiceFlight.OnTravellersRecieved() {
            @Override
            public void onRecieved(ArrayList<Traveller> travellersArray) {
                if (travellersArray != null)
                {
                    if (travellersArray.size()!= 0)
                    messageTextview.setVisibility(View.GONE);
                    travellers.addAll(travellersArray);
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
                ((BookingActivity)getActivity()).initializeViews(traveller);
                dismiss();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(travellerRecyclerviewaddapter);
    }

    // generate fake data for travellers
    private ArrayList<Traveller> getFakeTravellers()
    {
        ArrayList<Traveller> travellers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Traveller traveller = new Traveller();
            traveller.setFirstName("Ali");
            traveller.setLastName("Saeedi");

            travellers.add(traveller);
        }
        return travellers;
    }


}
