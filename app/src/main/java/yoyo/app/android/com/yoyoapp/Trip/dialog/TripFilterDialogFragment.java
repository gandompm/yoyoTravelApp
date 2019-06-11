package yoyo.app.android.com.yoyoapp.Trip.dialog;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.appyvet.materialrangebar.RangeBar;
import com.dagang.library.GradientButton;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.shawnlin.numberpicker.NumberPicker;
import yoyo.app.android.com.yoyoapp.DataModels.Category;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.TripActivity;
import yoyo.app.android.com.yoyoapp.Trip.adapter.CategoryRecyclerviewAddapter;
import yoyo.app.android.com.yoyoapp.Trip.result.TripResultFragment;
import yoyo.app.android.com.yoyoapp.Trip.search.TripSearchViewModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TripFilterDialogFragment extends BottomSheetDialogFragment{

    private static final String TAG = "TripFilterDialogFragmen";
    private RangeBar rangeBarPrice;
    private TextView rangebarPriceTextview, rangebarDurationTextview;
    private Button gradientButton;
    private ImageView closeImageview;
    private ArrayList<Category> categorieList;
    private RecyclerView recyclerView;
    private CategoryRecyclerviewAddapter adapter;
    private ArrayList<String> categoryNames;
    private TripSearchViewModel tripSearchViewModel;
    private NumberPicker numberPicker;
    private String minimum, maximum;
    private String minPrice = "10", maxPrice = "5500";
    private boolean hasPriceChanged = false, hasDurationChanged = false;
    private int duration = 6;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_sheet_filter_trip,container,false);


        init();
        setupNumberPicker();
        setupPriceRangeBar();
        gradientButton.setOnClickListener(v -> setupApplyButton());
        closeImageview.setOnClickListener(v -> dismiss());
//        rangeBarPrice.setOnRangeBarChangeListener(this);

        return view;
    }

    private void setupNumberPicker() {
        // Using string values
        // IMPORTANT! setMinValue to 1 and call setDisplayedValues after setMinValue and setMaxValue
        numberPicker.setOrientation(NumberPicker.HORIZONTAL);
        String[] data = {"1 Day", "2 Day", "3 Day", "4 Day", "5 Day", "6 Day", "7 Day",
                "8 Day", "9 Day","10 Day","11 Day","12 Day","13 Day"};
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);

        // OnValueChangeListener
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hasDurationChanged = true;
                rangebarDurationTextview.setText(newVal +" * Day");
                duration = newVal;
                if (gradientButton.getVisibility() == View.GONE)
                {
                    showApplyButton();
                }
            }
        });
    }

    private void setupApplyButton() {
        if (hasPriceChanged)
        {
            ((TripActivity)getActivity()).fromPrice  = Integer.parseInt(minPrice);
            ((TripActivity)getActivity()).toPrice = Integer.parseInt(maxPrice);
        }
        if (hasDurationChanged) {
            ((TripActivity)getActivity()).minDuration = duration;
        }
        dismiss();
        getFragmentManager().popBackStack();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,new TripResultFragment());
        fragmentTransaction.addToBackStack("triplist");
        fragmentTransaction.commit();
    }

    private void setupPriceRangeBar() {
        rangeBarPrice.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex, String leftPinValue, String rightPinValue) {
                hasPriceChanged = true;
                minPrice = leftPinValue;
                maxPrice = rightPinValue;

                DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
                minimum = decimalFormat.format(Integer.valueOf(minPrice));
                maximum = decimalFormat.format(Integer.valueOf(maxPrice));

                if (gradientButton.getVisibility() == View.GONE)
                {
                    showApplyButton();
                }

                rangebarPriceTextview.setText("From " + minimum + "$ to " + maximum + "$");
            }
        });
    }


    private void init() {
        rangebarPriceTextview = view.findViewById(R.id.tv_filtertrip_price_num);
        rangeBarPrice = view.findViewById(R.id.rangebar_filtertrip_price);
        rangebarDurationTextview = view.findViewById(R.id.tv_filtertrip_duration_num);
        gradientButton = view.findViewById(R.id.button_filtertrip_apply);
        gradientButton.setVisibility(View.GONE);
        closeImageview = view.findViewById(R.id.iv_filter_trip_close);
        categorieList = new ArrayList<>();
        categoryNames = ((TripActivity)getActivity()).categories;
        numberPicker = view.findViewById(R.id.number_picker);
        tripSearchViewModel = ViewModelProviders.of(getActivity()).get(TripSearchViewModel.class);
    }


    private void showApplyButton() {
        if (gradientButton.getVisibility() == View.GONE)
        {
            gradientButton.setVisibility(View.VISIBLE);
        }
    }


    private void setupRecyclerview() {
        recyclerView = view.findViewById(R.id.rv_category);
        recyclerView.setLayoutManager( new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }


    private void getCategories() {
        tripSearchViewModel.initCategoryList();
        tripSearchViewModel.getCategoryList().observe(getActivity(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                if (categories != null) {
                    categorieList.clear();
                    categorieList.addAll(categories);
                    if (adapter == null)
                    {
//                        adapter = new CategoryRecyclerviewAddapter(categorieList, getContext(), CategotyFilterBottomSheetDialogFragment.this);
                        recyclerView.setAdapter(adapter);
                    }
                    else
                        adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public static TripFilterDialogFragment newInstance()
    {
        return new TripFilterDialogFragment();
    }

}
