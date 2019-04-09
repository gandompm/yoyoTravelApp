package yoyo.app.android.com.yoyoapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import yoyo.app.android.com.yoyoapp.Addapters.TravellerCompanionRecyclerviewAddaptor;
import yoyo.app.android.com.yoyoapp.DataModels.Traveller;

import java.util.ArrayList;


public class TravellerCompanionFragment extends Fragment {

    private static final String TAG = "TravellerCompanionFragm";
    private RecyclerView recyclerView;
    private ArrayList<Traveller> travellers;
    private ImageView backImageview;
    private FloatingActionButton floatingActionButton;
    private FragmentManager fragmentManager;
    private ApiService apiService;
    private TravellerCompanionRecyclerviewAddaptor travellerRecyclerviewAddaptor;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_traveller_companion, container, false);

        travellers = new ArrayList<>();
        apiService = new ApiService(getContext());
        floatingActionButton = view.findViewById(R.id.fbutton_traveller_companion);
        recyclerView = view.findViewById(R.id.rv_traveller_companion);
        backImageview = view.findViewById(R.id.iv_traveller_companion_back);
        fragmentManager = getFragmentManager();
        getTravellersCompanions();
        setupRecyclerview();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TravellerCompanionsEditFragment detailsFragment = new TravellerCompanionsEditFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_framelayout,detailsFragment).addToBackStack("traveller companion edit");
                fragmentTransaction.commit();
            }
        });

        backImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });


        return view;
    }

    private void getTravellersCompanions() {
//        apiService.getTravellersCompanions(new ApiService.OnTravellersRecieved() {
//            @Override
//            public void onRecieved(ArrayList<Traveller> travellersArray) {
//                travellers.addAll(travellersArray);
//                travellerRecyclerviewAddaptor.notifyDataSetChanged();
//            }
//        });
    }

    private void setupRecyclerview() {
        travellerRecyclerviewAddaptor = new TravellerCompanionRecyclerviewAddaptor(false,travellers, getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(travellerRecyclerviewAddaptor);
    }

}
