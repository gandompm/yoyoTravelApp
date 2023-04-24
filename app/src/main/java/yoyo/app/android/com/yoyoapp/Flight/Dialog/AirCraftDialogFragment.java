package yoyo.app.android.com.yoyoapp.Flight.Dialog;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import yoyo.app.android.com.yoyoapp.Flight.Addaptor.AirCraftRecyclerviewAddapter;
import yoyo.app.android.com.yoyoapp.Flight.ApiServiceFlight;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.AirCraft;
import yoyo.app.android.com.yoyoapp.Flight.MainFlightActivity;
import yoyo.app.android.com.yoyoapp.R;

import java.util.ArrayList;

public class AirCraftDialogFragment extends DialogFragment {

    private ArrayList<AirCraft> airCraftArrayList;
    private RecyclerView recyclerView;
    private AirCraftRecyclerviewAddapter addapter;
    private ArrayList<String> idAircrafts;
    private Button okButton, cancelButton;
    private ApiServiceFlight apiServiceFlight;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aircraft_dialog, container, false);

        apiServiceFlight = new ApiServiceFlight(getContext());
        airCraftArrayList = new ArrayList<>();
        okButton = view.findViewById(R.id.button_aircraft_positive);
        cancelButton = view.findViewById(R.id.button_aircraft_negative);
        idAircrafts = ((MainFlightActivity)getActivity()).idAircrafts;

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save  selected aircrafts to main activity for later usages
                ((MainFlightActivity)getActivity()).idAircrafts = idAircrafts;
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // get aircarfts list from server
        apiServiceFlight.getAirCraftList(new ApiServiceFlight.OnAircraftsRecieved() {
            @Override
            public void onRecieved(ArrayList<AirCraft> airCrafts) {
                if (airCrafts!=null)
                {
                    airCraftArrayList.clear();
                    airCraftArrayList.addAll(airCrafts);
                    addapter.notifyDataSetChanged();
                }
            }
        });
        setupRecyclerview();

        return view;
    }

    // setup aircraft recycler view
    private void setupRecyclerview() {
        recyclerView = view.findViewById(R.id.rv_aircraft);
        addapter = new AirCraftRecyclerviewAddapter(airCraftArrayList, getContext(), new AirCraftRecyclerviewAddapter.OnItemAircraftSelected() {
            @Override
            public void onSelectd(int manufacturerId, int position) {
                // if the aircraft that the user clicked, is already clicked,
                // remove that aircraft from list
                if (idAircrafts.contains(String.valueOf(manufacturerId)))
                {
                    idAircrafts.remove(String.valueOf(manufacturerId));
                }
                else
                {
                    idAircrafts.add(String.valueOf(manufacturerId));
                }
            }
        });
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(addapter);
    }

    // generate fake data for aircraft
    private ArrayList<AirCraft> getFakeData() {
        ArrayList<AirCraft> airCrafts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            AirCraft airCraft = new AirCraft();
            airCraft.setManufacturer("Boeing 740");

            airCrafts.add(airCraft);
        }
        return airCrafts;
    }


}
