package yoyo.app.android.com.yoyoapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import yoyo.app.android.com.yoyoapp.BottomSheet.DatePickerBottomSheet;
import yoyo.app.android.com.yoyoapp.BottomSheet.PriceFilterBottomSheetDialogFragment;
import yoyo.app.android.com.yoyoapp.SearchDialog.SampleSearchModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

public class SearchFragment extends Fragment implements View.OnClickListener  {

    private static final String TAG = "SearchActivity";
    public static final String KEY_BUNDLE_SEARCH_STRING_CODE = "search string";
    public static final String KEY_BUNDLE_FROM_DATE_CODE = "start date from search";
    public static final String KEY_BUNDLE_TO_DATE_CODE = "end date from search";
    public static final String KEY_BUNDLE_NIGHT_NUM_CODE = "night number from search";
    private TextView checkInEditText ,checkOutEditText, checkInTitle, checkOutTitle;
    private TextView searchTextView;
    private TextView smallTitleTextview ,titleTextview;
    private TextView filterPriceTextview;
    private String incommingBundle;
    private Button searchButton;
    private ImageView backButton;
    private BottomSheetBehavior bottomSheetBehavior;
    private PriceFilterBottomSheetDialogFragment priceFilterBottomSheetFragment;
    private FragmentManager fragmentManager;
    private DatePickerBottomSheet datePickerBottomSheet;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search,container,false);

        datePickerBottomSheet = new DatePickerBottomSheet(getContext(),view);
        init();
        setupBundle();
        setupDatePicker();
        setupSearchbutton();
        setupBackButton();
        setupSearchDialog();
        setupFilterPriceButtonsheet();

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        return view;
    }


    private void setupFilterPriceButtonsheet() {

        filterPriceTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                priceFilterBottomSheetFragment = PriceFilterBottomSheetDialogFragment.newInstance();
                priceFilterBottomSheetFragment.show(getFragmentManager(), "add_price_filter_dialog_fragment");

            }
        });
    }

    private void init() {

        checkInEditText = view.findViewById(R.id.tv_search_check_in);
        checkOutEditText = view.findViewById(R.id.tv_search_check_out);
        titleTextview = view.findViewById(R.id.tv_search_page_title);
        smallTitleTextview = view.findViewById(R.id.tv_search_title);
        searchButton = view.findViewById(R.id.button_search_search);
        backButton = view.findViewById(R.id.iv_search_back);
        searchTextView = view.findViewById(R.id.et_search_bar);
        LinearLayout llBottomSheet = (LinearLayout) view.findViewById(R.id.ll_datepicker_bottom_sheet);
        filterPriceTextview = view.findViewById(R.id.tv_search_price_filter);
        fragmentManager = getFragmentManager();
        checkInTitle = view.findViewById(R.id.tv_search_check_in_txt);
        checkOutTitle = view.findViewById(R.id.tv_search_check_out_txt);
        // init the bottom sheet behavior
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
    }

    private void setupBundle() {
        Bundle bundle = getArguments();
        incommingBundle = bundle.getString(MainPageFragment.KEY_BUNDLE_MAIN_PAGE_CODE);

        if (incommingBundle.contains("tour"))
        {
            titleTextview.setText("Tour");
            smallTitleTextview.setText("Going anywhere?");
            searchTextView.setText("Tour near me");
        }
    }

    private void setupDatePicker() {
        checkInEditText.setOnClickListener(this);
        checkOutEditText.setOnClickListener(this);
        checkInTitle.setOnClickListener(this);
        checkOutTitle.setOnClickListener(this);
    }

    private void setupBackButton() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack();
            }
        });
    }

    private void setupSearchbutton() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();

                bundle.putString(KEY_BUNDLE_SEARCH_STRING_CODE, searchTextView.getText().toString());
                bundle.putString(KEY_BUNDLE_FROM_DATE_CODE, DatePickerBottomSheet.startDateString);
                bundle.putString(KEY_BUNDLE_TO_DATE_CODE, DatePickerBottomSheet.endDateString);
                bundle.putLong(KEY_BUNDLE_NIGHT_NUM_CODE, DatePickerBottomSheet.diffDays);
                if (incommingBundle.equals("tour"))
                {
                    ToursListSearchResultFragment toursListSearchResultFragment = new ToursListSearchResultFragment();
                    toursListSearchResultFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.main_framelayout,toursListSearchResultFragment);
                    fragmentTransaction.addToBackStack("tourlist");
                    fragmentTransaction.commit();
                }
                else
                {
                    HotelListSearchResultFragment hotelListSearchResultFragment = new HotelListSearchResultFragment();
                    hotelListSearchResultFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.main_framelayout,hotelListSearchResultFragment);
                    fragmentTransaction.addToBackStack("hotellist");
                    fragmentTransaction.commit();
                }
            }
        });
    }

    private void setupSearchDialog()
    {

        searchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SimpleSearchDialogCompat(getContext(), "Search...",
                        "What are you looking for...?", null, createSampleData(),
                        new SearchResultListener<SampleSearchModel>() {
                            @Override
                            public void onSelected(BaseSearchDialogCompat dialog, SampleSearchModel item, int position) {

                                searchTextView.setText( item.getTitle());
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
    }

    private ArrayList<SampleSearchModel> createSampleData(){
        ArrayList<SampleSearchModel> items = new ArrayList<>();
        items.add(new SampleSearchModel("Tehran"));
        items.add(new SampleSearchModel("Ghazvin"));
        items.add(new SampleSearchModel("Qom"));
        items.add(new SampleSearchModel("Kashan"));
        items.add(new SampleSearchModel("Yazd"));
        items.add(new SampleSearchModel("Shiraz"));
        items.add(new SampleSearchModel("Isfahan"));
        items.add(new SampleSearchModel("Kish"));
        items.add(new SampleSearchModel("Neyshabour"));
        return items;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_search_check_in_txt
                || v.getId() == R.id.tv_search_check_out_txt
                || v.getId() == R.id.tv_search_check_in
                || v.getId() == R.id.tv_search_check_out)
        {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }
}