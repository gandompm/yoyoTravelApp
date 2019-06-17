package yoyo.app.android.com.yoyoapp;

import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import yoyo.app.android.com.yoyoapp.Flight.FlightTicketFragment;
import yoyo.app.android.com.yoyoapp.Flight.FlightTicketNotSignedInFragment;
import yoyo.app.android.com.yoyoapp.Flight.Utils.LanguageSetup;
import yoyo.app.android.com.yoyoapp.Flight.Utils.UserSharedManagerFlight;
import yoyo.app.android.com.yoyoapp.Trip.Utils.Versioning;
import yoyo.app.android.com.yoyoapp.Trip.profile.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity{

    public BottomNavigationView bottomNavigation;
    private LanguageSetup languageSetup;
    public static boolean isSingnedIn = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getBundle();
        init();
        languageSetup.loadLanguageFromSharedPref();
        checkingSignIn();
        Versioning versioning = new Versioning();
        versioning.checkingUpdates(this);
        setupBottomNavigation();
    }

    private void getBundle() {
//        if (getIntent().getBooleanExtra(Utils.KEY_BUNDLE_MAINACTIVITY,false))
//        {
//            addFragment(new ProfileFragment(),"profile");
//        }
    }


    private void init() {
        languageSetup = new LanguageSetup(this);
        bottomNavigation = findViewById(R.id.bn_main);
    }


    private void setupBottomNavigation() {

        addFragment(new MainPageFragment(),"mainpage");
        bottomNavigation.setSelectedItemId(R.id.bn_home);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bn_home:
                        addFragment(new MainPageFragment(),"mainpage");
                        return true;
                    case R.id.bn_profile:
                        addFragment(new ProfileFragment(),"profile");
                        return true;
                    case R.id.bn_orders:
                        addFragment(new OrdersFragment(),"orders");
                        return true;


//                        if (isSingnedIn)
//                            addFragment(new FlightTicketFragment(),"ticket");
//                        else
//                            addFragment(new FlightTicketNotSignedInFragment(), "ticket");
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
        ft.replace(R.id.container, fragment, tag);
        ft.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }


    // check if the user has before signed in or not
    private void checkingSignIn() {
        UserSharedManagerFlight userSharedManager = new UserSharedManagerFlight(MainActivity.this);
        userSharedManager.getClient();
        if (!userSharedManager.getToken().equals(""))
        {
            isSingnedIn = true;
        }
    }

}

