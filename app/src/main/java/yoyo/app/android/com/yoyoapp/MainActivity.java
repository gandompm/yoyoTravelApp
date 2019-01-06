package yoyo.app.android.com.yoyoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.TextView;


import yoyo.app.android.com.yoyoapp.BottomSheet.PriceFilterBottomSheetDialogFragment;
import yoyo.app.android.com.yoyoapp.DataModels.Client;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity implements PriceFilterBottomSheetDialogFragment.BotomSheetListener{

    private FragmentTransaction transaction;
    public BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadLanguageFromSharedPref();
        setupBottomNavigation();
    }


    private void setupBottomNavigation() {

        bottomNavigation = (BottomNavigationView) findViewById(R.id.bn_main);

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
                        replaceFragment(new OrdersPageFragment());
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


    public void setLocale(String lang) {
        changeLocale(lang);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }

    private void changeLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(myLocale);
        res.updateConfiguration(conf, dm);
        saveLangSharedPref(lang);
    }

    private void saveLangSharedPref(String language) {
        UserSharedManager userSharedManager = new UserSharedManager(this);
        userSharedManager.savaLanguage(language);
    }

    public void loadLanguageFromSharedPref()
    {
        UserSharedManager userSharedManager = new UserSharedManager(this);
        Client client = userSharedManager.getClient();
        changeLocale(client.getLanguage());
    }
}

