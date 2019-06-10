package yoyo.app.android.com.yoyoapp.Trip;


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
import yoyo.app.android.com.yoyoapp.Trip.dialog.CategotyFilterBottomSheetDialogFragment;
import yoyo.app.android.com.yoyoapp.Trip.dialog.PriceFilterBottomSheetDialogFragment;
import yoyo.app.android.com.yoyoapp.Flight.TicketFragment;
import yoyo.app.android.com.yoyoapp.Flight.TicketNotSignedInFragment;
import yoyo.app.android.com.yoyoapp.MainActivity;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.profile.profile.ProfileFragment;
import yoyo.app.android.com.yoyoapp.Trip.search.TripSearchFragment;
import yoyo.app.android.com.yoyoapp.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TripActivity extends AppCompatActivity implements PriceFilterBottomSheetDialogFragment.BotomSheetListener, CategotyFilterBottomSheetDialogFragment.BotomSheetListener {

    public BottomNavigationView bottomNavigation;
    public int fromPrice = 0, toPrice = 20000000;
    public long fromTime = 1158742400, toTime = 1959088000;
    public int minDuration = 1;
    public ArrayList<String> categories;
    public String location = "";
    public int diffDays = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        init();
        setupBottomNavigation();

    }

    private void init() {
        bottomNavigation = findViewById(R.id.bn_main);
        categories = new ArrayList<>();
    }


    private void setupBottomNavigation() {

        sendingToSearchFragment();
        bottomNavigation.setSelectedItemId(R.id.bn_home);

        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bn_home:
                    finish();
                    overridePendingTransition(0,  0);
                    return true;
                case R.id.bn_profile:
                    addFragment(new ProfileFragment(),"profile");
                    return true;
                case R.id.bn_orders:
                    if (MainActivity.isSingnedIn)
                        addFragment(new TicketFragment(),"ticket");
                    else
                        addFragment(new TicketNotSignedInFragment(), "ticket not signed in");
                    return true;
                default:
                    return false;
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
    public void onApplyClicked(String min, String max) {
         TextView filterPriceTextview = findViewById(R.id.tv_search_price_filter);
         DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
         String minimum = decimalFormat.format(Integer.valueOf(min));
         String maximum = decimalFormat.format(Integer.valueOf(max));
         filterPriceTextview.setText("From " + minimum + " to "+ maximum  +"$");
         if (!min.equals("10") || !max.equals("5500"))
         {
             fromPrice = Integer.parseInt(min);
             toPrice = Integer.parseInt(max);
         }
         else
             filterPriceTextview.setText(getResources().getString(R.string.filter_by_price_optional));
    }

    private void sendingToSearchFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(Utils.KEY_BUNDLE_MAIN_PAGE_CODE,"trip");

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        TripSearchFragment tripSearchFragment = new TripSearchFragment();
        tripSearchFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.container, tripSearchFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount()==0)
        {
            finish();
            overridePendingTransition(0,  0);
        }
        else if (getSupportFragmentManager().getBackStackEntryCount() ==1)
        {
            // clear filter parameters
            minDuration =1;
            categories.clear();
            super.onBackPressed();
        }
        else
            super.onBackPressed();
    }

    @Override
    public void onApplyClicked(ArrayList<String> catNames) {
        StringBuilder catString = new StringBuilder();
        for (String catName: catNames) {
            catString.append(catName + "• ");
        }
        TextView categoryTextview = findViewById(R.id.tv_search_type);
        categoryTextview.setText(catString);
        categories = catNames;
        if (catNames.size()==0)
            categoryTextview.setText(getResources().getString(R.string.choose_tour_type));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}

