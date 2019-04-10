package yoyo.app.android.com.yoyoapp.Flight.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import yoyo.app.android.com.yoyoapp.Flight.Addaptor.AirlineRecyclerviewAddapter;
import yoyo.app.android.com.yoyoapp.Flight.ApiServiceFlight;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.Airline;
import yoyo.app.android.com.yoyoapp.Flight.MainFlightActivity;
import yoyo.app.android.com.yoyoapp.R;

import java.util.ArrayList;

public class AirlineDialogFragment extends DialogFragment {

    private ArrayList<Airline> airlines;
    private RecyclerView recyclerView;
    private AirlineRecyclerviewAddapter addapter;
    private ArrayList<String> iataCodeAirlines;
    private Button okButton, cancelButton;
    private ApiServiceFlight apiServiceFlight;
    private int position = 0;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_airline_dialog, container, false);

        apiServiceFlight = new ApiServiceFlight(getContext());
        airlines = new ArrayList<>();
        iataCodeAirlines = ((MainFlightActivity)getActivity()).iataCodeAirlines;
        okButton = view.findViewById(R.id.button_airline_positive);
        cancelButton = view.findViewById(R.id.button_airline_negative);
        setupRecyclerview();

        // get airline list from server
        apiServiceFlight.getAirlineList(new ApiServiceFlight.OnAirlinesRecieved() {
            @Override
            public void onRecieved(ArrayList<Airline> airlineArrayList) {
                if(airlineArrayList!=null)
                {
                    airlines.clear();
                    airlines.addAll(airlineArrayList);
                    addapter.notifyDataSetChanged();
                }
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save  selected airlines to main activity for later usages
                ((MainFlightActivity)getActivity()).iataCodeAirlines = iataCodeAirlines;
                 dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    // setup airline recycler view
    private void setupRecyclerview() {
        recyclerView = view.findViewById(R.id.rv_airline);
        final LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        addapter = new AirlineRecyclerviewAddapter(airlines, getContext(), new AirlineRecyclerviewAddapter.OnItemAirlineSelected() {
            @Override
            public void onSelectd(String iataCode, int newPosition) {
                if (iataCodeAirlines.contains(iataCode))
                {
                    iataCodeAirlines.remove(iataCode);
                }
                else
                {
                    iataCodeAirlines.add(iataCode);
                    if (newPosition > position) {
                        linearLayoutManager.smoothScrollToPosition(recyclerView,null,newPosition + 2);
                    }
                    else if (newPosition > 1)
                    {
                        linearLayoutManager.smoothScrollToPosition(recyclerView,null,newPosition - 2);
                    }
                }
                position = newPosition;
            }
        });
        recyclerView.setAdapter(addapter);
    }

    // generate fake data for airlines
    private ArrayList<Airline> getFakeData() {
        ArrayList<Airline> airlines = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Airline airline = new Airline();
            airline.setAirline("Air Asia");
            if (i==0)
            {
                airline.setIataCode("df");
            }
            if (i==1)
            {
                airline.setIataCode("qw");
            }
            if (i==2)
                airline.setIataCode("yu");
            if (i==3)
                airline.setIataCode("hj");
            if (i==4)
                airline.setIataCode("kl");
            if (i==5)
                airline.setIataCode("cv");
            if (i==6)
                airline.setIataCode("bn");


            airlines.add(airline);
        }
        return airlines;
    }


}
