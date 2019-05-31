package yoyo.app.android.com.yoyoapp.Trip.result;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.appyvet.materialrangebar.RangeBar;
import com.dagang.library.GradientButton;
import yoyo.app.android.com.yoyoapp.DataModels.Category;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.TripActivity;
import yoyo.app.android.com.yoyoapp.Trip.adapter.CategoryRecyclerviewAddapter;
import yoyo.app.android.com.yoyoapp.Trip.search.TripSearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class FilterTripFragment extends Fragment implements RangeBar.OnRangeBarChangeListener{

    private RangeBar rangeBarPrice ,rangeBarDuration;
    private TextView rangebarPriceTextview, rangebarDurationTextview;
    private GradientButton gradientButton;
    private ImageView closeImageview;
    private ArrayList<Category> categorieList;
    private RecyclerView recyclerView;
    private CategoryRecyclerviewAddapter adapter;
    private ArrayList<String> categoryNames;
    private TripSearchViewModel tripSearchViewModel;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_sheet_filter_trip,container,false);


        init();
        setupCloseBottomSheet();
        setupApplyButton();
        rangeBarPrice.setOnRangeBarChangeListener(this);
        rangeBarDuration.setOnRangeBarChangeListener(this);

        return view;
    }

    private void setupApplyButton() {
        gradientButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setupCloseBottomSheet() {
        closeImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private void init() {
        rangebarPriceTextview = view.findViewById(R.id.tv_filtertrip_price_num);
        rangeBarPrice = view.findViewById(R.id.rangebar_filtertrip_price);
        rangebarDurationTextview = view.findViewById(R.id.tv_filtertrip_duration_num);
        rangeBarDuration = view.findViewById(R.id.rangebar_filtertrip_duration);
        gradientButton = view.findViewById(R.id.button_filtertrip_apply);
        gradientButton.setVisibility(View.GONE);
        closeImageview = view.findViewById(R.id.iv_filter_trip_close);
        categorieList = new ArrayList<>();
        categoryNames = ((TripActivity)getActivity()).categories;
        tripSearchViewModel = ViewModelProviders.of(getActivity()).get(TripSearchViewModel.class);
    }


    private void showApplyButton() {
        if (gradientButton.getVisibility() == View.GONE)
        {
            gradientButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
        switch (rangeBar.getId())
        {
            case R.id.rangebar_filtertrip_duration:
                rangebarDurationTextview.setText(String.valueOf(rightPinIndex +1) + " × Day");
                break;
            case R.id.rangebar_filtertrip_price:
                rangebarPriceTextview.setText(String.valueOf(leftPinValue) + " - " + String.valueOf(rightPinValue) + " $");
                break;
            default:
                break;
        }
        if (gradientButton.getVisibility() == View.GONE)
        {
            showApplyButton();
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

}
