package yoyo.app.android.com.yoyoapp;

import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import yoyo.app.android.com.yoyoapp.BottomSheet.PriceFilterBottomSheetDialogFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import yoyo.app.android.com.yoyoapp.Flight.ProfileFragment;
import yoyo.app.android.com.yoyoapp.Flight.TicketFragment;

public class MainActivity extends AppCompatActivity implements PriceFilterBottomSheetDialogFragment.BotomSheetListener{

    private FragmentTransaction transaction;
    public BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupBottomNavigation();
    }


    private void setupBottomNavigation() {

        bottomNavigation = findViewById(R.id.bn_main);

        replaceFragment(new MainPageFragment());
        bottomNavigation.setSelectedItemId(R.id.bn_home);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bn_home:
                        replaceFragment(new MainPageFragment());
                        return true;
                    case R.id.bn_profile:
                        replaceFragment(new ProfileFragment());
                        return true;
                    case R.id.bn_orders:
                        replaceFragment(new TicketFragment());
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    private void replaceFragment(Fragment fragment) {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_framelayout, fragment);
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onApplyClicked(String s) {
         TextView filterPriceTextview = findViewById(R.id.tv_search_price_filter);
         filterPriceTextview.setText(s);
    }

}

