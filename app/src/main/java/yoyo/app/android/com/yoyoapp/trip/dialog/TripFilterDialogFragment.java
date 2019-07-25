package yoyo.app.android.com.yoyoapp.trip.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.util.Consumer;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.appyvet.materialrangebar.RangeBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.shawnlin.numberpicker.NumberPicker;
import yoyo.app.android.com.yoyoapp.DataModels.Category;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.SharedDataViewModel;
import yoyo.app.android.com.yoyoapp.trip.adapter.CategoryRecyclerviewAddapter;
import yoyo.app.android.com.yoyoapp.trip.search.TourSearchViewModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TripFilterDialogFragment extends BottomSheetDialogFragment {

    private static final String TAG = "TripFilterDialogFragmen";
    private RangeBar rangeBarPrice;
    private TextView rangebarPriceTextview, rangebarDurationTextview;
    private Button applyButton;
    private Button cancelButton;
    private ArrayList<Category> categorieList;
    private RecyclerView recyclerView;
    private CategoryRecyclerviewAddapter adapter;
    private ArrayList<Category> selectedCategories;
    private TourSearchViewModel tourSearchViewModel;
    private NumberPicker numberPicker;
    private String minimum, maximum;
    private String minPrice = "10", maxPrice = "5500";
    private boolean hasPriceChanged = false, hasDurationChanged = false;
    private int duration = 6;
    private View view;
    private SharedDataViewModel sharedDataViewModel;
    private Consumer<Boolean> applyConsumer;

    public TripFilterDialogFragment(Consumer<Boolean> applyConsumer) {
        this.applyConsumer = applyConsumer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_sheet_filter_trip, container, false);

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
        if (sharedDataViewModel.getMinDuration().getValue() != 1)
            numberPicker.setValue(sharedDataViewModel.getMinDuration().getValue());
    }

    private void setupNumberPicker() {
        // Using string values
        // IMPORTANT! setMinValue to 1 and call setDisplayedValues after setMinValue and setMaxValue
        numberPicker.setOrientation(NumberPicker.HORIZONTAL);
        String[] data = {"1 Days", "2 Days", "3 Days", "4 Days", "5 Days", "6 Days", "7 Days",
                "8 Days", "9 Days", "10 Days", "11 Days", "12 Days", "13 Days"};
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);

        // OnValueChangeListener
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hasDurationChanged = true;
                rangebarDurationTextview.setText(newVal + " Days");
                duration = newVal;
                showApplyButton();
            }
        });
    }

    private void setupApplyButton() {
        if (hasPriceChanged) {
            sharedDataViewModel.selectFromPrice(Integer.parseInt(minPrice));
            sharedDataViewModel.selectToPrice(Integer.parseInt(maxPrice));
        }
        if (hasDurationChanged) {
            sharedDataViewModel.selectMinDuration(duration);
        }
        sharedDataViewModel.selectCategories(selectedCategories);
        dismiss();
        applyConsumer.accept(true);
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
        numberPicker = view.findViewById(R.id.number_picker);
        tourSearchViewModel = ViewModelProviders.of(getActivity()).get(TourSearchViewModel.class);
        sharedDataViewModel = ViewModelProviders.of(getActivity()).get(SharedDataViewModel.class);
        sharedDataViewModel.getCategories().observe(getActivity(), categories -> selectedCategories = categories);
        selectedCategories = sharedDataViewModel.getCategories().getValue();
    }


    private void showApplyButton() { applyButton.setTextColor(ContextCompat.getColor(getContext(), R.color.green)); }

    private void setupRecyclerview() {
        recyclerView = view.findViewById(R.id.rv_filtertour_types);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }


    private void getCategories() {
        tourSearchViewModel.initCategories();
        tourSearchViewModel.getCategories().observe(getActivity(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                if (categories != null) {
                    categorieList.clear();
                    categorieList.addAll(categories);
                    if (adapter == null) {
                        adapter = new CategoryRecyclerviewAddapter(categorieList, getContext(), category -> {
                            showApplyButton();
                            if (selectedCategories.contains(category)) {
                                selectedCategories.remove(category);
                            } else {
                                selectedCategories.add(category);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    } else
                        adapter.notifyDataSetChanged();
                }
            }
        });
    }

}
