package yoyo.app.android.com.yoyoapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import yoyo.app.android.com.yoyoapp.BottomSheet.CitiesListBottomSheetDialogFragment;
import com.cpacm.library.SimpleViewPager;
import com.cpacm.library.transformers.CyclePageTransformer;
import yoyo.app.android.com.yoyoapp.BannerSlider.BasicPagerAdapter;
import yoyo.app.android.com.yoyoapp.Flight.MainFlightActivity;


public class MainPageFragment extends Fragment {

    public static final String KEY_BUNDLE_MAIN_PAGE_CODE = "Mainpage";
    private TextView searchEditText;
    private CardView searchHotelCardview, toursCardview , flightCardview;
    private SimpleViewPager simpleSlider;
    private BasicPagerAdapter sliderAdapter;
    private FragmentManager fragmentManager;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_page,container,false);

        init();
        setupBannerSlider();
        setupSearchview();
        setupServices();

        return view;
    }

    private void init() {
        toursCardview = view.findViewById(R.id.cv_mainpage_tours);
        searchHotelCardview = view.findViewById(R.id.cv_mainpage_hotels);
        fragmentManager = getFragmentManager();
        searchEditText = view.findViewById(R.id.sv_mainpage_search);
        flightCardview = view.findViewById(R.id.cv_mainpage_flight);
    }

    private void setupBannerSlider() {
        sliderAdapter = new BasicPagerAdapter(getContext());
        simpleSlider = (SimpleViewPager) view.findViewById(R.id.banner_slider_mainpage);
        simpleSlider.setAdapter(sliderAdapter);
        simpleSlider.startAutoScroll(true);
        simpleSlider.setPageTransformer(new CyclePageTransformer(simpleSlider));
    }

    private void setupSearchview() {
        searchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CitiesListBottomSheetDialogFragment citiesListBottomSheetDialogFragment = new CitiesListBottomSheetDialogFragment();
                citiesListBottomSheetDialogFragment.show(getFragmentManager(), "add_cities_list_dialog_fragment");
            }
        });
    }


    private void setupServices() {
        searchHotelCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendingToSearchHotelFragment("hotel");
            }
        });

        toursCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendingToSearchHotelFragment("tour");
            }
        });

        flightCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainFlightActivity.class));
                getActivity().finish();
            }
        });
    }


    private void sendingToSearchHotelFragment(String destination) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_BUNDLE_MAIN_PAGE_CODE,destination);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SearchFragment searchFragment = new SearchFragment();
        searchFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.main_framelayout,searchFragment).addToBackStack(destination+"search");
        fragmentTransaction.commit();
    }
}
