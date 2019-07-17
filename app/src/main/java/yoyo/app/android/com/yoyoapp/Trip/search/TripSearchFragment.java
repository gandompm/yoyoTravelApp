package yoyo.app.android.com.yoyoapp.Trip.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import es.dmoral.toasty.Toasty;
import yoyo.app.android.com.yoyoapp.MainActivity;
import yoyo.app.android.com.yoyoapp.Trip.dialog.PriceFilterBottomSheetDialogFragment;
import yoyo.app.android.com.yoyoapp.DataModels.Location;
import yoyo.app.android.com.yoyoapp.Trip.Utils.DatePickerFragment;
import yoyo.app.android.com.yoyoapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import yoyo.app.android.com.yoyoapp.Trip.result.TripResultFragment;
import yoyo.app.android.com.yoyoapp.Utils;

public class TripSearchFragment extends Fragment implements View.OnClickListener  {

    private static final String TAG = "SearchActivity";
    private TextView checkInTextview, checkOutTextview, checkInTitle, checkOutTitle, nightNumTextview;
    private TextView searchDestinationTextView;
    private TextView smallTitleTextview ,titleTextview;
    private TextView filterPriceTextview;
    private String incommingBundle;
    private Button searchButton;
    private ImageView backButton , calendarLogo1, calendarLogo2 , filterLogo, searchCityLogo2;
    private PriceFilterBottomSheetDialogFragment priceFilterBottomSheetFragment;
    private FragmentManager fragmentManager;
    private TripSearchViewModel tripSearchViewModel;
    private ArrayList<Location> originssList;
    private ArrayList<Location> destinationsList;
    private String diffDays ="7";
    private String startDateString= "From...";
    private String endDateString="To...";
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_trip,container,false);

        init();
        setupOnclickListner();
        setupSearchbutton();
        backButton.setOnClickListener(v -> getActivity().onBackPressed());
        getDestination();
        return view;
    }

    private void getDestination() {
        tripSearchViewModel.initDestination();
        tripSearchViewModel.getDestinations().observe(getActivity(), new Observer<List<Location>>() {
            @Override
            public void onChanged(List<Location> locations) {
                destinationsList.clear();
                if (locations != null) {
                    destinationsList.addAll(locations);
                }
            }
        });
    }


    // setup cities search dialog
    private void setupSearchDestination()
    {
                SimpleSearchDialogCompat<Location> simpleSearchDialogCompat =  new SimpleSearchDialogCompat(getContext(), "Search...",
                "Where do you like to go?", null, destinationsList,
                new SearchResultListener<Location>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat dialog, Location item, int position) {
                        searchDestinationTextView.setText(item.getTitle());
                        ((MainActivity) getActivity()).setDestination(item);
                        dialog.dismiss();
                    }
                });
                if (destinationsList.isEmpty())
                simpleSearchDialogCompat.setLoading(true);
                else
                    simpleSearchDialogCompat.setLoading(false);

        simpleSearchDialogCompat.show();
    }


    private void setupFilterPriceButtomsheet() {

        priceFilterBottomSheetFragment = new PriceFilterBottomSheetDialogFragment((min, max) -> {
            TextView filterPriceTextview = view.findViewById(R.id.tv_search_price_filter);
            DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
            String minimum = decimalFormat.format(Integer.valueOf(min));
            String maximum = decimalFormat.format(Integer.valueOf(max));
            filterPriceTextview.setText("From " + minimum + " to "+ maximum  +"$");
            if (!min.equals("10") || !max.equals("5500"))
            {
                ((MainActivity)getActivity()).setFromPrice(Integer.parseInt(min));
                ((MainActivity)getActivity()).setToPrice(Integer.parseInt(max));
            }
            else
                filterPriceTextview.setText(getResources().getString(R.string.filter_by_price_optional));
        });
        priceFilterBottomSheetFragment.show(getFragmentManager(), "add_price_filter_dialog_fragment");
    }

    private void init() {
        originssList = new ArrayList<>();
        destinationsList = new ArrayList<>();
        tripSearchViewModel = ViewModelProviders.of(getActivity()).get(TripSearchViewModel.class);
        checkInTextview = view.findViewById(R.id.tv_search_check_in);
        checkOutTextview = view.findViewById(R.id.tv_search_check_out);
        titleTextview = view.findViewById(R.id.tv_search_page_title);
        smallTitleTextview = view.findViewById(R.id.tv_search_title);
        searchButton = view.findViewById(R.id.button_search_search);
        backButton = view.findViewById(R.id.iv_search_back);
        searchDestinationTextView = view.findViewById(R.id.et_search_bar_destination);
        filterPriceTextview = view.findViewById(R.id.tv_search_price_filter);
        fragmentManager = getFragmentManager();
        checkInTitle = view.findViewById(R.id.tv_search_check_in_txt);
        checkOutTitle = view.findViewById(R.id.tv_search_check_out_txt);
        calendarLogo2 = view.findViewById(R.id.iv_search_calender_logo2);
        calendarLogo1 = view.findViewById(R.id.iv_search_calender_logo1);
        searchCityLogo2 = view.findViewById(R.id.iv_search_search_city3);
        filterLogo = view.findViewById(R.id.iv_search_filter);
        nightNumTextview = view.findViewById(R.id.tv_search_night_num);
        checkInTextview.setText(((MainActivity) getActivity()).getFromTimeString());
        checkOutTextview.setText(((MainActivity) getActivity()).getToTimeString());
    }



    private void setupOnclickListner() {
        checkInTextview.setOnClickListener(this);
        checkOutTextview.setOnClickListener(this);
        checkInTitle.setOnClickListener(this);
        checkOutTitle.setOnClickListener(this);
        calendarLogo1.setOnClickListener(this);
        calendarLogo2.setOnClickListener(this);
        filterLogo.setOnClickListener(this);
        filterPriceTextview.setOnClickListener(this);
        searchDestinationTextView.setOnClickListener(this);
    }

    private void setupSearchbutton() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchDestinationTextView.getText().equals("Destination")){
                    Toasty.normal(getContext(), "Destination can not be empty.").show();
                }else {
                        Bundle bundle = new Bundle();
                        bundle.putString(Utils.KEY_BUNDLE_FROM_DATE_CODE, startDateString);
                        bundle.putString(Utils.KEY_BUNDLE_TO_DATE_CODE, endDateString);
                        bundle.putString(Utils.KEY_BUNDLE_NIGHT_NUM_CODE, diffDays);
                        TripResultFragment tripListSearchResultFragment = new TripResultFragment();
                        tripListSearchResultFragment.setArguments(bundle);

                        ((MainActivity)getActivity()).showFragment(TripSearchFragment.this,tripListSearchResultFragment,false);
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
            ((MainActivity)getActivity()).showFragment(this,new DatePickerFragment(arrayList -> {
                checkInTextview.setText(arrayList.get(0));
                checkOutTextview.setText(arrayList.get(1));
                nightNumTextview.setText(arrayList.get(2));
                diffDays = arrayList.get(2);
                startDateString = arrayList.get(3);
                endDateString = arrayList.get(4);
            }),true);
        }

        if (v.getId() == R.id.iv_search_search_city3
                || v.getId() == R.id.et_search_bar_destination)
        {
            setupSearchDestination();
        }

        if (v.getId() == R.id.iv_search_filter
                || v.getId() == R.id.tv_search_price_filter)
        {
            setupFilterPriceButtomsheet();
        }

    }



}