package yoyo.app.android.com.yoyoapp.Trip.profile.travellerCompanion;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.hbb20.CountryCodePicker;
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;
import es.dmoral.toasty.Toasty;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;
import jp.gr.java_conf.androtaku.countrylist.CountryList;
import org.json.JSONException;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.DataModels.Traveller;
import yoyo.app.android.com.yoyoapp.Flight.ApiServiceFlight;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.Country;
import yoyo.app.android.com.yoyoapp.Flight.Enum.AgeClass;
import yoyo.app.android.com.yoyoapp.Flight.Enum.Gender;
import yoyo.app.android.com.yoyoapp.Flight.SearchDialog.SampleSearchModel;
import yoyo.app.android.com.yoyoapp.Flight.Utils.NationalCodeUtil;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.Utils.DateCalenderSetup;
import yoyo.app.android.com.yoyoapp.Trip.Utils.UserSharedManager;

import java.util.ArrayList;
import java.util.List;

public class TravellerCompanionsEditFragment extends Fragment {

    private static final String TAG = "TravellerCompanionsEdit";
    private EditText firstnameEditText, lastnameEditText;
    private EditText  iraninanNationalCodeEditText, passportEditText ;
    private TextView titleTextview, iraninanNationalCodeTextview, passportTextview , dateOfBirthTextview ,nationalityTextview;
    private ImageView backImageview;
    private FragmentManager fragmentManager;
    private Bundle bundle;
    private Button saveButton;
    private Button deleteButton;
    private String travllerId;
    private CountryCodePicker countryCodePicker;
    private Gender gender;
    private ProgressBar progressBar1, progressBar2;
    private ToggleSwitch genderToggleButton;
    private boolean isEditFragment , isIranian;
    private DatePickerDialog.OnDateSetListener dateOfBirthListner ,expiryPassportListner;
    private AgeClass ageClass;
    private long dateOfBirthTimestamp;
    private TravellerViewModel travellerViewModel;
    private ArrayList<SampleSearchModel> countryList;
    private UserSharedManager userSharedManager;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_traveller_companions_edit_trip, container, false);

        init();
        getBundle();


        saveButton.setOnClickListener(v -> saveTravellerCompanion());
        deleteButton.setOnClickListener(v -> deleteTravllerComapnion());
        backImageview.setOnClickListener(v -> getActivity().onBackPressed());
        nationalityTextview.setOnClickListener(v -> setupCountryList());
        dateOfBirthTextview.setOnClickListener(v -> setupDatePickers());
        getCountryList();
        setupToggleButton();

        return view;
    }

    private boolean checkEnglishChar() {
        if (isStringOnlyAlphabet(String.valueOf(firstnameEditText.getText())) && isStringOnlyAlphabet(String.valueOf(lastnameEditText.getText()))) {

            return true;

        }else {
            Toast.makeText(getContext(), "Please use english character", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    // setup date picker for user's birth date
    private void setupDatePickers() {
        new DateCalenderSetup(getContext() ,dateOfBirthListner, (timestamp, standardDate) ->
        {
            dateOfBirthTimestamp = timestamp;
            dateOfBirthTextview.setText(standardDate);
        });
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
    }

    // delete traveller
    private void deleteTravllerComapnion() {
        progressBar2.setVisibility(View.VISIBLE);
        travellerViewModel.initDeleteTraveller(travllerId);
        travellerViewModel.getDeleteResult().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                progressBar2.setVisibility(View.GONE);
                if (result) {
                    sendToPreviousPage();
                }
            }
        });
    }

    private void sendToPreviousPage() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,new TravellerCompanionFragment());
        fragmentTransaction.commit();
    }

    // save traveller, check errors
    private void saveTravellerCompanion() {
        Traveller traveller = new Traveller();
        int error = 0;

        if (!checkEnglishChar()){
            return;
        }


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
            traveller.setDateOfBirthTimeStamp(dateOfBirthTimestamp);
        }

        if (nationalityTextview.getText().toString().equals(""))
        {
            nationalityTextview.setError(getString(R.string.please_enter_nationality));
            error = 4;
        }
        else
        {
            String nationalityName = nationalityTextview.getText().toString();
            String code = CountryList.convertNameToCode(getContext(), nationalityName);
            traveller.setNationality(code);
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
            JSONObject jsonObject = convertTravellerToJson(traveller);
            progressBar1.setVisibility(View.VISIBLE);

            travellerViewModel.initAddTraveller(jsonObject);
            travellerViewModel.getIsTravellerAdded().observe(getActivity(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean result) {
                    if (result)
                    {
                        progressBar1.setVisibility(View.GONE);
                        if (result)
                            sendToPreviousPage();
                    }
                }
            });
        }

        // this fragment is edit fragment and no error has occurred
        else if (error == 0)
        {
            JSONObject jsonObject = convertTravellerToJson(traveller);
            progressBar1.setVisibility(View.VISIBLE);
            travellerViewModel.initEditTraveller(travllerId, jsonObject);
            travellerViewModel.getEditedResult().observe(getActivity(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean result) {
                    progressBar1.setVisibility(View.GONE);
                    if (result)
                        sendToPreviousPage();
                }
            });
        }

    }

    private JSONObject convertTravellerToJson(Traveller traveller) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("firstname",traveller.getFirstName());
            jsonObject.put("lastname",traveller.getLastName());
            jsonObject.put("gender",traveller.getGender());
            jsonObject.put("dob", traveller.getDateOfBirthTimeStamp());
            jsonObject.put("nationality",traveller.getNationality());

            if (traveller.isIranian())
            {
                jsonObject.put("national_code",traveller.getIranianNationalCode());
                jsonObject.put("passport_number","");
            }
            else
            {
                jsonObject.put("passport_number",traveller.getPassportNumber());
                jsonObject.put("national_code","");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
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
            dateOfBirthTimestamp = bundle.getLong("dateOfBirthTimeStamp");
            nationalityTextview.setText(bundle.getString("nationality"));
            travllerId = bundle.getString("id");
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
    private void getCountryList(){
        List<String> countryNames = CountryList.getCountryNames(getContext());
        for (int j = 0; j < countryNames.size(); j++) {
            String name = countryNames.get(j);
            SampleSearchModel sampleSearchModel = new SampleSearchModel(name);
            countryList.add(sampleSearchModel);
        }
    }

    private void init() {
        travellerViewModel = ViewModelProviders.of(getActivity()).get(TravellerViewModel.class);
        userSharedManager = new UserSharedManager(getContext());
        bundle = getArguments();
        fragmentManager = getFragmentManager();
        countryList = new ArrayList<>();
        nationalityTextview = view.findViewById(R.id.tv_traveller_companion_nationality);
        saveButton = view.findViewById(R.id.button_travellercompanion_edit_save);
        firstnameEditText = view.findViewById(R.id.et_travellercompanion_edit_firstname);
        lastnameEditText = view.findViewById(R.id.et_travellercompanion_edit_lastname);
        titleTextview = view.findViewById(R.id.tv_travellercompanion_title);
        deleteButton = view.findViewById(R.id.button_travellercompanions_edit_delete);
        backImageview = view.findViewById(R.id.iv_travellercompanion_edit_back);
        dateOfBirthTextview = view.findViewById(R.id.et_travellercompanion_edit_date_of_birth);
        passportEditText = view.findViewById(R.id.et_travellercompanion_edit_passport_number);
        iraninanNationalCodeEditText = view.findViewById(R.id.et_travellercompanion_edit_irainian_national_code);
        genderToggleButton = view.findViewById(R.id.toggleSwitch_traveller_details);
        countryCodePicker = view.findViewById(R.id.ccp_traveller_companion);
        iraninanNationalCodeTextview = view.findViewById(R.id.tv_travellercompanion_edit_irainian_national_code);
        passportTextview = view.findViewById(R.id.tv_travellercompanion_edit_passport_number);
        progressBar1 = view.findViewById(R.id.progressBar1);
        progressBar2 = view.findViewById(R.id.progressBar2);
        genderToggleButton.setCheckedPosition(0);
        ageClass = AgeClass.ADULT;
        gender = Gender.MALE;
        isIranian = true;
    }


    private boolean isStringOnlyAlphabet(String str)
    {
        return ((!str.equals(""))
                && (str != null)
                && (str.matches("^[a-zA-Z]*$")));
    }
}
