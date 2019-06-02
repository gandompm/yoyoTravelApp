package yoyo.app.android.com.yoyoapp.Trip;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;
import es.dmoral.toasty.Toasty;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;
import yoyo.app.android.com.yoyoapp.Flight.ApiServiceFlight;
import yoyo.app.android.com.yoyoapp.Flight.Booking.BookingPresenter;
import yoyo.app.android.com.yoyoapp.Flight.Booking.BookingSecondFragment;
import yoyo.app.android.com.yoyoapp.Flight.Booking.BookingThirdFragment;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.Country;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.Traveller;
import yoyo.app.android.com.yoyoapp.Flight.Dialog.ExitDialogFragment;
import yoyo.app.android.com.yoyoapp.Flight.Dialog.MyTravellerDialogFragment;
import yoyo.app.android.com.yoyoapp.Flight.Enum.Gender;
import yoyo.app.android.com.yoyoapp.Flight.MainFlightActivity;
import yoyo.app.android.com.yoyoapp.Flight.SearchDialog.SampleSearchModel;
import yoyo.app.android.com.yoyoapp.Flight.Utils.CheckInternetConnection;
import yoyo.app.android.com.yoyoapp.Flight.Utils.DateCalenderSetup;
import yoyo.app.android.com.yoyoapp.Flight.Utils.NationalCodeUtil;
import yoyo.app.android.com.yoyoapp.R;

import java.util.ArrayList;
import java.util.Date;

public class BookingActivity2 extends AppCompatActivity {

    private static final String TAG = "BookingActivity";
    private ImageView backImageview ,paymentImageview, greenCheckImageview1 ,greenCheckImageview2 ;
    public  Button continueButton;
    public  ArrayList<Traveller> travellers;
    private int position;
    private ProgressBar progressBar;
    private int whichFragment=0;
    private Button selectTravellersButton, saveTravellerInfoButton;
    private boolean loadfirstFragments = true, isIranian;
    private DatePickerDialog.OnDateSetListener dateOfBirthListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_2);
//
//        init();
//        setupContinueButton();
//        setupBackButton();
//
//        getTravellersNum();
//        setupFirstFragment();
//        saveTravellerInfo();
//        setupSelectTravellerButton();
//        closeBottomSheet();
//        setupToggleButtons();
//        disableDragingbottomSheet();
//        setupCountryList();
//        getCountryListApi();
    }

//
//
//    private void init() {
//        saveTravellerInfoButton = findViewById(R.id.button_traveller_details_save);
//        continueButton = findViewById(R.id.button_booking_continue);
//        paymentImageview = findViewById(R.id.iv_booking_payment);
//        greenCheckImageview1 = findViewById(R.id.iv_booking_green_check1);
//        greenCheckImageview2 = findViewById(R.id.iv_booking_green_check2);
//        selectTravellersButton = findViewById(R.id.button_traveller_details_select_travellers);
//        progressBar = findViewById(R.id.progressbar_booking_book);
//        isIranian = false;
//        travellers = new ArrayList<>();
//        ConstraintLayout clBottomSheet = findViewById(R.id.cl_traveller_details);
//        backImageview = findViewById(R.id.iv_sign_out_back);
//    }
//
//
//
//   // get traveller number from previous fragment and
//   // initialize travellers array list
//    private ArrayList<Traveller> getTravellersNum() {
//
//        Intent intent = getIntent();
//        int adultNum = intent.getIntExtra("adultNum",1);
//        int childNum = intent.getIntExtra("childNum",0);
//        int infantNum = intent.getIntExtra("infantNum",0);
//
//        for (int i = 0; i < adultNum; i++) {
//            Traveller traveller = new Traveller();
//            traveller.setAgeClass("ADULT");
//            travellers.add(traveller);
//        }
//        for (int i = 0; i < childNum; i++) {
//            Traveller traveller = new Traveller();
//            traveller.setAgeClass("CHILD");
//            travellers.add(traveller);
//        }
//        for (int i = 0; i < infantNum; i++) {
//            Traveller traveller = new Traveller();
//            traveller.setAgeClass("INFANT");
//            travellers.add(traveller);
//        }
//        return travellers;
//    }
//
//    // setup first fragment and setup views for first fragment
//    private void setupFirstFragment() {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        yoyo.app.android.com.yoyoapp.Flight.Booking.BookingFirstFragment bookingFirstFragment = new yoyo.app.android.com.yoyoapp.Flight.Booking.BookingFirstFragment();
//        transaction.replace(R.id.fl_booking,bookingFirstFragment,"travellerInfoFragment");
//        transaction.commit();
//
//        travellerInfoTextview.setTextColor(getResources().getColor(R.color.blue));
//        traverllerInfoImageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_traveller_info_blue_24dp));
//    }
//
//    // save traveller and when the fields are not empty
//    // and are correct, add the traveller to travellers array
//    private void saveTravellerInfo() {
//
//        saveTravellerInfoButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Traveller traveller = travellers.get(position);
//                Boolean flag = true;
//
//
//                    if (firstnameEditText.getText().toString().equals("")){
//                        Toasty.error(BookingActivity2.this,getString(R.string.traveler)+ " " +  " " +getResources().getString(R.string.first_name_cannot_be_empty)).show();
//                        flag =false;
//                    }if (lastnameEditText.getText().toString().equals("")){
//                        Toasty.error(BookingActivity2.this,getString(R.string.traveler)+ " " +  " " +getResources().getString(R.string.last_name_cannot_be_empty)).show();
//                        flag =false;
//                    }
//
//
//                traveller.setFirstName(firstnameEditText.getText().toString());
//                traveller.setLastName(lastnameEditText.getText().toString());
//                traveller.setGender(gender.toString());
//                traveller.setIranian(isIranian);
//                traveller.setNationality(nationalityTextview.getText().toString());
//                if (traveller.isIranian())
//                {
//                    traveller.setIranianNationalCode(iranianCodeEditText.getText().toString());
//                    if ((iranianCodeEditText.getText().toString().equals(""))){
//                        Toasty.error(BookingActivity2.this,getString(R.string.traveler)+ " " + (position+1) +  " " +getString(R.string.iranian_code_not_empty)).show();
//                        flag =false;
//                    }
//                    NationalCodeUtil nationalCodeUtil = new NationalCodeUtil();
//                    if (!nationalCodeUtil.checkNationalCode(iranianCodeEditText.getText().toString()))
//                    {
//                        flag = false;
//                        Toasty.error(BookingActivity2.this,getString(R.string.iranina_national_code_not_valid)).show();
//                    }
//                }
//                else {
//                    traveller.setPassportNumber(passpotNumberEditText.getText().toString());
//                    if (passpotNumberEditText.getText().toString().equals("")){
//                        Toasty.error(BookingActivity2.this,getString(R.string.traveler)+ " " + (position+1) +  " " +getString(R.string.passport_not_empty)).show();
//                        flag =false;
//                    }
//                }
//
//                if (traveller.getAgeClass().equals("INFANT"))
//                {
//                    if (dateOfBirthTextview.getText().toString().equals("")){
//                        Toasty.error(BookingActivity2.this,getString(R.string.traveler)+ " " + (position+1) +  " " +getString(R.string.date_birth_can_not_empty)).show();
//                        flag =false;
//                    }
//                    traveller.setDateOfBirth(dateOfBirthTextview.getText().toString());
//                }
//
//                if (flag){
//                    travellers.set(position ,traveller);
//                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
//                    notifyFirstBookingFragment();
//                }
//            }
//        });
//    }
//
//    // close bottom sheet
//    private void closeBottomSheet()
//    {
//        closeBottomSheetImageview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
//            }
//        });
//    }
//
//    // select traveller from the existing user's travellers
//    private void setupSelectTravellerButton() {
//        selectTravellersButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MyTravellerDialogFragment myTravellerDialogFragment = new MyTravellerDialogFragment();
//                myTravellerDialogFragment.show(getSupportFragmentManager(), "some tag");
//            }
//        });
//    }
//
//    // expand bottom sheet
//    public void expandBottomSheet(Traveller traveller, int position) {
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//        this.position = position;
//        initializeViews(traveller);
//
//        // only if traveller is infant, visible date of birth field
//        if (travellers.get(position).getAgeClass().equals("INFANT"))
//        {
//            dateOfBirthGroup.setVisibility(View.VISIBLE);
//            new DateCalenderSetup(BookingActivity2.this, dateOfBirthTextview,dateOfBirthListner);
//        }
//        else
//        {
//            dateOfBirthGroup.setVisibility(View.GONE);
//        }
//    }
//
//    // initialize views for bottom sheet
//    public void initializeViews(Traveller traveller) {
//        firstnameEditText.setText(traveller.getFirstName());
//        lastnameEditText.setText(traveller.getLastName());
//        travllerDetailsTitle.setText(getString(R.string.traveler)+ " "+ (position + 1) +" " + (traveller.getAgeClass()));
//        iranianCodeEditText.setText(traveller.getIranianNationalCode());
//        passpotNumberEditText.setText(traveller.getPassportNumber());
//        nationalityTextview.setText(traveller.getNationality());
//        if (traveller.getGender() != null)
//        {
//            switch (traveller.getGender())
//            {
//                case "MALE":
//                    genderToggleSwitch.setCheckedPosition(0);
//                    gender = Gender.MALE;
//                    break;
//                case "FEMALE":
//                    genderToggleSwitch.setCheckedPosition(1);
//                    gender = Gender.FEMALE;
//                    break;
//            }
//        }
//        // if traveller is iranian, visible nationality code field
//            if (traveller.isIranian())
//            {
//                iranianCodeEditText.setText(traveller.getIranianNationalCode());
//                passportNumberTextview.setVisibility(View.GONE);
//                passpotNumberEditText.setVisibility(View.GONE);
//                iranianCodeEditText.setVisibility(View.VISIBLE);
//                iraninanCodeTextview.setVisibility(View.VISIBLE);
//                isIranian = true;
//            }
//            // if traveller is not iranian, visible passport field
//            else {
//                passpotNumberEditText.setText(traveller.getPassportNumber());
//                passportNumberTextview.setVisibility(View.VISIBLE);
//                passpotNumberEditText.setVisibility(View.VISIBLE);
//                iranianCodeEditText.setVisibility(View.GONE);
//                iraninanCodeTextview.setVisibility(View.GONE);
//                isIranian = false;
//            }
//    }
//
//    // access to first fragment class method (updateaddapter)
//    private void notifyFirstBookingFragment() {
//        firstFragment = (yoyo.app.android.com.yoyoapp.Flight.Booking.BookingFirstFragment) getSupportFragmentManager().findFragmentByTag("travellerInfoFragment");
//
//        // Check if the tab fragment is available
//        if (firstFragment != null) {
//
//            // Call your method in the TabFragment
//            firstFragment.updateaddapter();
//        }
//    }
//
//    // setup continue button for first, second and third fragment
//    private void setupContinueButton() {
//        continueButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressBar.setVisibility(View.VISIBLE);
//                switch (whichFragment)
//                {
//                     case 0:
//                         if (firstFragment!= null)
//                         {
//                             emailString = firstFragment.emailEditText.getText().toString();
//                             phoneNumberString = firstFragment.mobileNumber + firstFragment.mobilenumberEditText.getText().toString();
//                             Log.d(TAG, "onClick: asasasasasasasas  " + emailString + "  " + phoneNumberString);
//                         }
//                        int result = bookingPresenter.checkingEmptyItems(emailString,phoneNumberString,travellers);
//                        if (result == 200)
//                        {
//                            new CheckInternetConnection(BookingActivity2.this, frameLayout, new CheckInternetConnection.OnInternetConnected() {
//                                @Override
//                                public void onConnected(boolean result) {
//                                    if (result)
//                                    {
//                                        bookFlight();
//                                    }
//                                }
//                            });
//                        }
//                        else
//                        {
//                            Toasty.error(BookingActivity2.this, getString(R.string.please_complete_passernger)+ " " + (1 + result) + " " + getString(R.string.info)).show();
//                        }
//                         progressBar.setVisibility(View.GONE);
//                             break;
//                     case 1:
//
//                         //TODO: FAST FIX SOLUTION
//                         if ((serverDeadlineDate.getTime()+12600000 - nowDate.getTime()) > 0) {
//                             new CheckInternetConnection(BookingActivity2.this, frameLayout, new CheckInternetConnection.OnInternetConnected() {
//                                 @Override
//                                 public void onConnected(boolean result) {
//                                     if (result)
//                                     {
//                                         sendPaymentRequest();
//                                     }
//                                 }
//                             });
//                         }
//                         else
//                         {
//                             startActivity(new Intent(BookingActivity2.this, MainFlightActivity.class));
//                         }
//                         progressBar.setVisibility(View.GONE);
//                         break;
//                      case 2:
//                                startActivity(new Intent(BookingActivity2.this, MainFlightActivity.class));
//                                finish();
//                          progressBar.setVisibility(View.GONE);
//                              break;
//        }
//            }
//        });
//    }
//
//    // send payment request for second fragment and send user to the url
//    private void sendPaymentRequest() {
//        progressBar.setVisibility(View.VISIBLE);
//        bookingPresenter.sendPaymentRequest(voucherNumber, paymentUrl ->
//        {
//            Intent browserIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(paymentUrl));
//            startActivity(browserIntent);
//            progressBar.setVisibility(View.GONE);
//        });
//    }
//
//    // book flight reuest for first fragment
//    private void bookFlight() {
//
//        progressBar.setVisibility(View.VISIBLE);
//        bookingPresenter.bookFlight(emailString,phoneNumberString,travellers, message ->
//        {
//            if (message == null)
//            {
//                setupSecondFragment();
//            }
//            else
//            {
//                Toasty.error(BookingActivity2.this,message).show();
//            }
//            progressBar.setVisibility(View.GONE);
//        });
//    }
//
//    // setup second fragment and setup views for second fragment
//    private void setupSecondFragment() {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fl_booking,new BookingSecondFragment(),"paymentFragment");
//        transaction.addToBackStack("second booking fragment");
//        transaction.commit();
//
//        travellerInfoTextview.setTextColor(getResources().getColor(R.color.green));
//        traverllerInfoImageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_traveller_info_green_24dp));
//        greenCheckImageview1.setVisibility(View.VISIBLE);
//
//        paymentTextview.setTextColor(getResources().getColor(R.color.blue));
//        paymentImageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_payment_blue_24dp));
//        continueButton.setBackground(getResources().getDrawable(R.drawable.green_rectangle));
//
//        continueButton.setText("Pay");
//        whichFragment = 1;
//    }
//
//    // setup third fragment and setup views for third fragment
//    private void setupThirdFragment(Bundle bundle) {
//
//        loadfirstFragments = false;
//        BookingThirdFragment bookingThirdFragment = new BookingThirdFragment();
//        bookingThirdFragment.setArguments(bundle);
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fl_booking,bookingThirdFragment,"suceessFragment");
//        transaction.addToBackStack("third booking fragment");
//        transaction.commit();
//
//        travellerInfoTextview.setTextColor(getResources().getColor(R.color.green));
//        traverllerInfoImageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_traveller_info_green_24dp));
//        greenCheckImageview1.setVisibility(View.VISIBLE);
//
//        paymentTextview.setTextColor(getResources().getColor(R.color.green));
//        paymentImageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_payment_green_24dp));
//        greenCheckImageview2.setVisibility(View.VISIBLE);
//
//        successTextview.setTextColor(getResources().getColor(R.color.blue));
//        successImageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_tick_primary_24dp));
//
//        continueButton.setText(getString(R.string.back_to_main_page));
//        continueButton.setBackground(getResources().getDrawable(R.drawable.green_rectangle));
//        whichFragment = 2;
//    }
//
//    // show passport or national code for iranian and foreigner
//    private void setupCountryList()
//    {
//        nationalityTextview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SimpleSearchDialogCompat<Searchable> simpleSearchDialogCompat =  new SimpleSearchDialogCompat(BookingActivity2.this, "Search...",
//                        "What are you looking for...?", null, countryList,
//                        new SearchResultListener<SampleSearchModel>() {
//                            @Override
//                            public void onSelected(BaseSearchDialogCompat dialog, SampleSearchModel item, int position) {
//
//                                if (item.getTitle().toUpperCase().contains("IRAN") || item.getTitle().contains("ایران") ||
//                                        ( item.getTitle().toUpperCase().contains("IR") && !item.getTitle().toUpperCase().contains("IRAQ") ))
//                                {
//                                    isIranian = true;
//                                    passportNumberTextview.setVisibility(View.INVISIBLE);
//                                    passpotNumberEditText.setVisibility(View.INVISIBLE);
//                                    iranianCodeEditText.setVisibility(View.VISIBLE);
//                                    iraninanCodeTextview.setVisibility(View.VISIBLE);
//                                }
//                                else
//                                {
//                                    isIranian = false;
//                                    passpotNumberEditText.setVisibility(View.VISIBLE);
//                                    passportNumberTextview.setVisibility(View.VISIBLE);
//                                    iranianCodeEditText.setVisibility(View.INVISIBLE);
//                                    iraninanCodeTextview.setVisibility(View.INVISIBLE);
//                                }
//                                nationalityTextview.setText( item.getTitle());
//                                dialog.dismiss();
//                            }
//                        });
//
//                simpleSearchDialogCompat.show();
//            }
//        });
//    }
//
//    // get country list
//    private void getCountryListApi(){
//        apiServiceFlight.getCountryList(new ApiServiceFlight.OnCountriesRecieved() {
//            @Override
//            public void onRecieved(ArrayList<Country> countries) {
//                if (countries != null)
//                    for (int j = 0; j < countries.size(); j++) {
//                        Country country = countries.get(j);
//                        SampleSearchModel sampleSearchModel = new SampleSearchModel(country.getName() +" ("+ country.getCode()+") " );
//                        countryList.add(sampleSearchModel);
//                    }
//            }
//        });
//    }
//
//    @Override
//    public void onBackPressed() {
//        setupExit();
//    }
//
//    private void setupBackButton() {
//        backImageview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setupExit();
//            }
//        });
//    }
//
//    // exit from booking activity
//    private void setupExit() {
//        ExitDialogFragment exitDialogFragment = new ExitDialogFragment();
//        exitDialogFragment.show(getSupportFragmentManager(), "exit");
//    }

}
