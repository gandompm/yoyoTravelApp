package yoyo.app.android.com.yoyoapp.MaterialViewPager;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import yoyo.app.android.com.yoyoapp.Flight.Profile.SignOutFragment;
import yoyo.app.android.com.yoyoapp.R;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class HotelSearchActivity extends AppCompatActivity {

    private MaterialViewPager mViewPager;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_city_view_pager);
        setTitle("");

        mViewPager = findViewById(R.id.materialViewPager);



        final HotelListFragment hotelListFragment = new HotelListFragment();

        final Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 2) {
                    case 0:
                        return hotelListFragment;
                    case 1:
                        return new SignOutFragment();

                    default:
                        return new HotelListFragment();
                }
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 2) {
                    case 0:
                        return "Hotels";
                    case 1:
                        return "Trips";
//                    case 2:
//                        return "Professionnel";
//                    case 3:
//                        return "Divertissement";
                }
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                            R.color.green,
                            "https://photorator.com/photos/images/-view-of-tehran-from-the-nature-bridge-pol-e-tabiat-iran-74985.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                            R.color.sky_black,
                            "https://image.freepik.com/free-photo/a-wide-shot-from-milad-tower-in-tehran_16422-6.jpg");
//                    case 2:
//                        return HeaderDesign.fromColorResAndUrl(
//                            R.color.cyan,
//                            "http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg");
//                    case 3:
//                        return HeaderDesign.fromColorResAndUrl(
//                            R.color.red,
//                            "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        mViewPager.setBackgroundColor(R.color.black);



        final View logo = findViewById(R.id.logo_white);
        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });
        }

        mViewPager.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(HotelSearchActivity.this,SearchActivity.class));
           }
        });




    }


}
