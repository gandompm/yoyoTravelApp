package yoyo.app.android.com.yoyoapp.Flight;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import yoyo.app.android.com.yoyoapp.Flight.BottomSheet.TravellerBottomSheetDialogFragment;
import yoyo.app.android.com.yoyoapp.Flight.FlightSearch.FlightSearchFragment;
import yoyo.app.android.com.yoyoapp.Flight.Utils.LanguageSetup;
import yoyo.app.android.com.yoyoapp.Flight.Utils.UserSharedManagerFlight;
import yoyo.app.android.com.yoyoapp.Flight.Utils.Versioning;
import yoyo.app.android.com.yoyoapp.MainActivity;
import yoyo.app.android.com.yoyoapp.R;
import java.util.ArrayList;
import java.util.Calendar;

public class MainFlightActivity extends AppCompatActivity implements TravellerBottomSheetDialogFragment.BotomSheetListener {

    private static final String TAG = "MainFlightActivity";
    public BottomNavigationView bottomNavigation;
    public int adultCount =1, childCount =0, infantCount =0, sum =1;
    public Calendar standardDate;
    public boolean isDateChanged = false;
    public ArrayList<String> iataCodeAirlines ;
    public ArrayList<String> idAircrafts;
    public ArrayList<String> dayTimes;
    private LanguageSetup languageSetup;
    private FlightSearchFragment flightSearchFragment;
    private ProfileFragment profileFragment;
    private TicketFragment ticketFragment;
    private boolean isSingnedIn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_flight);

        init();
        languageSetup.loadLanguageFromSharedPref();
        checkingSignIn();
        Versioning versioning = new Versioning();
        versioning.checkingUpdates(this);
        setupSeperateFragmentStack();

    }

    private void setupSeperateFragmentStack() {
        setupBottomNavigation();
    }

    // setup bottom navigation
    private void setupBottomNavigation() {
        bottomNavigation.setSelectedItemId(R.id.bn_flight);
        addFragment(new FlightSearchFragment(),"search");

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bn_flight:
                        startActivity(new Intent(MainFlightActivity.this, MainActivity.class));
                        return true;
                    case R.id.bn_profile:
                        addFragment(new ProfileFragment(),"profile");
                        return true;
                    case R.id.bn_orders:
                        if (isSingnedIn)
                            addFragment(new TicketFragment(),"ticket");
                        else
                            addFragment(new TicketNotSignedInFragment(), "ticket");
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    // setup fragment
    public void addFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_framelayout, fragment, tag);
        ft.addToBackStack(null);
        ft.commit();
    }

    // check if the user has before signed in or not
    private void checkingSignIn() {
        UserSharedManagerFlight userSharedManager = new UserSharedManagerFlight(MainFlightActivity.this);
        userSharedManager.getClient();
        if (!userSharedManager.getToken().equals(""))
        {
            isSingnedIn = true;
        }
    }

    private void init() {
        standardDate = Calendar.getInstance();
        bottomNavigation = findViewById(R.id.bn_main);
        languageSetup = new LanguageSetup(this);
        iataCodeAirlines = new ArrayList<>();
        idAircrafts =   new ArrayList<>();
        dayTimes = new ArrayList<>();
        flightSearchFragment = new FlightSearchFragment();
        profileFragment = new ProfileFragment();
        ticketFragment = new TicketFragment();
    }

    // override the interface method (the interface in TravellerBottomSheetDialogFragment)
    // this method runs when the user select the number of travellers for adult, child and infant
    @Override
    public void onButtonClicked(int sum, int adultNum, int childNum, int infantNum) {
        TextView travellerTextView = findViewById(R.id.tv_flight_travellers_num);
        TextView adultNumTextView = findViewById(R.id.tv_flight_adult_num);
        TextView childNumTextView = findViewById(R.id.tv_flight_child_num);
        TextView infantNumTextView = findViewById(R.id.tv_flight_infant_num);
        travellerTextView.setText(sum + " Traveler");
        adultNumTextView.setText(String.valueOf(adultNum));
        childNumTextView.setText(String.valueOf(childNum));
        infantNumTextView.setText(String.valueOf(infantNum));
        this.sum = sum;
        this.adultCount = adultNum;
        this.childCount = childNum;
        this.infantCount = infantNum;
    }


}
