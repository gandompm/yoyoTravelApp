package yoyo.app.android.com.yoyoapp;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import yoyo.app.android.com.yoyoapp.Flight.Utils.LanguageSetup;
import yoyo.app.android.com.yoyoapp.Flight.Utils.UserSharedManagerFlight;
import yoyo.app.android.com.yoyoapp.Trip.Utils.Versioning;
import yoyo.app.android.com.yoyoapp.Trip.profile.profile.ProfileFragment;
import yoyo.app.android.com.yoyoapp.Trip.ticket.order.TourTicketFragment;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    public BottomNavigationView bottomNavigation;
    private LanguageSetup languageSetup;
    public static boolean isSingnedIn = false;
    public FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Uri data = this.getIntent().getData();
        if (data!=null) {
            checkingIfItIsFromPayment();
        }else {
            init();
            languageSetup.loadLanguageFromSharedPref();
            checkingSignIn();
            Versioning versioning = new Versioning();
            versioning.checkingUpdates(this);
            setupBottomNavigation();
        }
    }

    private void checkingIfItIsFromPayment() {
        Uri data = this.getIntent().getData();

            if (data.getPath().contains("orders"))
            {
                Log.d(TAG, "checkingIfItIsFromPayment: llllllll2");
                TourTicketFragment tourTicketFragment = new TourTicketFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("showTicket",true);
                tourTicketFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container,tourTicketFragment).addToBackStack("ticket");
                fragmentTransaction.commit();
            }else {
                init();
                languageSetup.loadLanguageFromSharedPref();
                checkingSignIn();
                Versioning versioning = new Versioning();
                versioning.checkingUpdates(this);
                setupBottomNavigation();
            }

    }



    private void init() {
        languageSetup = new LanguageSetup(this);
        bottomNavigation = findViewById(R.id.bn_main);
        frameLayout = findViewById(R.id.container);
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
        getSupportFragmentManager().popBackStack();
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

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() ==1)
        {
            float scale = getResources().getDisplayMetrics().density;
            int dpAsPixels = (int) (50 * scale + 0.5f);
            bottomNavigation.setVisibility(View.VISIBLE);
            frameLayout.setPadding(0, 0, 0, dpAsPixels);
            bottomNavigation.setVisibility(View.VISIBLE);
        }
        super.onBackPressed();
    }
}

