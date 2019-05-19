package yoyo.app.android.com.yoyoapp.Flight;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import yoyo.app.android.com.yoyoapp.Flight.BottomSheet.TravellerBottomSheetDialogFragment;
import yoyo.app.android.com.yoyoapp.Flight.FlightSearch.FlightSearchFragment;
import yoyo.app.android.com.yoyoapp.FragmentTransaction.BaseFragment;
import yoyo.app.android.com.yoyoapp.FragmentTransaction.Utils.FragmentHistory;
import yoyo.app.android.com.yoyoapp.FragmentTransaction.Utils.Utilss;
import yoyo.app.android.com.yoyoapp.FragmentTransaction.Views.FragNavController;
import yoyo.app.android.com.yoyoapp.MainActivity;
import yoyo.app.android.com.yoyoapp.R;
import java.util.ArrayList;
import java.util.Calendar;

public class MainFlightActivity extends AppCompatActivity  implements TravellerBottomSheetDialogFragment.BotomSheetListener
        , BaseFragment.FragmentNavigation, FragNavController.TransactionListener, FragNavController.RootFragmentListener {

    private static final String TAG = "MainFlightActivity";
    public BottomNavigationView bottomNavigation;
    public int adultCount =1, childCount =0, infantCount =0, sum =1;
    public Calendar standardDate;
    public boolean isDateChanged = false;
    public ArrayList<String> iataCodeAirlines;
    public ArrayList<String> idAircrafts;
    public ArrayList<String> dayTimes;
    private String[] TABS = {"Ticket","Flight","Setting"};
    private TabLayout bottomTabLayout;
    private FragNavController navController;
    private FragmentHistory fragmentHistory;
    private int[] tabIconsSelected = {
            R.drawable.shopping_cart,
            R.drawable.tab_home,
            R.drawable.tab_profile
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_flight);

        init();
        initTab();
        setupBottomTabLayout(savedInstanceState);
    }

    private void setupBottomTabLayout(Bundle savedInstanceState) {
        fragmentHistory = new FragmentHistory();
        navController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.container)
                .transactionListener(this)
                .rootFragmentListener(this, TABS.length)
                .build();


        switchTab(1);
        updateTabSelection(1);
        bottomTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                fragmentHistory.push(tab.getPosition());
                switchTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                navController.clearStack();
                switchTab(tab.getPosition());
            }
        });
    }

    private void initTab() {
        if (bottomTabLayout != null) {
            for (int i = 0; i < TABS.length; i++) {
                bottomTabLayout.addTab(bottomTabLayout.newTab());
                TabLayout.Tab tab = bottomTabLayout.getTabAt(i);
                if (tab != null)
                    tab.setCustomView(getTabView(i));
            }
        }
    }

    private View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_item_bottom, null);
        ImageView icon = view.findViewById(R.id.tab_icon);
        icon.setImageDrawable(Utilss.setDrawableSelector(this, tabIconsSelected[position], tabIconsSelected[position]));
        return view;
    }

    private void init() {
        standardDate = Calendar.getInstance();
        iataCodeAirlines = new ArrayList<>();
        idAircrafts =   new ArrayList<>();
        dayTimes = new ArrayList<>();
        bottomTabLayout = findViewById(R.id.bottom_tab_layout);
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

    @Override
    public void pushFragment(Fragment fragment) {
        if (navController != null) {
            navController.pushFragment(fragment);
        }
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {

            case FragNavController.TAB1:
                if (MainActivity.isSingnedIn)
                    return new TicketFragment();
                else
                    return new TicketNotSignedInFragment();
            case FragNavController.TAB2:
                return new FlightSearchFragment();
            case FragNavController.TAB3:
                return new ProfileFragment();
        }
        throw new IllegalStateException("Need to send an index that we know");
    }

    @Override
    public void onTabTransaction(Fragment fragment, int index) {
        // If we have a backstack, show the back button
        if (getSupportActionBar() != null && navController != null) {
//            updateToolbar();
        }
    }

    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
        //do fragmentty stuff. Maybe change title, I'm not going to tell you how to live your life
        // If we have a backstack, show the back button
        if (getSupportActionBar() != null && navController != null) {
//            updateToolbar();
        }
    }
    private void updateTabSelection(int currentTab){

        for (int i = 0; i <  TABS.length; i++) {
            TabLayout.Tab selectedTab = bottomTabLayout.getTabAt(i);
            if(currentTab != i) {
                selectedTab.getCustomView().setSelected(false);
            }else{
                selectedTab.getCustomView().setSelected(true);
            }
        }
    }
    @Override
    public void onBackPressed() {

        if (!navController.isRootFragment()) {
            navController.popFragment();
        } else {

            if (fragmentHistory.isEmpty()) {
                startActivity(new Intent(this,MainActivity.class));
                finish();
            } else {

                if (fragmentHistory.getStackSize() > 1) {

                    int position = fragmentHistory.popPrevious();
                    switchTab(position);
                    updateTabSelection(position);

                } else {

                    switchTab(1);
                    updateTabSelection(1);
                    fragmentHistory.emptyStack();
                }
            }
        }
    }
    private void switchTab(int position) {
        navController.switchTab(position);
//      updateToolbarTitle(position);
    }
}
