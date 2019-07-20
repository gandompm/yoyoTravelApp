package yoyo.app.android.com.yoyoapp.trip.booking;

import android.content.Intent;
import android.net.Uri;
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
import yoyo.app.android.com.yoyoapp.Flight.Booking.BookingThirdFragment;
import yoyo.app.android.com.yoyoapp.Flight.Utils.CheckInternetConnection;
import yoyo.app.android.com.yoyoapp.MainActivity;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.trip.dialog.ExitDialogFragment;

import java.util.ArrayList;
import java.util.Date;

public class BookingActivity extends AppCompatActivity {

    private static final String TAG = "BookingActivity";
    private ImageView backImageview, traverllerInfoImageview, paymentImageview, successImageview, greenCheckImageview1, greenCheckImageview2;
    private TextView travellerInfoTextview, paymentTextview, successTextview;
    public Button continueButton;
    public ArrayList<Traveller> travellers;
    private ProgressBar progressBar;
    public Date serverDeadlineDate, nowDate;
    private int whichFragment = 0;
    public int minCapacity = 1;
    private double price;
    private FrameLayout frameLayout;
    private JSONObject jsonObject;
    private String scheduleId, url;
    private BookingViewModel bookingViewModel;
    public ConstraintLayout constraintLayout;
    public String mobileNumberString, emailString, countryCode, mobileNumberCode, fullNameString;
    public MutableLiveData<Boolean> passerngerNumLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking2);

        init();
        getBundle();
        for (int i = 0; i < minCapacity; i++) {
            Traveller traveller = new Traveller();
            travellers.add(traveller);
        }
        setupFirstFragment();
        continueButton.setOnClickListener(v -> setupContinueButton());
        backImageview.setOnClickListener(v -> setupBackButton());


        passerngerNumLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isNumberAdded) {
                if (isNumberAdded) {
                    Traveller traveller = new Traveller();
                    travellers.add(traveller);
                } else {
                    travellers.remove(travellers.size() - 1);
                }
            }
        });
    }

    private void getBundle() {
        Intent intent = getIntent();
        scheduleId = intent.getStringExtra("scheduleId");
        price = intent.getDoubleExtra("price", 0);
        minCapacity = intent.getIntExtra("minCapacity",1);
        Log.d(TAG, "getBundle: " + scheduleId);
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
        backImageview = findViewById(R.id.iv_sign_out_back);
        passerngerNumLiveData = new MutableLiveData<>();
        bookingViewModel = ViewModelProviders.of(BookingActivity.this).get(BookingViewModel.class);
    }

    // setup first fragment and setup views for first fragment
    private void setupFirstFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        BookingFirstFragment bookingFirstFragment = new BookingFirstFragment();
        transaction.replace(R.id.fl_booking, bookingFirstFragment, "firstFragment");
        transaction.commit();

        travellerInfoTextview.setTextColor(getResources().getColor(R.color.blue));
        traverllerInfoImageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_traveller_info_blue_24dp));
    }


    // setup continue button for first, second and third fragment
    private void setupContinueButton() {

        progressBar.setVisibility(View.VISIBLE);
        switch (whichFragment) {
            case 0:
                boolean result = checkingEmptyItems(fullNameString, emailString, mobileNumberString,travellers);
                if (result) {
                    if (!emailString.matches("^[A-Za-z0-9_.]+[@][A-Za-z.]+$")) {
                        Toasty.error(this, "Your Email Address is incorrect").show();
                        progressBar.setVisibility(View.GONE);
                        return;
                    }
                    new CheckInternetConnection(BookingActivity.this, frameLayout, result1 -> {
                        if (result1) {
                            bookFlight();
                        }
                    });
                } else if (!result) {
                    Toasty.error(BookingActivity.this, getString(R.string.please_complete_all_fields)).show();
                } else {
                    Toasty.error(this, "Your Email Address is incorrect").show();
                }
                progressBar.setVisibility(View.GONE);
                break;
            case 1:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                progressBar.setVisibility(View.GONE);
                break;
            case 2:
                startActivity(new Intent(BookingActivity.this, MainActivity.class));
                finish();
                progressBar.setVisibility(View.GONE);
                break;
        }
    }

    public boolean checkingEmptyItems(String fullNameString, String emailString, String phoneNumberString, ArrayList<Traveller> travellers) {
        if (fullNameString == null || fullNameString.equals(""))
        {
            Toasty.error(this,"full name can not be empty").show();
            return false;
        }
        if (emailString == null || emailString.equals(""))
        {
            Toasty.error(this,getResources().getString(R.string.email_cannot_be_empty)).show();
            return false;
        }
        if (phoneNumberString == null || phoneNumberString.equals(""))
        {
            Toasty.error(this,getResources().getString(R.string.phone_number_cannot_be_empty)).show();
            return false;
        }
        for (int i = 0; i < travellers.size(); i++) {
            if (travellers.get(i).getFirstName() == null) {
                Toasty.error(this,"Please complete all traveller's data").show();
                return false;
            }
        }
        return true;
    }


    // book flight reuest for first fragment
    private void bookFlight() {
        setupJson();
        bookingViewModel.initBookingRequest(scheduleId, jsonObject);
        bookingViewModel.getBookingId().observe(BookingActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String newurl) {
                if (newurl != null) {
                    Bundle bundle = new Bundle();
                    url = newurl;
                    bundle.putString("name", fullNameString);
                    bundle.putInt("travellerNumber", travellers.size());
                    bundle.putDouble("price", price);

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    BookingSecondFragment bookingSecondFragment = new BookingSecondFragment();
                    bookingSecondFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.fl_booking, bookingSecondFragment);
                    fragmentTransaction.addToBackStack("bookingsecond");
                    fragmentTransaction.commit();
                    setupSecondFragment();
                }
            }
        });
    }

    // book flight request and generate a json object
    public void setupJson() {
        jsonObject = new JSONObject();
        try {
            JSONObject leaderJsonObject = new JSONObject();
            leaderJsonObject.put("fullname", fullNameString);
            leaderJsonObject.put("email", emailString);
            leaderJsonObject.put("phone_number", mobileNumberCode + mobileNumberString);

            jsonObject.put("leader_traveller", leaderJsonObject);
            JSONArray jsonArray = new JSONArray();
            for (Traveller item : travellers) {
                JSONObject js = new JSONObject();
                js.put("firstname", item.getFirstName());
                js.put("lastname", item.getLastName());
                js.put("gender", item.getGender());
                js.put("dob", item.getDateOfBirthTimeStamp());
                js.put("nationality", item.getNationality());
                js.put("national_code", item.getNationalityCode());
                js.put("passport_number", item.getPassportNumber());

                jsonArray.put(js);
            }
            jsonObject.put("companion_travellers", jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // setup second fragment and setup views for second fragment
    private void setupSecondFragment() {
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
        transaction.replace(R.id.fl_booking, bookingThirdFragment, "suceessFragment");
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
            setupExit();
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
