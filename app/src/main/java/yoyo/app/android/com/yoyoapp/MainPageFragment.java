package yoyo.app.android.com.yoyoapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import es.dmoral.toasty.Toasty;
import yoyo.app.android.com.yoyoapp.BottomSheet.CitiesListBottomSheetDialogFragment;
import com.cpacm.library.SimpleViewPager;
import com.cpacm.library.transformers.CyclePageTransformer;
import yoyo.app.android.com.yoyoapp.BannerSlider.BasicPagerAdapter;
import yoyo.app.android.com.yoyoapp.Flight.MainFlightActivity;
import yoyo.app.android.com.yoyoapp.Trip.TripActivity;
import yoyo.app.android.com.yoyoapp.Trip.booking.BookingActivity;
import yoyo.app.android.com.yoyoapp.Trip.schedule.ScheduleTripFragment;
import yoyo.app.android.com.yoyoapp.Trip.schedule.request.RequestFragment;


public class MainPageFragment extends Fragment {

    private TextView searchEditText;
    private CardView searchHotelCardview, tripsCardview , flightCardview;
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
        tripsCardview = view.findViewById(R.id.cv_mainpage_trips);
        searchHotelCardview = view.findViewById(R.id.cv_mainpage_hotels);
        fragmentManager = getFragmentManager();
        searchEditText = view.findViewById(R.id.sv_mainpage_search);
        flightCardview = view.findViewById(R.id.cv_mainpage_flight);
    }

    private void setupBannerSlider() {
        sliderAdapter = new BasicPagerAdapter(getContext());
        simpleSlider = view.findViewById(R.id.banner_slider_mainpage);
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

//                sendingToSearchFragment("hotel");

                Toasty.info(getContext(),"Coming soon...").show();
//                Toast.makeText(getContext(), "Coming soon...", Toast.LENGTH_SHORT).show();
//                RequestFragment requestFragment = new RequestFragment();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.add(R.id.container,requestFragment);
//                fragmentTransaction.addToBackStack("request");
//                fragmentTransaction.commit();
//
//                Intent intent = new Intent(getActivity(),BookingActivity.class);
//                intent.putExtra("travellerNum",1);
//                startActivity(intent);
//                getActivity().overridePendingTransition(0,  0);
            }
        });

        tripsCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), TripActivity.class));
                getActivity().overridePendingTransition(0,  0);
            }
        });

        flightCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getContext(), MainFlightActivity.class));
//                getActivity().overridePendingTransition(0,  0);
                Toasty.info(getContext(),"Coming soon...").show();

            }
        });
    }

    private void sendingToSearchFragment(String destination) {
        Bundle bundle = new Bundle();
        bundle.putString(Utils.KEY_BUNDLE_MAIN_PAGE_CODE,destination);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HotelSearchFragment hotelSearchFragment = new HotelSearchFragment();
        hotelSearchFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.container, hotelSearchFragment).addToBackStack(destination + "search");
        fragmentTransaction.commit();
    }

}
