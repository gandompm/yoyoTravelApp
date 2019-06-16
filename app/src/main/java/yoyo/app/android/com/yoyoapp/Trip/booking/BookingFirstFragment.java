package yoyo.app.android.com.yoyoapp.Trip.booking;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hbb20.CountryCodePicker;
import yoyo.app.android.com.yoyoapp.DataModels.Traveller;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.adapter.TravellerRecyclerviewAddapter;

public class BookingFirstFragment extends Fragment {

    private static final String TAG = "BookingFirstFragment";
    private RecyclerView recyclerView;
    private TravellerRecyclerviewAddapter travellerRecyclerviewAddapter;
    public  EditText mobilenumberEditText, emailEditText ,fullNameEditText;
    private TextView passengerCount;
    private ImageView minusImageview, plusImageview;
    private CountryCodePicker countryCodePicker;
    private int passengerNum = 1;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_booking_first2, container, false);
        init();
        setupContactViews();
        setupMobileNumber();
        setupRecyclerview();
        plusImageview.setOnClickListener(v -> addNumber());
        minusImageview.setOnClickListener(v -> reduceNumber());


        return view;
    }

    private void setupContactViews() {
        mobilenumberEditText.setText(((BookingActivity)getActivity()).mobileNumberString);
        emailEditText.setText(((BookingActivity)getActivity()).emailString);
        fullNameEditText.setText(((BookingActivity)getActivity()).fullNameString);
        fullNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    ((BookingActivity)getActivity()).fullNameString = s.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    ((BookingActivity)getActivity()).emailString = s.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mobilenumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    ((BookingActivity)getActivity()).mobileNumberString = s.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void reduceNumber() {
        if (passengerNum > 1) {
            passengerNum--;
            ((BookingActivity)getActivity()).passerngerNumLiveData.postValue(passengerNum);
            passengerCount.setText(String.valueOf(passengerNum));
            travellerRecyclerviewAddapter.notifyDataSetChanged();
        }
        if (passengerNum == 1)
        {
            minusImageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_light_24dp));
        }
    }

    private void addNumber() {
        if (passengerNum < 9) {
            passengerNum++;
            ((BookingActivity)getActivity()).passerngerNumLiveData.postValue(passengerNum);
            passengerCount.setText(String.valueOf(passengerNum));
            travellerRecyclerviewAddapter.notifyDataSetChanged();
        }
        if (passengerNum ==2)
        {
            appearView(minusImageview, passengerCount);
        }
    }
    private void init() {
        recyclerView = view.findViewById(R.id.rv_booking);
        mobilenumberEditText = view.findViewById(R.id.et_traveller_details_mobile);
        emailEditText = view.findViewById(R.id.et_traveller_details_email);
        countryCodePicker = view.findViewById(R.id.ccp_traveller_details);
        minusImageview = view.findViewById(R.id.iv_bookingfirst_minus);
        plusImageview = view.findViewById(R.id.iv_bookingfirst_plus);
        passengerCount = view.findViewById(R.id.tv_bookingfirst_num);
        fullNameEditText = view.findViewById(R.id.et_booking_contact_name);
    }

    // get country code for mobile number
    private void setupMobileNumber() {
        ((BookingActivity)getActivity()).mobileNumberCode = countryCodePicker.getDefaultCountryCodeWithPlus();
        if ( ((BookingActivity)getActivity()).countryCode != null) {
            countryCodePicker.setCountryForNameCode(((BookingActivity)getActivity()).countryCode);
        }
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                String s = ((BookingActivity)getActivity()).countryCode = countryCodePicker.getSelectedCountryNameCode();
                String s1= ((BookingActivity)getActivity()).mobileNumberCode = countryCodePicker.getSelectedCountryCodeWithPlus();
            }
        });
    }

    // setup traveller recycler view
    private void setupRecyclerview() {
        travellerRecyclerviewAddapter = new TravellerRecyclerviewAddapter(((BookingActivity)getActivity()).travellers, getActivity(), new TravellerRecyclerviewAddapter.OnItemSelected() {
            @Override
            public void onSendResult(Traveller traveller, int position) {
                // when the user on one traveller item clicked, call expandbottomsheet method in booking activity
                Log.d(TAG, "onSendResult: " + traveller.getFirstName());
                sendToTravellerInfoPage(position);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(travellerRecyclerviewAddapter);
    }

    private void sendToTravellerInfoPage(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);

        TravellerInfoFragment travellerInfoFragment = new TravellerInfoFragment();
        travellerInfoFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fl_booking,travellerInfoFragment);
        fragmentTransaction.addToBackStack("travellerInfo");
        fragmentTransaction.commit();
    }

    public void appearView(ImageView imageView, TextView textView)
    {
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_black_24dp));
        textView.setTextColor(getResources().getColor(R.color.colorPrimary));
    }
}
