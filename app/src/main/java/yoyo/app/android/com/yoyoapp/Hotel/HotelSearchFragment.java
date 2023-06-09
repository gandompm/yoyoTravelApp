package yoyo.app.android.com.yoyoapp.Hotel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;
import yoyo.app.android.com.yoyoapp.BottomSheet.DatePickerBottomSheet;
import yoyo.app.android.com.yoyoapp.MainActivity;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.SearchDialog.SampleSearchModel;
import yoyo.app.android.com.yoyoapp.SharedDataViewModel;
import yoyo.app.android.com.yoyoapp.Utils;
import yoyo.app.android.com.yoyoapp.trip.Utils.DatePickerFragment;
import yoyo.app.android.com.yoyoapp.trip.dialog.PriceFilterBottomSheetDialogFragment;
import yoyo.app.android.com.yoyoapp.trip.search.TourSearchViewModel;

import java.util.ArrayList;

public class HotelSearchFragment extends Fragment implements View.OnClickListener  {

    private static final String TAG = "SearchActivity";
    private TextView checkInEditText ,checkOutEditText, checkInTitle, checkOutTitle;
    private TextView searchTextView;
    private TextView smallTitleTextview ,titleTextview;
    private TextView filterPriceTextview, categoryTextview;
    private String incommingBundle;
    private Button searchButton;
    private ImageView backButton , calendarLogo1, calendarLogo2 , searchCityLogo, filterLogo, categoryLogo;
    private BottomSheetBehavior bottomSheetBehavior;
    private PriceFilterBottomSheetDialogFragment priceFilterBottomSheetFragment;
    private FragmentManager fragmentManager;
    private TourSearchViewModel tourSearchViewModel;
    private ArrayList<SampleSearchModel> locationsList;
    private DatePickerBottomSheet datePickerBottomSheet;
    private View view;

    private SharedDataViewModel sharedDataViewModel;
    private int fromPrice;
    private int toPrice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search,container,false);

        init();
        setupBundle();
        setupOnclickListner();
        setupSearchbutton();
        backButton.setOnClickListener(v -> getActivity().finish());

        sharedDataViewModel.getFromPrice().observe(this, price -> fromPrice = price);
        sharedDataViewModel.getToPrice().observe(this, price -> toPrice = price);

        return view;
    }

    // setup cities search dialog
    private void setupSearchDialog()
    {
        SimpleSearchDialogCompat<Searchable> simpleSearchDialogCompat =  new SimpleSearchDialogCompat(getContext(), "Search...",
                "What are you looking for...?", null, locationsList,
                new SearchResultListener<SampleSearchModel>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat dialog, SampleSearchModel item, int position) {

                        searchTextView.setText(item.getTitle());
                        dialog.dismiss();
                    }
                });

        simpleSearchDialogCompat.show();
    }


    private void setupFilterPriceButtonsheet() {

    }

    private void init() {
        locationsList = new ArrayList<>();
        tourSearchViewModel = ViewModelProviders.of(getActivity()).get(TourSearchViewModel.class);
        checkInEditText = view.findViewById(R.id.tv_search_check_in);
        checkOutEditText = view.findViewById(R.id.tv_search_check_out);
        titleTextview = view.findViewById(R.id.tv_search_page_title);
        smallTitleTextview = view.findViewById(R.id.tv_search_title);
        searchButton = view.findViewById(R.id.button_search_search);
        backButton = view.findViewById(R.id.iv_search_back);
        searchTextView = view.findViewById(R.id.et_search_bar_origin);
        filterPriceTextview = view.findViewById(R.id.tv_search_price_filter);
        fragmentManager = getFragmentManager();
        checkInTitle = view.findViewById(R.id.tv_search_check_in_txt);
        checkOutTitle = view.findViewById(R.id.tv_search_check_out_txt);
        calendarLogo2 = view.findViewById(R.id.iv_search_calender_logo2);
        calendarLogo1 = view.findViewById(R.id.iv_search_calender_logo1);
        searchCityLogo = view.findViewById(R.id.iv_search_search_city);
        filterLogo = view.findViewById(R.id.iv_search_filter);
        categoryTextview = view.findViewById(R.id.tv_search_type);
        categoryLogo = view.findViewById(R.id.iv_search_type);
        sharedDataViewModel = ViewModelProviders.of(getActivity()).get(SharedDataViewModel.class);
    }

    private void setupBundle() {
        Bundle bundle = getArguments();
        incommingBundle = bundle.getString(Utils.KEY_BUNDLE_MAIN_PAGE_CODE);

        if (incommingBundle.contains("trip"))
        {
            titleTextview.setText(getString(R.string.tours));
            smallTitleTextview.setText(getString(R.string.going_anywhere));
            searchTextView.setText(getString(R.string.tours_near_me));
        }
    }

    private void setupOnclickListner() {
        checkInEditText.setOnClickListener(this);
        checkOutEditText.setOnClickListener(this);
        checkInTitle.setOnClickListener(this);
        checkOutTitle.setOnClickListener(this);
        calendarLogo1.setOnClickListener(this);
        calendarLogo2.setOnClickListener(this);
        searchTextView.setOnClickListener(this);
        searchCityLogo.setOnClickListener(this);
        filterLogo.setOnClickListener(this);
        filterPriceTextview.setOnClickListener(this);
        categoryTextview.setOnClickListener(this);
        categoryLogo.setOnClickListener(this);
    }

    private void setupSearchbutton() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();

                bundle.putString(Utils.KEY_BUNDLE_SEARCH_STRING_CODE, searchTextView.getText().toString());
                bundle.putString(Utils.KEY_BUNDLE_FROM_DATE_CODE, DatePickerBottomSheet.startDateString);
                bundle.putString(Utils.KEY_BUNDLE_TO_DATE_CODE, DatePickerBottomSheet.endDateString);
                bundle.putLong(Utils.KEY_BUNDLE_NIGHT_NUM_CODE, DatePickerBottomSheet.diffDays);
                bundle.putInt(Utils.KEY_BUNDLE_FROM_PRICE_CODE, fromPrice);
                bundle.putInt(Utils.KEY_BUNDLE_TO_PRICE_CODE, toPrice);
//                bundle.putLong(Utils.KEY_BUNDLE_FROM_TIME_CODE, ((MainActivity) getActivity()).getFromTime());
//                bundle.putLong(Utils.KEY_BUNDLE_TO_TIME_CODE, ((MainActivity) getActivity()).getToTime());
//                bundle.putString(Utils.KEY_BUNDLE_LOCATION_CODE,((MainActivity)getActivity()).origin);

                if (incommingBundle.equals("trip"))
                {
//                    TripListSearchResultFragment tripListSearchResultFragment = new TripListSearchResultFragment();
//                    tripListSearchResultFragment.setArguments(bundle);
//                    fragmentTransaction.replace(R.id.container, tripListSearchResultFragment);
//                    fragmentTransaction.addToBackStack("triplist");
//                    fragmentTransaction.commit();
                }
                else
                {
                    HotelListSearchResultFragment hotelListSearchResultFragment = new HotelListSearchResultFragment();
                    hotelListSearchResultFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.container,hotelListSearchResultFragment);
                    fragmentTransaction.addToBackStack("hotellist");
                    fragmentTransaction.commit();
                }
            }
        });

    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_search_check_in_txt
                || v.getId() == R.id.tv_search_check_out_txt
                || v.getId() == R.id.tv_search_check_in
                || v.getId() == R.id.tv_search_check_out
                || v.getId() == R.id.iv_search_calender_logo1
                || v.getId() == R.id.iv_search_calender_logo2)
        {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container,new DatePickerFragment(fromDateString->{

            }));
            fragmentTransaction.addToBackStack("date_picker");
            fragmentTransaction.commit();
        }

        if (v.getId() == R.id.iv_search_search_city
                || v.getId() == R.id.et_search_bar_origin)
        {
            setupSearchDialog();
        }

        if (v.getId() == R.id.iv_search_filter
                || v.getId() == R.id.tv_search_price_filter)
        {
            setupFilterPriceButtonsheet();
        }

        if (v.getId() == R.id.iv_search_type
                || v.getId() == R.id.tv_search_type)
        {

        }
    }


}