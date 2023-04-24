package yoyo.app.android.com.yoyoapp.Flight;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.hbb20.CountryCodePicker;
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;
import es.dmoral.toasty.Toasty;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.Country;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.Traveller;
import yoyo.app.android.com.yoyoapp.Flight.Enum.AgeClass;
import yoyo.app.android.com.yoyoapp.Flight.Enum.Gender;
import yoyo.app.android.com.yoyoapp.Flight.SearchDialog.SampleSearchModel;
import yoyo.app.android.com.yoyoapp.Flight.Utils.DateCalenderSetup;
import yoyo.app.android.com.yoyoapp.Flight.Utils.NationalCodeUtil;
import yoyo.app.android.com.yoyoapp.R;

import java.util.ArrayList;

public class TravellerCompanionsEditFragment extends Fragment {

    private static final String TAG = "TravellerCompanionsEdit";
    private EditText firstnameEditText, lastnameEditText;
    private EditText  iraninanNationalCodeEditText, passportEditText ;
    private TextView titleTextview, iraninanNationalCodeTextview, passportTextview , dateOfBirthTextview ,nationalityTextview;
    private ImageView backImageview;
    private FragmentManager fragmentManager;
    private ApiServiceFlight apiServiceFlight;
    private Bundle bundle;
    private Button saveButton;
    private Button deleteButton;
    private int travllerId;
    private CountryCodePicker countryCodePicker;
    private Gender gender;
    private ProgressBar progressBar1, progressBar2;
    private ToggleSwitch genderToggleButton, ageClassToggleSwitch;
    private boolean isEditFragment , isIranian;
    private DatePickerDialog.OnDateSetListener dateOfBirthListner ,expiryPassportListner;
    private AgeClass ageClass;
    private ArrayList<SampleSearchModel> countryList;
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
        nationalityTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupCountryList();
            }
        });

        getCountryListApi();
        setupDatePickers();
        setupToggleButton();

        return view;
    }

    // setup date picker for user's birth date
    private void setupDatePickers() {
        new DateCalenderSetup(getContext(), dateOfBirthTextview,dateOfBirthListner);
    }

    // setup toggle button for gender and age
    private void setupToggleButton() {

        genderToggleButton.setOnChangeListener(new ToggleSwitch.OnChangeListener() {
            @Override
            public void onToggleSwitchChanged(int i) {
                if (i == 0)
                {
                    gender = Gender.MALE;
                }
                else
                {
                    gender = Gender.FEMALE;
                }
            }
        });

        ageClassToggleSwitch.setOnChangeListener(new ToggleSwitch.OnChangeListener() {
            @Override
            public void onToggleSwitchChanged(int i) {
                if (i == 0)
                {
                    ageClass = AgeClass.ADULT;
                }else if (i ==1)
                {
                    ageClass = AgeClass.CHILD;
                }else{
                    ageClass = AgeClass.INFANT;
                }
            }
        });
    }

    // delete traveller
    private void deleteTravllerComapnion() {
        progressBar2.setVisibility(View.VISIBLE);
        apiServiceFlight.deleteTravellerCompanion(travllerId, new ApiServiceFlight.OnTravellerDeleted() {
            @Override
            public void onDeleted(Boolean result) {
                progressBar2.setVisibility(View.GONE);
                if (result) {
                    Log.d(TAG, "onDeleteddddddd: ");
                    fragmentManager.popBackStack();
                }
            }
        });
    }

    // save traveller, check errors
    private void saveTravellerCompanion() {
        Traveller traveller = new Traveller();
        int error = 0;

        if (firstnameEditText.getText().toString().equals("")) {
            error = 1;
            firstnameEditText.setError(getString(R.string.please_enter_firstname));
        }else {
            traveller.setFirstName(firstnameEditText.getText().toString());
        }

        if (lastnameEditText.getText().toString().equals("")){
            error = 2;
            lastnameEditText.setError(getString(R.string.please_enter_lastname));
        }else {

            traveller.setLastName(lastnameEditText.getText().toString());
        }


        if (dateOfBirthTextview.getText().toString().equals("")){
            dateOfBirthTextview.setError(getString(R.string.pl_enter_date_of_birth));
            error = 3;
        }
        else {
            traveller.setDateOfBirth(dateOfBirthTextview.getText().toString());
        }

        if (nationalityTextview.getText().toString().equals(""))
        {
            nationalityTextview.setError(getString(R.string.please_enter_nationality));
            error = 4;
        }
        else
        {
            int iend = nationalityTextview.getText().toString().indexOf("(");
            traveller.setNationality( nationalityTextview.getText().toString().substring(iend+1 , nationalityTextview.getText().toString().indexOf(")")));
            if (isIranian)
            {
                if (iraninanNationalCodeEditText.getText().toString().equals("")) {
                    error = 4;
                    iraninanNationalCodeTextview.setError(getString(R.string.pl_enter_iranian_code));
                }
                else
                {
                    NationalCodeUtil nationalCodeUtil = new NationalCodeUtil();
                    Log.d(TAG, "onClick:  cccc  : "+ iraninanNationalCodeEditText.getText().toString() );
                    if (!nationalCodeUtil.checkNationalCode(iraninanNationalCodeEditText.getText().toString()))
                    {
                        error = 6;
                        Toasty.error(getContext(),getString(R.string.iranina_national_code_not_valid)).show();
                    }else
                    {
                        traveller.setIranianNationalCode(iraninanNationalCodeEditText.getText().toString());
                    }
                }
            }
            else
            {
                if (passportEditText.getText().toString().equals("")){
                    error  = 5;
                    passportTextview.setError(getString(R.string.pl_enter_passport));
                }
                else
                    traveller.setPassportNumber(passportEditText.getText().toString());
            }
        }

        traveller.setIranian(isIranian);
        traveller.setGender(gender.toString());
        traveller.setAgeClass(ageClass.toString());

        // this fragment is add fragment and no error has occurred
        if (!isEditFragment && error==0)
        {
            progressBar1.setVisibility(View.VISIBLE);
            apiServiceFlight.addTravellerCompanion(traveller, new ApiServiceFlight.OnTravellerAdded() {
                @Override
                public void onAdded(Boolean result) {
                    progressBar1.setVisibility(View.GONE);
                    if (result)
                    fragmentManager.popBackStack();
                }
            });
        }
        // this fragment is edit fragment and no error has occurred
        else if (error == 0)
        {
            progressBar1.setVisibility(View.VISIBLE);
            traveller.setTravellerId(travllerId);
            apiServiceFlight.editTravellerCompanion(traveller, new ApiServiceFlight.OnTravellerEdited() {
                @Override
                public void onEdited(Boolean result) {
                    progressBar1.setVisibility(View.GONE);
                    if (result)
                    fragmentManager.popBackStack();
                }
            });
        }

    }

    // get traveller companion data with bundle and initialize the views
    private void getBundle() {
        // if bundle has data,so this fragment is edit fragment
        if (bundle != null)
        {
            isEditFragment = true;
            firstnameEditText.setText(bundle.getString("firstName"));
            lastnameEditText.setText(bundle.getString("lastName"));
            dateOfBirthTextview.setText(bundle.getString("dateOfBirth"));
            nationalityTextview.setText(bundle.getString("nationality"));
            travllerId = bundle.getInt("id");
            if (bundle.getString("gender")!= null)
            switch (bundle.getString("gender"))
            {
                case "MALE":
                    genderToggleButton.setCheckedPosition(0);
                    break;
                case "FEMALE":
                    genderToggleButton.setCheckedPosition(1);
                    break;
            }
            // if traveller is iranian, set data for national code
            if (bundle.getBoolean("isIranian"))
            {
                isIranian = true;
                iraninanNationalCodeEditText.setText(bundle.getString("iranianCode"));
                passportEditText.setVisibility(View.INVISIBLE);
                passportTextview.setVisibility(View.INVISIBLE);
                iraninanNationalCodeEditText.setVisibility(View.VISIBLE);
                iraninanNationalCodeTextview.setVisibility(View.VISIBLE);
            }
            // if traveller is not iranian, set data for passport
            else
            {
                isIranian = false;
                passportEditText.setText(bundle.getString("passport"));
                passportEditText.setVisibility(View.VISIBLE);
                passportTextview.setVisibility(View.VISIBLE);
                iraninanNationalCodeEditText.setVisibility(View.INVISIBLE);
                iraninanNationalCodeTextview.setVisibility(View.INVISIBLE);
            }

        }
        // the bundle has no data,so this fragment is add fragment
        else {
            isEditFragment = false;
            titleTextview.setText("Add Traveller");
            deleteButton.setVisibility(View.GONE);
        }
    }

    // setup country list, if the user is iranian, he should complete iranian national code
    private void setupCountryList()
    {
        SimpleSearchDialogCompat<Searchable> simpleSearchDialogCompat =  new SimpleSearchDialogCompat(getContext(), "Search...",
                "What are you looking for...?", null, countryList,
                new SearchResultListener<SampleSearchModel>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat dialog, SampleSearchModel item, int position) {

                        if (item.getTitle().toUpperCase().contains("IRAN") || item.getTitle().contains("ایران") ||
                                (item.getTitle().toUpperCase().contains("IR") && !item.getTitle().toUpperCase().contains("IRAQ")))
                        {
                            isIranian = true;
                            passportTextview.setVisibility(View.INVISIBLE);
                            passportEditText.setVisibility(View.INVISIBLE);
                            iraninanNationalCodeEditText.setVisibility(View.VISIBLE);
                            iraninanNationalCodeTextview.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            isIranian = false;
                            passportEditText.setVisibility(View.VISIBLE);
                            passportTextview.setVisibility(View.VISIBLE);
                            iraninanNationalCodeEditText.setVisibility(View.INVISIBLE);
                            iraninanNationalCodeTextview.setVisibility(View.INVISIBLE);
                        }
                        nationalityTextview.setText( item.getTitle());
                        dialog.dismiss();
                    }
                });

        simpleSearchDialogCompat.show();
        final String s = simpleSearchDialogCompat.getTitleTextView().toString();
        Log.d(TAG, "setupSearchDialog: "+ s);
    }

    // get country list from server
    private void getCountryListApi(){
        apiServiceFlight.getCountryList(new ApiServiceFlight.OnCountriesRecieved() {
            @Override
            public void onRecieved(ArrayList<Country> countries) {
                if (countries != null)
                    for (int j = 0; j < countries.size(); j++) {
                        Country country = countries.get(j);
                        SampleSearchModel sampleSearchModel = new SampleSearchModel(country.getName() +" ("+ country.getCode()+") " );
                        countryList.add(sampleSearchModel);
                    }
            }
        });
    }

    private void init() {
        bundle = getArguments();
        fragmentManager = getFragmentManager();
        countryList = new ArrayList<>();
//        nationalityTextview = view.findViewById(R.id.tv_traveller_companion_nationality);
//        saveButton = view.findViewById(R.id.button_travellercompanion_edit_save);
//        firstnameEditText = view.findViewById(R.id.et_travellercompanion_edit_firstname);
//        lastnameEditText = view.findViewById(R.id.et_travellercompanion_edit_lastname);
//        titleTextview = view.findViewById(R.id.tv_travellercompanion_title);
//        deleteButton = view.findViewById(R.id.button_travellercompanions_edit_delete);
//        backImageview = view.findViewById(R.id.iv_travellercompanion_edit_back);
//        dateOfBirthTextview = view.findViewById(R.id.et_travellercompanion_edit_date_of_birth);
//        passportEditText = view.findViewById(R.id.et_travellercompanion_edit_passport_number);
//        iraninanNationalCodeEditText = view.findViewById(R.id.et_travellercompanion_edit_irainian_national_code);
//        genderToggleButton = view.findViewById(R.id.toggleSwitch_traveller_details);
//        countryCodePicker = view.findViewById(R.id.ccp_traveller_companion);
//        iraninanNationalCodeTextview = view.findViewById(R.id.tv_travellercompanion_edit_irainian_national_code);
//        passportTextview = view.findViewById(R.id.tv_travellercompanion_edit_passport_number);
//        progressBar1 = view.findViewById(R.id.progressBar1);
//        progressBar2 = view.findViewById(R.id.progressBar2);
//        genderToggleButton.setCheckedPosition(0);
//        ageClass = AgeClass.ADULT;
//        gender = Gender.MALE;
//        isIranian = true;
//        apiServiceFlight = new ApiServiceFlight(getContext());
//        ageClassToggleSwitch = view.findViewById(R.id.toggleSwitch_traveller_age_class);
        ageClassToggleSwitch.setCheckedPosition(0);
    }
}
