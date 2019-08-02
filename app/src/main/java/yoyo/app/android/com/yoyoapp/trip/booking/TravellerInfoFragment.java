package yoyo.app.android.com.yoyoapp.trip.booking;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentTransaction;
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;
import es.dmoral.toasty.Toasty;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;
import jp.gr.java_conf.androtaku.countrylist.CountryList;
import yoyo.app.android.com.yoyoapp.DataModels.Traveller;
import yoyo.app.android.com.yoyoapp.Flight.Enum.Gender;
import yoyo.app.android.com.yoyoapp.Flight.SearchDialog.SampleSearchModel;
import yoyo.app.android.com.yoyoapp.Flight.Utils.NationalCodeUtil;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.trip.Utils.DateCalenderSetup;

import java.util.ArrayList;
import java.util.List;

public class TravellerInfoFragment extends Fragment {

    private Group dateOfBirthGroup;
    private EditText firstnameEditText, lastnameEditText, iranianCodeEditText, passpotNumberEditText;
    private Button selectTravellersButton, saveTravellerInfoButton;
    private DatePickerDialog.OnDateSetListener dateOfBirthListner;
    private ImageView closeBottomSheetImageview;
    private ArrayList<SampleSearchModel> countryList;
    private TextView iraninanCodeTextview, passportNumberTextview , travllerDetailsTitle, dateOfBirthTextview,nationalityTextview;
    private ToggleSwitch genderToggleSwitch;
    private Traveller traveller;
    private BookingFirstFragment firstFragment;
    private Gender gender;
    private boolean isIranian;
    private int position;
    public View view;
    private Long dateOfBirthTimestamp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_traveller_info, container, false);

        init();
        getBundle();
        setupToggleButtons();
        saveTravellerInfoButton.setOnClickListener(v -> saveTravellerInfo());
        setupSelectTravellerButton();
        getCountryList();
        nationalityTextview.setOnClickListener(v -> setupCountryList());
        closeBottomSheetImageview.setOnClickListener(v-> setupBackButton());
        dateOfBirthTextview.setOnClickListener(v -> setupCalender());

        return view;
    }

    private void setupCalender() {
        new DateCalenderSetup(getContext() ,dateOfBirthListner, (timestamp, standardDate) ->
        {
            dateOfBirthTimestamp = timestamp;
            dateOfBirthTextview.setText(standardDate);
        });
    }


    private boolean checkEnglishChar() {
        if (isStringOnlyAlphabet(String.valueOf(firstnameEditText.getText())) && isStringOnlyAlphabet(String.valueOf(lastnameEditText.getText()))) {
            return true;
        }else {
            Toast.makeText(getContext(), "Please use english character", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean isStringOnlyAlphabet(String str)
    {
        return ((!str.equals(""))
                && (str != null)
                && (str.matches("^[a-zA-Z]*$")));
    }

    private void setupBackButton() {
        ((BookingActivity) getActivity()).getContinueButton().setVisibility(View.VISIBLE);
        ((BookingActivity) getActivity()).getConstraintLayout().setVisibility(View.VISIBLE);
        getFragmentManager().popBackStack();
    }

    private void getBundle() {
        Bundle bundle = getArguments();
        traveller = new Traveller();
        if (bundle != null)
        {
            position = bundle.getInt("position");
            travllerDetailsTitle.setText("Traveller "+ (position+1));
            traveller = ((BookingActivity) getActivity()).getTravellers().get(position);
        }
        setupView(traveller,position);
    }

    private void init() {
        firstnameEditText = view.findViewById(R.id.et_traveller_details_firstname);
        lastnameEditText = view.findViewById(R.id.et_traveller_details_lastname);
        saveTravellerInfoButton = view.findViewById(R.id.button_traveller_details_save);
        closeBottomSheetImageview = view.findViewById(R.id.iv_travellers_details_close);
        selectTravellersButton = view.findViewById(R.id.button_traveller_details_select_travellers);
        genderToggleSwitch = view.findViewById(R.id.toggleSwitch_traveller_details);
        dateOfBirthTextview = view.findViewById(R.id.et_traveller_details_dateofbirth);
        dateOfBirthGroup = view.findViewById(R.id.group_date_of_birth);
        iranianCodeEditText = view.findViewById(R.id.et_traveller_details_iranian_nationality_code);
        iraninanCodeTextview = view.findViewById(R.id.tv_traveller_details_iranian_code);
        passportNumberTextview = view.findViewById(R.id.tv_traveller_details_passport_number);
        passpotNumberEditText = view.findViewById(R.id.et_traveller_details_passport_number);
        travllerDetailsTitle = view.findViewById(R.id.tv_traveller_details_title);
        nationalityTextview = view.findViewById(R.id.tv_traveller_details_nationality);
        ((BookingActivity) getActivity()).getContinueButton().setVisibility(View.GONE);
        ((BookingActivity) getActivity()).getConstraintLayout().setVisibility(View.GONE);
        genderToggleSwitch.setCheckedPosition(0);
        gender = Gender.MALE;
        isIranian = false;
        countryList = new ArrayList<>();
    }

    // setup toggle button for gender
    private void setupToggleButtons() {
        genderToggleSwitch.setOnChangeListener(new ToggleSwitch.OnChangeListener() {
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

    // save traveller and when the fields are not empty
    // and are correct, add the traveller to travellers array
    private void saveTravellerInfo() {
        Boolean flag = true;

        if (!checkEnglishChar()){
            return;
        }
            if (firstnameEditText.getText().toString().equals("")){
                Toasty.error(getContext(),getString(R.string.traveler)+ " " +  " " +getResources().getString(R.string.first_name_cannot_be_empty)).show();
                flag =false;
            }if (lastnameEditText.getText().toString().equals("")){
                Toasty.error(getContext(),getString(R.string.traveler)+ " " +  " " +getResources().getString(R.string.last_name_cannot_be_empty)).show();
                flag =false;
            }

            traveller.setFirstName(firstnameEditText.getText().toString());
            traveller.setLastName(lastnameEditText.getText().toString());
            traveller.setGender(gender.toString());
            traveller.setIranian(isIranian);






        if (traveller.isIranian())
            {
                traveller.setIranianNationalCode(iranianCodeEditText.getText().toString());
                if ((iranianCodeEditText.getText().toString().equals(""))){
                    Toasty.error(getContext(),getString(R.string.traveler)+ " " + (position+1) +  " " +getString(R.string.iranian_code_not_empty)).show();
                    flag =false;
                }
                NationalCodeUtil nationalCodeUtil = new NationalCodeUtil();
                if (!nationalCodeUtil.checkNationalCode(iranianCodeEditText.getText().toString()))
                {
                    flag = false;
                    Toasty.error(getContext(),getString(R.string.iranina_national_code_not_valid)).show();
                }
            }
            else {
                traveller.setPassportNumber(passpotNumberEditText.getText().toString());
                if (passpotNumberEditText.getText().toString().equals("")){
                    Toasty.error(getContext(),getString(R.string.traveler)+ " " + (position+1) +  " " +getString(R.string.passport_not_empty)).show();
                    flag =false;
                }
            }

            if (dateOfBirthTextview.getText().toString().equals("")){
                Toasty.error(getContext(),getString(R.string.traveler)+ " " + (position+1) +  " " +getString(R.string.date_birth_can_not_empty)).show();
                flag =false;
            }
            traveller.setDateOfBirth(dateOfBirthTextview.getText().toString());
            traveller.setDateOfBirthTimeStamp(dateOfBirthTimestamp);

            if (TextUtils.isEmpty(nationalityTextview.getText()))
            {
                Toasty.error(getContext(),"Nationality Field is empty").show();
                flag = false;
            }
            String code = CountryList.convertNameToCode(getContext(), nationalityTextview.getText().toString());
            traveller.setNationality(code);

            if (flag){
                ((BookingActivity) getActivity()).getTravellers().set(position ,traveller);
                sendToPreviousPage();
            }
    }

    private void sendToPreviousPage() {
        ((BookingActivity) getActivity()).getContinueButton().setVisibility(View.VISIBLE);
        ((BookingActivity) getActivity()).getConstraintLayout().setVisibility(View.VISIBLE);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_booking,new BookingFirstFragment(),"firstFragment");
        fragmentTransaction.commit();
    }


    // select traveller from the existing user's travellers
    private void setupSelectTravellerButton() {
        selectTravellersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTravellerDialogFragment myTravellerDialogFragment = new MyTravellerDialogFragment(selectedTraveller ->{
                    setupView(selectedTraveller,position);
                });
                myTravellerDialogFragment.show(getFragmentManager(), "select your traveller");
            }
        });
    }

    // expand bottom sheet
    public void setupView(Traveller traveller, int position) {
        this.position = position;

        firstnameEditText.setText(traveller.getFirstName());
        lastnameEditText.setText(traveller.getLastName());
        iranianCodeEditText.setText(traveller.getIranianNationalCode());
        passpotNumberEditText.setText(traveller.getPassportNumber());
        nationalityTextview.setText(traveller.getNationality());
        dateOfBirthTextview.setText(traveller.getDateOfBirth());
        dateOfBirthTimestamp = traveller.getDateOfBirthTimeStamp();
        if (traveller.getGender() != null)
        {
            switch (traveller.getGender())
            {
                case "MALE":
                    genderToggleSwitch.setCheckedPosition(0);
                    gender = Gender.MALE;
                    break;
                case "FEMALE":
                    genderToggleSwitch.setCheckedPosition(1);
                    gender = Gender.FEMALE;
                    break;
            }
        }
        // if traveller is iranian, visible nationality code field
        if (traveller.isIranian())
        {
            iranianCodeEditText.setText(traveller.getIranianNationalCode());
            passportNumberTextview.setVisibility(View.GONE);
            passpotNumberEditText.setVisibility(View.GONE);
            iranianCodeEditText.setVisibility(View.VISIBLE);
            iraninanCodeTextview.setVisibility(View.VISIBLE);
            isIranian = true;
        }
        // if traveller is not iranian, visible passport field
        else {
            passpotNumberEditText.setText(traveller.getPassportNumber());
            passportNumberTextview.setVisibility(View.VISIBLE);
            passpotNumberEditText.setVisibility(View.VISIBLE);
            iranianCodeEditText.setVisibility(View.GONE);
            iraninanCodeTextview.setVisibility(View.GONE);
            isIranian = false;
        }
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


    // show passport or national code for iranian and foreigner
    private void setupCountryList()
    {
            SimpleSearchDialogCompat<Searchable> simpleSearchDialogCompat =  new SimpleSearchDialogCompat(getContext(), "Search...",
                    "What are you looking for...?", null, countryList,
                    new SearchResultListener<SampleSearchModel>() {
                        @Override
                        public void onSelected(BaseSearchDialogCompat dialog, SampleSearchModel item, int position) {

                            if (item.getTitle().toUpperCase().contains("IRAN") || item.getTitle().contains("ایران") ||
                                    ( item.getTitle().toUpperCase().contains("IR") && !item.getTitle().toUpperCase().contains("IRAQ") ))
                            {
                                isIranian = true;
                                passportNumberTextview.setVisibility(View.INVISIBLE);
                                passpotNumberEditText.setVisibility(View.INVISIBLE);
                                iranianCodeEditText.setVisibility(View.VISIBLE);
                                iraninanCodeTextview.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                isIranian = false;
                                passpotNumberEditText.setVisibility(View.VISIBLE);
                                passportNumberTextview.setVisibility(View.VISIBLE);
                                iranianCodeEditText.setVisibility(View.INVISIBLE);
                                iraninanCodeTextview.setVisibility(View.INVISIBLE);
                            }
                            nationalityTextview.setText( item.getTitle());
                            dialog.dismiss();
                        }
                    });
            simpleSearchDialogCompat.show();
    }
}
