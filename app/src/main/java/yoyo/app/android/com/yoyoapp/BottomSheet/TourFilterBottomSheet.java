package yoyo.app.android.com.yoyoapp.BottomSheet;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import yoyo.app.android.com.yoyoapp.R;
import com.appyvet.materialrangebar.RangeBar;
import com.dagang.library.GradientButton;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;

import androidx.cardview.widget.CardView;

public class TourFilterBottomSheet implements RangeBar.OnRangeBarChangeListener {

    private static final String TAG = "TourFilterBottomSheet";
    private Context context;
    private View view;
    private RangeBar rangeBarPrice ,rangeBarDuration, rangeBarAge;
    private TextView rangebarPriceTextview, rangebarDurationTextview, rangebarAgeTextview;
    private GradientButton gradientButton;
    private BottomSheetBehavior bottomSheetBehavior;
    private MultiSelectToggleGroup multiTypes, multiLanguage;
    private RelativeLayout relativeLayout;
    private ImageView closeImageview;

    public TourFilterBottomSheet(Context context, View view) {
        this.context = context;
        this.view = view;
        init();
        setupMultySelect();
        setupCloseBottomSheet();
        setupApplyButton();
        rangeBarPrice.setOnRangeBarChangeListener(this);
        rangeBarAge.setOnRangeBarChangeListener(this);
        rangeBarDuration.setOnRangeBarChangeListener(this);
    }

    private void setupApplyButton() {
        gradientButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
    }

    private void setupCloseBottomSheet() {
        closeImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
    }

    private void setupMultySelect() {
        multiTypes.setOnCheckedChangeListener(new MultiSelectToggleGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked) {

                Log.d(TAG, "onCheckedStateChanged(): group.getCheckedIds() = " + group.getCheckedIds());
                showApplyButton();
            }
        });

        multiLanguage.setOnCheckedChangeListener(new MultiSelectToggleGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked) {

                Log.d(TAG, "onCheckedStateChanged(): group.getCheckedIds() = " + group.getCheckedIds());
                showApplyButton();
            }
        });
    }

    private void init() {
        relativeLayout = view.findViewById(R.id.bottom_sheet_tour_filter);
        bottomSheetBehavior = BottomSheetBehavior.from(relativeLayout);
        rangebarPriceTextview = view.findViewById(R.id.tv_filtertour_price_num);
        rangeBarPrice = view.findViewById(R.id.rangebar_filtertour_price);
        rangebarAgeTextview = view.findViewById(R.id.tv_filtertour_age_num);
        rangeBarAge = view.findViewById(R.id.rangebar_filtertour_age);
        rangebarDurationTextview = view.findViewById(R.id.tv_filtertour_duration_num);
        rangeBarDuration = view.findViewById(R.id.rangebar_filtertour_duration);
        gradientButton = view.findViewById(R.id.button_filtertour_apply);
        gradientButton.setVisibility(View.GONE);
        multiTypes = view.findViewById(R.id.groupbutton_filtertour);
        multiLanguage = view.findViewById(R.id.group_choices_filtertour_language);
        closeImageview = view.findViewById(R.id.iv_filter_tour_close);
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
            case R.id.rangebar_filtertour_age:
                rangebarAgeTextview.setText(String.valueOf(leftPinValue) + " - " + String.valueOf(rightPinValue));
                break;
            case R.id.rangebar_filtertour_duration:
                rangebarDurationTextview.setText(String.valueOf(rightPinIndex +1) + " × Day");
                break;
            case R.id.rangebar_filtertour_price:
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
}
