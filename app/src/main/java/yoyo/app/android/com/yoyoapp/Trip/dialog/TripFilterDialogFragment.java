package yoyo.app.android.com.yoyoapp.Trip.dialog;

import android.os.Bundle;
import android.widget.Button;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.appyvet.materialrangebar.RangeBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.shawnlin.numberpicker.NumberPicker;
import yoyo.app.android.com.yoyoapp.DataModels.Category;
import yoyo.app.android.com.yoyoapp.MainActivity;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.adapter.CategoryRecyclerviewAddapter;
import yoyo.app.android.com.yoyoapp.Trip.result.TripResultFragment;
import yoyo.app.android.com.yoyoapp.Trip.search.TripSearchFragment;
import yoyo.app.android.com.yoyoapp.Trip.search.TripSearchViewModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TripFilterDialogFragment extends BottomSheetDialogFragment{

    private static final String TAG = "TripFilterDialogFragmen";
    private RangeBar rangeBarPrice;
    private TextView rangebarPriceTextview, rangebarDurationTextview;
    private Button applyButton;
    private Button cancelButton;
    private ArrayList<Category> categorieList;
    private RecyclerView recyclerView;
    private CategoryRecyclerviewAddapter adapter;
    private ArrayList<Category> selectedCategories;
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
        setupRecyclerview();
        initFilter();
        getCategories();
        applyButton.setOnClickListener(v -> setupApplyButton());
        cancelButton.setOnClickListener(v -> dismiss());



        return view;
    }

    private void initFilter() {
        if (((MainActivity) getActivity()).getMinDuration() != 1)
        numberPicker.setValue(((MainActivity) getActivity()).getMinDuration());
    }

    private void setupNumberPicker() {
        // Using string values
        // IMPORTANT! setMinValue to 1 and call setDisplayedValues after setMinValue and setMaxValue
        numberPicker.setOrientation(NumberPicker.HORIZONTAL);
        String[] data = {"1 Days", "2 Days", "3 Days", "4 Days", "5 Days", "6 Days", "7 Days",
                "8 Days", "9 Days","10 Days","11 Days","12 Days","13 Days"};
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);

        // OnValueChangeListener
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hasDurationChanged = true;
                rangebarDurationTextview.setText(newVal +" Days");
                duration = newVal;
                showApplyButton();
            }
        });
    }

    private void setupApplyButton() {
        if (hasPriceChanged)
        {
            ((MainActivity) getActivity()).setFromPrice(Integer.parseInt(minPrice));
            ((MainActivity) getActivity()).setToPrice(Integer.parseInt(maxPrice));
        }
        if (hasDurationChanged) {
            ((MainActivity) getActivity()).setMinDuration(duration);
        }
        dismiss();
        getActivity().onBackPressed();
        ((MainActivity)getActivity()).showFragment(new TripSearchFragment(),new TripResultFragment(),false);
    }

    private void setupPriceRangeBar() {
        rangeBarPrice.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                hasPriceChanged = true;
                minPrice = leftPinValue;
                maxPrice = rightPinValue;

                DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
                minimum = decimalFormat.format(Integer.valueOf(minPrice));
                maximum = decimalFormat.format(Integer.valueOf(maxPrice));

                showApplyButton();

                rangebarPriceTextview.setText("From " + minimum + "$ to " + maximum + "$");
            }
        });
    }


    private void init() {
        rangebarPriceTextview = view.findViewById(R.id.tv_filtertrip_price_num);
        rangeBarPrice = view.findViewById(R.id.rangebar_filtertrip_price);
        rangebarDurationTextview = view.findViewById(R.id.tv_filtertrip_duration_num);
        applyButton = view.findViewById(R.id.button_filtertrip_apply);
        cancelButton = view.findViewById(R.id.button_filtertrip_cancel);
        categorieList = new ArrayList<>();
        selectedCategories = ((MainActivity) getActivity()).getCategories();
        numberPicker = view.findViewById(R.id.number_picker);
        tripSearchViewModel = ViewModelProviders.of(getActivity()).get(TripSearchViewModel.class);
    }


    private void showApplyButton() {
       applyButton.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
    }


    private void setupRecyclerview() {
        recyclerView = view.findViewById(R.id.rv_filtertour_types);
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
                        adapter = new CategoryRecyclerviewAddapter(categorieList, getContext(), category -> {
                            showApplyButton();
                            if (selectedCategories.contains(category)) {
                                selectedCategories.remove(category);
                            } else {
                                selectedCategories.add(category);
                            }
                        });
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
