package yoyo.app.android.com.yoyoapp.MaterialViewPager;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import yoyo.app.android.com.yoyoapp.AboutCityFragment;
import yoyo.app.android.com.yoyoapp.MainActivity;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Utils;

public class CitiyViewPagerFragment extends Fragment {

    private MaterialViewPager mViewPager;
    private View view;
    private String cityImage;
    private String cityName;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_city_view_pager,container,false);

        ((MainActivity) getActivity()).getBottomNavigation().setVisibility(View.GONE);
        ((MainActivity) getContext()).getMainFrameLayout().setPadding(0, 0, 0, 0);

        mViewPager = view.findViewById(R.id.materialViewPager);
        Bundle bundle = getArguments();
        cityName = bundle.getString(Utils.KEY_BUNDLE_CITY_NAME);
        cityImage = bundle.getString(Utils.KEY_BUNDLE_CITY_IMAGE);


        AboutCityFragment aboutCityFragment = new AboutCityFragment();
        aboutCityFragment.setArguments(bundle);

        final Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        }

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 2) {
                    case 0:
                        return aboutCityFragment;


                    default:
                        return aboutCityFragment;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 2) {
                    case 0:
                        return "About City";
//                    case 1:
//                        return "Services";
//                    case 2:
//                        return "Professionnel";
//                    case 3:
//                        return "Divertissement";
                }
                return "";
            }
        });

        mViewPager.getPagerTitleStrip().setTextColor(R.color.golden);

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.colorPrimary,
                                cityImage);
//                    case 1:
//                        return HeaderDesign.fromColorResAndUrl(
//                                R.color.colorPrimary,
//                                "https://image.freepik.com/free-photo/a-wide-shot-from-milad-tower-in-tehran_16422-6.jpg");
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




        final TextView logo = (TextView)view.findViewById(R.id.logo_white);
        logo.setText(cityName);



        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    Toast.makeText(getContext().getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });
        }

        mViewPager.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float scale = getResources().getDisplayMetrics().density;
                int dpAsPixels = (int) (50 * scale + 0.5f);
                ((MainActivity) getActivity()).getBottomNavigation().setVisibility(View.VISIBLE);
                ((MainActivity) getActivity()).getMainFrameLayout().setPadding(0, 0, 0, dpAsPixels);
                ((MainActivity) getActivity()).getBottomNavigation().setVisibility(View.VISIBLE);
                getFragmentManager().popBackStack();
            }
        });

        mViewPager.getToolbar().setTitle("");


        return view;
    }
}
