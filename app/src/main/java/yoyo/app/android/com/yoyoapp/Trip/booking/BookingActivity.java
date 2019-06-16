package yoyo.app.android.com.yoyoapp.Trip.booking;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import es.dmoral.toasty.Toasty;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.DataModels.Traveller;
import yoyo.app.android.com.yoyoapp.Flight.Booking.BookingSecondFragment;
import yoyo.app.android.com.yoyoapp.Flight.Booking.BookingThirdFragment;
import yoyo.app.android.com.yoyoapp.Flight.Utils.CheckInternetConnection;
import yoyo.app.android.com.yoyoapp.MainActivity;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.dialog.ExitDialogFragment;

import java.util.ArrayList;
import java.util.Date;

public class BookingActivity extends AppCompatActivity {

    private static final String TAG = "BookingActivity";
    private ImageView backImageview ,traverllerInfoImageview ,paymentImageview, successImageview , greenCheckImageview1 ,greenCheckImageview2;
    private TextView travellerInfoTextview ,paymentTextview, successTextview;
    public Button continueButton;
    public ArrayList<Traveller> travellers;
    private ProgressBar progressBar;
    public Date serverDeadlineDate, nowDate;
    private int whichFragment = 0, previousArraySize = 0;
    private FrameLayout frameLayout;
    private JSONObject jsonObject;
    private String scheduleId, bookingId;
    private BookingViewModel bookingViewModel;
    public ConstraintLayout constraintLayout;
    private BookingPresenter bookingPresenter;
    public String mobileNumberString, emailString, countryCode, mobileNumberCode,fullNameString;
    public MutableLiveData<Integer> passerngerNumLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking2);

        init();
        getScheduleId();
        Traveller traveller = new Traveller();
        travellers.add(traveller);
        setupFirstFragment();
        continueButton.setOnClickListener(v -> setupContinueButton());
        backImageview.setOnClickListener(v -> setupBackButton());


        passerngerNumLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer arraySize) {
                if (arraySize!=1)
                {
                    if (previousArraySize < arraySize)
                    {
                        Traveller traveller = new Traveller();
                        travellers.add(traveller);
                        Log.d(TAG, "onChangedtttttt:  addddd  " + travellers.size() +"          -----        " + arraySize);
                    }
                    else {
                        travellers.remove(travellers.size()-1);
                        Log.d(TAG, "onChangedtttttt:  reduce  " + travellers.size() +"          -----        " + arraySize);
                    }
                    previousArraySize = arraySize;
                }
            }
        });
    }

    private void getScheduleId() {
        Intent intent = getIntent();
        scheduleId = intent.getStringExtra("scheduleId");
        Log.d(TAG, "getScheduleId: " + scheduleId);
    }

    private void init() {
        frameLayout = findViewById(R.id.fl_booking);
        traverllerInfoImageview = findViewById(R.id.iv_booking_traveller_info);
        travellerInfoTextview = findViewById(R.id.tv_booking_traveller_info);
        continueButton = findViewById(R.id.button_booking_continue);
        paymentImageview = findViewById(R.id.iv_booking_payment);
        paymentTextview = findViewById(R.id.tv_booking_payment);
        greenCheckImageview1 = findViewById(R.id.iv_booking_green_check1);
        greenCheckImageview2 = findViewById(R.id.iv_booking_green_check2);
        successImageview = findViewById(R.id.iv_booking_success);
        successTextview = findViewById(R.id.tv_booking_success);
        progressBar = findViewById(R.id.progressbar_booking_book);
        constraintLayout = findViewById(R.id.cl_booking);
        travellers = new ArrayList<>();
        bookingPresenter = new BookingPresenter(BookingActivity.this);
        backImageview = findViewById(R.id.iv_sign_out_back);
        passerngerNumLiveData = new MutableLiveData<>();
        passerngerNumLiveData.postValue(1);
        bookingViewModel = ViewModelProviders.of(BookingActivity.this).get(BookingViewModel.class);
    }

    // setup first fragment and setup views for first fragment
    private void setupFirstFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        BookingFirstFragment bookingFirstFragment = new BookingFirstFragment();
        transaction.replace(R.id.fl_booking,bookingFirstFragment,"firstFragment");
        transaction.commit();

        travellerInfoTextview.setTextColor(getResources().getColor(R.color.blue));
        traverllerInfoImageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_traveller_info_blue_24dp));
    }


    // setup continue button for first, second and third fragment
    private void setupContinueButton() {

        progressBar.setVisibility(View.VISIBLE);
        switch (whichFragment)
        {
            case 0:
                boolean result = bookingPresenter.checkingEmptyItems(fullNameString,emailString,mobileNumberString);
                if (result)
                {
                    new CheckInternetConnection(BookingActivity.this, frameLayout, result1 -> {
                        if (result1)
                        {
                            bookFlight();
                        }
                    });
                }
                else
                {
                    Toasty.error(BookingActivity.this, getString(R.string.please_complete_all_fields)).show();
                }
                progressBar.setVisibility(View.GONE);
                break;
            case 1:

                //TODO: FAST FIX SOLUTION
                if ((serverDeadlineDate.getTime()+12600000 - nowDate.getTime()) > 0) {
                    new CheckInternetConnection(BookingActivity.this, frameLayout, new CheckInternetConnection.OnInternetConnected() {
                        @Override
                        public void onConnected(boolean result) {
                            if (result)
                            {
                                // payment request
                            }
                        }
                    });
                }
                else
                {
                    startActivity(new Intent(BookingActivity.this, MainActivity.class));
                }
                progressBar.setVisibility(View.GONE);
                break;
            case 2:
                startActivity(new Intent(BookingActivity.this, MainActivity.class));
                finish();
                progressBar.setVisibility(View.GONE);
                break;
        }
    }


    // book flight reuest for first fragment
    private void bookFlight() {

        setupJson();
        bookingViewModel.initBookingRequest(scheduleId, jsonObject);
        bookingViewModel.getBookingId().observe(BookingActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    bookingId = s;
                    Log.d(TAG, "onChanged: rtrtrt "+ bookingId);
                }
            }
        });
    }

    // book flight request and generate a json object
    public void setupJson() {
        jsonObject = new JSONObject();
        try {
            JSONObject leaderJsonObject = new JSONObject();
            leaderJsonObject.put("leader_traveller", fullNameString);
            leaderJsonObject.put("email",emailString);
            leaderJsonObject.put("phone_number",mobileNumberCode + mobileNumberString);

            jsonObject.put("leader_traveller",leaderJsonObject);
            JSONArray jsonArray = new JSONArray();
            for (Traveller item : travellers)
            {
                JSONObject js = new JSONObject();
                js.put("firstname",item.getFirstName());
                js.put("lastname",item.getLastName());
                js.put("gender",item.getGender());
                js.put("dob",item.getDateOfBirth());
                js.put("nationality",item.getNationality());
                js.put("national_code",item.getNationalityCode());
                js.put("passport_number",item.getPassportNumber());

                jsonArray.put(js);
            }
            jsonObject.put("companion_travellers",jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // setup second fragment and setup views for second fragment
    private void setupSecondFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_booking,new BookingSecondFragment(),"paymentFragment");
        transaction.addToBackStack("second booking fragment");
        transaction.commit();

        travellerInfoTextview.setTextColor(getResources().getColor(R.color.green));
        traverllerInfoImageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_traveller_info_green_24dp));
        greenCheckImageview1.setVisibility(View.VISIBLE);

        paymentTextview.setTextColor(getResources().getColor(R.color.blue));
        paymentImageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_payment_blue_24dp));
        continueButton.setBackground(getResources().getDrawable(R.drawable.green_rectangle));

        continueButton.setText("Pay");
        whichFragment = 1;
    }

    // setup third fragment and setup views for third fragment
    private void setupThirdFragment(Bundle bundle) {

        BookingThirdFragment bookingThirdFragment = new BookingThirdFragment();
        bookingThirdFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_booking,bookingThirdFragment,"suceessFragment");
        transaction.addToBackStack("third booking fragment");
        transaction.commit();

        travellerInfoTextview.setTextColor(getResources().getColor(R.color.green));
        traverllerInfoImageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_traveller_info_green_24dp));
        greenCheckImageview1.setVisibility(View.VISIBLE);

        paymentTextview.setTextColor(getResources().getColor(R.color.green));
        paymentImageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_payment_green_24dp));
        greenCheckImageview2.setVisibility(View.VISIBLE);

        successTextview.setTextColor(getResources().getColor(R.color.blue));
        successImageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_tick_primary_24dp));

        continueButton.setText(getString(R.string.back_to_main_page));
        continueButton.setBackground(getResources().getDrawable(R.drawable.green_rectangle));
        whichFragment = 2;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount()==0)
        {
            setupExit();
        }
        else {
            continueButton.setVisibility(View.VISIBLE);
            constraintLayout.setVisibility(View.VISIBLE);
            super.onBackPressed();
        }
    }

    private void setupBackButton() {
        setupExit();
    }

    // exit from booking activity
    private void setupExit() {
        ExitDialogFragment exitDialogFragment = new ExitDialogFragment();
        exitDialogFragment.show(getSupportFragmentManager(), "exit");
    }

}
