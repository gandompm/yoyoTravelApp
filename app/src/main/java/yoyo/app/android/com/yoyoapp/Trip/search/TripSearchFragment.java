package yoyo.app.android.com.yoyoapp.Trip.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import ir.mirrajabi.searchdialog.core.Searchable;
import yoyo.app.android.com.yoyoapp.Trip.dialog.CategotyFilterBottomSheetDialogFragment;
import yoyo.app.android.com.yoyoapp.Trip.dialog.PriceFilterBottomSheetDialogFragment;
import yoyo.app.android.com.yoyoapp.DataModels.Location;
import yoyo.app.android.com.yoyoapp.Trip.Utils.DatePickerFragment;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.SearchDialog.SampleSearchModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import yoyo.app.android.com.yoyoapp.Trip.TripActivity;
import yoyo.app.android.com.yoyoapp.Trip.result.TripResultFragment;
import yoyo.app.android.com.yoyoapp.Utils;

public class TripSearchFragment extends Fragment implements View.OnClickListener  {

    private static final String TAG = "SearchActivity";
    private TextView checkInTextview, checkOutTextview, checkInTitle, checkOutTitle, nightNumTextview;
    private TextView searchTextView;
    private TextView smallTitleTextview ,titleTextview;
    private TextView filterPriceTextview, categoryTextview;
    private String incommingBundle;
    private Button searchButton;
    private ImageView backButton , calendarLogo1, calendarLogo2 , searchCityLogo, filterLogo, categoryLogo;
    private PriceFilterBottomSheetDialogFragment priceFilterBottomSheetFragment;
    private CategotyFilterBottomSheetDialogFragment categotyFilterBottomSheetDialogFragment;
    private FragmentManager fragmentManager;
    private TripSearchViewModel tripSearchViewModel;
    private ArrayList<SampleSearchModel> locationsList;
    private String diffDays ="7";
    private String startDateString= "From...";
    private String endDateString="To...";
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_trip,container,false);

        init();
        setupBundle();
        setupOnclickListner();
        setupSearchbutton();
        backButton.setOnClickListener(v -> getActivity().finish());

        getLocations();
        return view;
    }

    private void getLocations() {
        tripSearchViewModel.initLocationList();
        tripSearchViewModel.getLocationList().observe(getActivity(), new Observer<List<Location>>() {
            @Override
            public void onChanged(List<Location> locations) {
                if (locations != null) {
                    for (Location location:locations) {
                        SampleSearchModel sampleSearchModel = new SampleSearchModel(location.getTitle());
                        locationsList.add(sampleSearchModel);
                    }
                }
            }
        });
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
                        ((TripActivity)getActivity()).location = item.getTitle();
                        dialog.dismiss();
                    }
                });

        simpleSearchDialogCompat.show();
    }


    private void setupFilterPriceButtomsheet() {

        priceFilterBottomSheetFragment = PriceFilterBottomSheetDialogFragment.newInstance();
        priceFilterBottomSheetFragment.show(getFragmentManager(), "add_price_filter_dialog_fragment");
    }

    private void setupFilterCategoryButtomsheet() {

        categotyFilterBottomSheetDialogFragment = CategotyFilterBottomSheetDialogFragment.newInstance();
        categotyFilterBottomSheetDialogFragment.show(getFragmentManager(), "add_category_filter_dialog_fragment");
    }

    private void init() {
        locationsList = new ArrayList<>();
        tripSearchViewModel = ViewModelProviders.of(getActivity()).get(TripSearchViewModel.class);
        checkInTextview = view.findViewById(R.id.tv_search_check_in);
        checkOutTextview = view.findViewById(R.id.tv_search_check_out);
        titleTextview = view.findViewById(R.id.tv_search_page_title);
        smallTitleTextview = view.findViewById(R.id.tv_search_title);
        searchButton = view.findViewById(R.id.button_search_search);
        backButton = view.findViewById(R.id.iv_search_back);
        searchTextView = view.findViewById(R.id.et_search_bar);
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
        nightNumTextview = view.findViewById(R.id.tv_search_night_num);
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
        checkInTextview.setOnClickListener(this);
        checkOutTextview.setOnClickListener(this);
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
                bundle.putString(Utils.KEY_BUNDLE_FROM_DATE_CODE, startDateString);
                bundle.putString(Utils.KEY_BUNDLE_TO_DATE_CODE, endDateString);
                bundle.putString(Utils.KEY_BUNDLE_NIGHT_NUM_CODE, diffDays);
                bundle.putInt(Utils.KEY_BUNDLE_FROM_PRICE_CODE, ((TripActivity)getActivity()).fromPrice);
                bundle.putInt(Utils.KEY_BUNDLE_TO_PRICE_CODE, ((TripActivity)getActivity()).toPrice);
                bundle.putLong(Utils.KEY_BUNDLE_FROM_TIME_CODE,((TripActivity)getActivity()).fromTime);
                bundle.putLong(Utils.KEY_BUNDLE_TO_TIME_CODE,((TripActivity)getActivity()).toTime);
                bundle.putStringArrayList(Utils.KEY_BUNDLE_CATEGORIES_CODE,((TripActivity)getActivity()).categories);
                bundle.putString(Utils.KEY_BUNDLE_LOCATION_CODE,((TripActivity)getActivity()).location);


                TripResultFragment tripListSearchResultFragment = new TripResultFragment();
                tripListSearchResultFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.container, tripListSearchResultFragment);
                fragmentTransaction.addToBackStack("triplist");
                fragmentTransaction.commit();

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
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_up,R.anim.no_animation);
            fragmentTransaction.add(R.id.container,new DatePickerFragment(arrayList -> {
                 checkInTextview.setText(arrayList.get(0));
                 checkOutTextview.setText(arrayList.get(1));
                 nightNumTextview.setText(arrayList.get(2));
                diffDays = arrayList.get(2);
                startDateString = arrayList.get(3);
                endDateString = arrayList.get(4);

            }));
            fragmentTransaction.addToBackStack("date_picker");
            fragmentTransaction.commit();
        }

        if (v.getId() == R.id.iv_search_search_city
                || v.getId() == R.id.et_search_bar)
        {
            setupSearchDialog();
        }

        if (v.getId() == R.id.iv_search_filter
                || v.getId() == R.id.tv_search_price_filter)
        {
            setupFilterPriceButtomsheet();
        }

        if (v.getId() == R.id.iv_search_type
                || v.getId() == R.id.tv_search_type)
        {
            setupFilterCategoryButtomsheet();
        }
    }



}