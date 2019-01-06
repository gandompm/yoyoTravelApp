package yoyo.app.android.com.yoyoapp.BottomSheet;


import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import yoyo.app.android.com.yoyoapp.R;
import com.appyvet.materialrangebar.RangeBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class PriceFilterBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private TextView rangebarTextview;
    private Button applyButton;
    private RangeBar rangeBar;
    private String leftIndex ,rightIndex;
    private BotomSheetListener bottomSheetListener;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_sheet_filter_price,container,false);


        init();
        setupApplyButton();
        setupRangeBar();

        return view;
    }

    private void setupRangeBar() {
        rangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex, String leftPinValue, String rightPinValue) {
                leftIndex = leftPinValue;
                rightIndex = rightPinValue;
                rangebarTextview.setText("From " + String.valueOf(leftPinValue) + "$ to " + String.valueOf(rightPinValue) + "$");

            }
        });
    }

    private void setupApplyButton() {
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetListener.onApplyClicked("From " + String.valueOf(leftIndex) + "$ to " + String.valueOf(rightIndex) + "$");
                dismiss();
            }
        });
    }

    private void init() {
        rangebarTextview = view.findViewById(R.id.tv_filter_price_filter);
        applyButton = view.findViewById(R.id.button_filter_price_apply);
        rangeBar = view.findViewById(R.id.rangebar_filter_price);
    }

    public static PriceFilterBottomSheetDialogFragment newInstance()
    {
        return new PriceFilterBottomSheetDialogFragment();
    }

    public interface BotomSheetListener
    {
        void onApplyClicked(String s);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        bottomSheetListener = (BotomSheetListener) context;
    }
}
