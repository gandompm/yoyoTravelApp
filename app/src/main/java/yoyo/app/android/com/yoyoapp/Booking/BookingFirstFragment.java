package yoyo.app.android.com.yoyoapp.Booking;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hbb20.CountryCodePicker;
import yoyo.app.android.com.yoyoapp.Flight.Addaptor.TravellerRecyclerviewAddapter;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.Traveller;
import yoyo.app.android.com.yoyoapp.R;


public class BookingFirstFragment extends Fragment {

    private static final String TAG = "BookingFirstFragment";
    private RecyclerView recyclerView;
    private TravellerRecyclerviewAddapter travellerRecyclerviewAddapter;
    public EditText mobilenumberEditText,emailEditText;
    private CountryCodePicker countryCodePicker;
    public String mobileNumber;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_booking_first, container, false);

        init();
        setupMobileNumber();
        setupRecyclerview();

        return view;
    }

    private void init() {
        recyclerView = view.findViewById(R.id.rv_booking);
        mobilenumberEditText = view.findViewById(R.id.et_traveller_details_mobile);
        emailEditText = view.findViewById(R.id.et_traveller_details_email);
        countryCodePicker = view.findViewById(R.id.ccp_traveller_details);
    }

    // get country code for mobile number
    private void setupMobileNumber() {
        mobileNumber = countryCodePicker.getDefaultCountryCodeWithPlus();
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                mobileNumber = countryCodePicker.getSelectedCountryCodeWithPlus();
            }
        });
    }

    // setup traveller recycler view
    private void setupRecyclerview() {
        travellerRecyclerviewAddapter = new TravellerRecyclerviewAddapter(((BookingActivity) getActivity()).travellers, getActivity(), new TravellerRecyclerviewAddapter.OnItemSelected() {
            @Override
            public void onSendResult(Traveller traveller, int position) {
                // when the user on one traveller item clicked, call expandbottomsheet method in booking activity
                Log.d(TAG, "onSendResult: " + traveller.getFirstName());
                ((BookingActivity) getActivity()).expandBottomSheet(traveller, position);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(travellerRecyclerviewAddapter);
    }

    // update adapter when the items change
    public void updateaddapter() {
        travellerRecyclerviewAddapter.notifyDataSetChanged();
    }
}
