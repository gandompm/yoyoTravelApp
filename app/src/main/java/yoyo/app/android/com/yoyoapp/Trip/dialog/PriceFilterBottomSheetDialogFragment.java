package yoyo.app.android.com.yoyoapp.Trip.dialog;


import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import yoyo.app.android.com.yoyoapp.R;
import com.appyvet.materialrangebar.RangeBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import yoyo.app.android.com.yoyoapp.Trip.TripActivity;

import java.text.DecimalFormat;


public class PriceFilterBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private TextView rangebarTextview, resetTextview;
    private Button applyButton;
    private RangeBar rangeBar;
    private BotomSheetListener bottomSheetListener;
    private ImageView closeImageview;
    private String minimum, maximum;
    private String minPrice = "10", maxPrice = "5500";
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_sheet_filter_price,container,false);

        init();
        setupApplyButton();
        setupRangeBar();
        setupCloseButton();
        setupResetButton();

        return view;
    }

    private void setupResetButton() {
        resetTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TripActivity)getActivity()).fromPrice = 0;
                ((TripActivity)getActivity()).toPrice = 20000000;
                rangeBar.setRangePinsByValue(10,5500);
            }
        });
    }


    private void setupCloseButton() {
        closeImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void setupRangeBar() {
        rangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex, String leftPinValue, String rightPinValue) {
                minPrice = leftPinValue;
                maxPrice = rightPinValue;
//                minPrice += "000";
//                maxPrice += "000";
                DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
                minimum = decimalFormat.format(Integer.valueOf(minPrice));
                maximum = decimalFormat.format(Integer.valueOf(maxPrice));

                if (applyButton.getVisibility() == View.GONE)
                {
                    showApplyButton();
                }


                rangebarTextview.setText("From " + minimum + "$ to " + maximum + "$");

            }
        });
    }

    // visible apply button
    private void showApplyButton() {
        if (applyButton.getVisibility() == View.GONE)
        {
            applyButton.setVisibility(View.VISIBLE);
        }
        resetTextview.setVisibility(View.VISIBLE);
    }

    private void setupApplyButton() {
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetListener.onApplyClicked(minPrice ,maxPrice);
                dismiss();
            }
        });
    }

    private void init() {
        rangebarTextview = view.findViewById(R.id.tv_filter_price_filter);
        applyButton = view.findViewById(R.id.button_filter_price_apply);
        rangeBar = view.findViewById(R.id.rangebar_filter_price);
        closeImageview = view.findViewById(R.id.iv_filter_price_close);
        resetTextview = view.findViewById(R.id.tv_filter_price_reset);
    }

    public static PriceFilterBottomSheetDialogFragment newInstance()
    {
        return new PriceFilterBottomSheetDialogFragment();
    }

    public interface BotomSheetListener
    {
        void onApplyClicked(String minimum, String maximum);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        bottomSheetListener = (BotomSheetListener) context;
    }
}
