package yoyo.app.android.com.yoyoapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import yoyo.app.android.com.yoyoapp.DataModels.Traveller;

public class TravellerCompanionsEditFragment extends Fragment {

    private static final String TAG = "TravellerCompanionsEdit";
    private EditText firstnameEditText, lastnameEditText, emailEditText, mobilenumberEditText;
    private TextView titleTextview;
    private ImageView backImageview;
    private ApiService apiService;
    private FragmentManager fragmentManager;
    private Bundle bundle;
    private Button saveButton;
    private Button deleteButton;
    private int travllerId;
    boolean isEditFragment;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_traveller_companions_edit, container, false);

        init();
        getBundle();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTravellerCompanion();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTravllerComapnion();
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

    private void deleteTravllerComapnion() {
//        apiService.deleteTravellerCompanion(travllerId, new ApiService.OnTravellerDeleted() {
//            @Override
//            public void onDeleted(Boolean result) {
//                if (result)
//                fragmentManager.popBackStack();
//            }
//        });
    }

    private void saveTravellerCompanion() {
        Traveller traveller = new Traveller();
        traveller.setFirstName(firstnameEditText.getText().toString());
        traveller.setLastName(lastnameEditText.getText().toString());
        traveller.setEmail(emailEditText.getText().toString());
        traveller.setDateOfBirth("2019-01-02");
        traveller.setIranianNationalCode("234566");
        traveller.setPassportNumber("45");
        traveller.setExpiryDateOfPassport("2020-02-02");
        traveller.setPhoneNumber("223233");
        traveller.setNationality(2);
        traveller.setGender("MALE");
        traveller.setAgeClass("ADULT");

        if (!isEditFragment)
        {
//            apiService.addTravellerCompanion(traveller, new ApiService.OnTravellerAdded() {
//                @Override
//                public void onAdded(Boolean result) {
//                    if (result)
//                    fragmentManager.popBackStack();
//                }
//            });
        }
        else
        {
            traveller.setTravellerId(travllerId);
//            apiService.editTravellerCompanion(traveller, new ApiService.OnTravellerEdited() {
//                @Override
//                public void onEdited(Boolean result) {
//                    if (result)
//                    fragmentManager.popBackStack();
//                }
//            });
        }

    }

    private void getBundle() {
        if (bundle != null) {
            isEditFragment = true;
            firstnameEditText.setText(bundle.getString("firstName"));
            lastnameEditText.setText(bundle.getString("lastName"));
            mobilenumberEditText.setText(bundle.getString("phone"));
            emailEditText.setText(bundle.getString("email"));
            travllerId = bundle.getInt("id");
        } else {
            isEditFragment = false;
            titleTextview.setText("Add Traveller");
            deleteButton.setVisibility(View.GONE);
        }
    }

    private void init() {
        bundle = getArguments();
        fragmentManager = getFragmentManager();
        saveButton = view.findViewById(R.id.button_travellercompanion_edit_save);
        firstnameEditText = view.findViewById(R.id.et_travellercompanion_edit_firstname);
        lastnameEditText = view.findViewById(R.id.et_travellercompanion_edit_lastname);
        mobilenumberEditText = view.findViewById(R.id.et_travellercompanion_edit_mobile);
        emailEditText = view.findViewById(R.id.et_travellercompanion_edit_email);
        titleTextview = view.findViewById(R.id.tv_travellercompanion_title);
        deleteButton = view.findViewById(R.id.button_travellercompanions_edit_delete);
        backImageview = view.findViewById(R.id.iv_travellercompanion_edit_back);
        apiService = new ApiService(getContext());
    }

}
