package yoyo.app.android.com.yoyoapp.Trip.profile.travellerCompanion;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import yoyo.app.android.com.yoyoapp.DataModels.Traveller;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.Utils.UserSharedManager;
import yoyo.app.android.com.yoyoapp.Trip.adapter.TravellerCompanionRecyclerviewAddapter;

import java.util.ArrayList;
import java.util.List;


public class TravellerCompanionFragment extends Fragment {

    private static final String TAG = "TravellerCompanionFragm";
    private RecyclerView recyclerView;
    private ArrayList<Traveller> travellers;
    private FragmentManager fragmentManager;
    private ImageView backImageview;
    private FloatingActionButton floatingActionButton;
    private ProgressBar progressBar;
    private UserSharedManager userSharedManager;
    private TravellerViewModel travellerViewModel;
    private TravellerCompanionRecyclerviewAddapter travellerRecyclerviewaddapter;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_traveller_companion, container, false);

        init();
        getTravellersCompanions();

        // add button for adding traveller companion
        floatingActionButton.setOnClickListener(v -> setupEditTravellerPage());
        backImageview.setOnClickListener(v -> getFragmentManager().popBackStack());

        return view;
    }

    private void setupEditTravellerPage() {
        TravellerCompanionsEditFragment detailsFragment = new TravellerCompanionsEditFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,detailsFragment).addToBackStack("traveller companion edit");
        fragmentTransaction.commit();
    }

    private void init() {
        travellerViewModel = ViewModelProviders.of(getActivity()).get(TravellerViewModel.class);
        travellers = new ArrayList<>();
        progressBar = view.findViewById(R.id.progress_bar);
        floatingActionButton = view.findViewById(R.id.fbutton_traveller_companion);
        recyclerView = view.findViewById(R.id.rv_traveller_companion);
        backImageview = view.findViewById(R.id.iv_traveller_companion_back);
        fragmentManager = getFragmentManager();
        userSharedManager = new UserSharedManager(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    // get traveller companion from server
    private void getTravellersCompanions() {

        progressBar.setVisibility(View.VISIBLE);
        travellerViewModel.initGetTravellers();
        travellerViewModel.getTravellers().observe(getActivity(), new Observer<List<Traveller>>() {
            @Override
            public void onChanged(List<Traveller> newTravellers) {
                progressBar.setVisibility(View.GONE);
                if (newTravellers != null)
                {
                    travellers.clear();
                    travellers.addAll(newTravellers);
                    if (travellerRecyclerviewaddapter == null) {
                        setupRecyclerview();
                    }
                    else
                    travellerRecyclerviewaddapter.notifyDataSetChanged();
                }
            }
        });
    }

    // setup recycler view for traveller companions
    private void setupRecyclerview() {
        travellerRecyclerviewaddapter = new TravellerCompanionRecyclerviewAddapter(travellers, getActivity(), new TravellerCompanionRecyclerviewAddapter.OnItemSelected() {
            @Override
            public void onSendResult(Traveller traveller) {
                Bundle bundle = new Bundle();
                bundle.putString("id",traveller.getId());
                bundle.putString("firstName",traveller.getFirstName());
                bundle.putString("lastName",traveller.getLastName());
                bundle.putString("gender",traveller.getGender());
                bundle.putString("nationality",traveller.getNationality());
                bundle.putBoolean("isIranian",traveller.isIranian());
                bundle.putString("iranianCode",traveller.getIranianNationalCode());
                bundle.putString("passport",traveller.getPassportNumber());
                bundle.putString("dateOfBirth",traveller.getDateOfBirth());

                TravellerCompanionsEditFragment travellerCompanionsEditFragment = new TravellerCompanionsEditFragment();
                travellerCompanionsEditFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.container,travellerCompanionsEditFragment);
                fragmentTransaction.addToBackStack("edit traveller companion");
                fragmentTransaction.commit();
            }
        });
        recyclerView.setAdapter(travellerRecyclerviewaddapter);
    }
}
