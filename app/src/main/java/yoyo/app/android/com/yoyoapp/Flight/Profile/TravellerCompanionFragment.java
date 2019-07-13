package yoyo.app.android.com.yoyoapp.Flight.Profile;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import yoyo.app.android.com.yoyoapp.Flight.Addaptor.TravellerCompanionRecyclerviewAddapter;
import yoyo.app.android.com.yoyoapp.Flight.ApiServiceFlight;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.Traveller;
import yoyo.app.android.com.yoyoapp.FragmentTransaction.BaseFragment;
import yoyo.app.android.com.yoyoapp.R;
import java.util.ArrayList;


public class TravellerCompanionFragment extends BaseFragment {

    private static final String TAG = "TravellerCompanionFragm";
    private RecyclerView recyclerView;
    private ArrayList<Traveller> travellers;
    private FragmentManager fragmentManager;
    private ImageView backImageview;
    private FloatingActionButton floatingActionButton;
    private ApiServiceFlight apiServiceFlight;
    private ProgressBar progressBar;
    private TravellerCompanionRecyclerviewAddapter travellerRecyclerviewaddapter;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_traveller_companion, container, false);

        init();
        setupRecyclerview();
        getTravellersCompanions();

        // add button for adding traveller companion
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TravellerCompanionsEditFragment detailsFragment = new TravellerCompanionsEditFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container,detailsFragment).addToBackStack("traveller companion edit");
                fragmentTransaction.commit();
            }
        });

        backImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        return view;
    }

    private void init() {
        travellers = new ArrayList<>();
        apiServiceFlight = new ApiServiceFlight(getContext());
        progressBar = view.findViewById(R.id.progress_bar);
        floatingActionButton = view.findViewById(R.id.fbutton_traveller_companion);
        recyclerView = view.findViewById(R.id.rv_traveller_companion);
        backImageview = view.findViewById(R.id.iv_traveller_companion_back);
        fragmentManager = getFragmentManager();
    }

    // get traveller companion from server
    private void getTravellersCompanions() {

        progressBar.setVisibility(View.VISIBLE);
        apiServiceFlight.getTravellersCompanions(new ApiServiceFlight.OnTravellersRecieved() {
            @Override
            public void onRecieved(ArrayList<Traveller> travellersArray) {
                progressBar.setVisibility(View.GONE);
                if (travellersArray!=null)
                {
                    travellers.clear();
                    travellers.addAll(travellersArray);
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
                bundle.putString("firstName",traveller.getFirstName());
                bundle.putString("lastName",traveller.getLastName());
                bundle.putString("gender",traveller.getGender());
                bundle.putString("nationality",traveller.getNationality());
                bundle.putBoolean("isIranian",traveller.isIranian());
                bundle.putString("iranianCode",traveller.getIranianNationalCode());
                bundle.putString("passport",traveller.getPassportNumber());
                bundle.putString("dateOfBirth",traveller.getDateOfBirth());
                bundle.putString("ageClass",traveller.getAgeClass());
                bundle.putInt("id",traveller.getTravellerId());

                TravellerCompanionsEditFragment travellerCompanionsEditFragment = new TravellerCompanionsEditFragment();
                travellerCompanionsEditFragment.setArguments(bundle);
                if (mFragmentNavigation != null) {
                    mFragmentNavigation.pushFragment(travellerCompanionsEditFragment);
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(travellerRecyclerviewaddapter);
    }

    //generate fake data for traveller companions
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
