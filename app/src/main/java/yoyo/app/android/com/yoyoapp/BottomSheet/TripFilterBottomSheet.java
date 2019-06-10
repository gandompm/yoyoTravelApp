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

public class TripFilterBottomSheet implements RangeBar.OnRangeBarChangeListener {

    private static final String TAG = "TripFilterBottomSheet";
    private Context context;
    private View view;
    private RangeBar rangeBarPrice ,rangeBarDuration;
    private TextView rangebarPriceTextview, rangebarDurationTextview;
    private GradientButton gradientButton;
    private ImageView closeImageview;

    public TripFilterBottomSheet(Context context, View view) {
        this.context = context;
        this.view = view;
        init();
        setupCloseBottomSheet();
        setupApplyButton();
        rangeBarPrice.setOnRangeBarChangeListener(this);
        rangeBarDuration.setOnRangeBarChangeListener(this);
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
        gradientButton = view.findViewById(R.id.button_filtertrip_apply);
        gradientButton.setVisibility(View.GONE);
        closeImageview = view.findViewById(R.id.iv_filter_trip_close);
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
//            case R.id.rangebar_filtertrip_duration:
//                rangebarDurationTextview.setText(String.valueOf(rightPinIndex +1) + " × Day");
//                break;
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
}
