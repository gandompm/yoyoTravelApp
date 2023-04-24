package yoyo.app.android.com.yoyoapp.Flight.BottomSheet;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.appyvet.materialrangebar.RangeBar;
import com.dagang.library.GradientButton;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;
import yoyo.app.android.com.yoyoapp.Flight.Dialog.AirCraftDialogFragment;
import yoyo.app.android.com.yoyoapp.Flight.Dialog.AirlineDialogFragment;
import yoyo.app.android.com.yoyoapp.Flight.MainFlightActivity;
import yoyo.app.android.com.yoyoapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FlightFilterBottomSheet  {

    private static final String TAG = "TripFilterBottomSheet";
    private FloatingActionButton floatingActionButton;
    private Context context;
    private View view;
    private Button aircraftsButton, airlineButton;
    private RangeBar rangeBarPrice;
    private TextView rangebarPriceTextview;
    private GradientButton gradientButton;
    private BottomSheetBehavior bottomSheetBehavior;
    private MultiSelectToggleGroup dayTimemultiSelectToggleGroup;
    private TextView resetFilterTextview;
    private RelativeLayout relativeLayout;
    private ImageView closeImageview;
    private ArrayList<String> daytimesString;
    private OnsendDataToResultPage onsendDataToResultPage;
    private String minPrice = "25000", maxPrice = "20000000";


    public FlightFilterBottomSheet(final Context context, final View view , OnsendDataToResultPage onsendDataToResultPage) {
        this.context = context;
        this.view = view;
        this.onsendDataToResultPage = onsendDataToResultPage;
        init();
        setupCloseBottomSheet();
        setupApplyButton();
        setupAirlineButton();
        setupAircraftButton();


        // setup range bar for price
        rangeBarPrice.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                minPrice =leftPinValue;
                maxPrice = rightPinValue;
                minPrice += "000";
                maxPrice += "000";
                DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
                String minimum = decimalFormat.format(Integer.valueOf(minPrice));
                String maximum = decimalFormat.format(Integer.valueOf(maxPrice));
                rangebarPriceTextview.setText(minimum + " - " + maximum);
                if (gradientButton.getVisibility() == View.GONE)
                {
                    showApplyButton();
                }
            }
        });

        // show filter bottom sheet
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                floatingActionButton.hide();
            }
        });

        // reset all filters
        resetFilterTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((MainFlightActivity)context).iataCodeAirlines.clear();
//                ((MainFlightActivity)context).idAircrafts.clear();
//                ((MainFlightActivity)context).dayTimes.clear();
                dayTimemultiSelectToggleGroup.clearCheck();
                rangeBarPrice.setRangePinsByValue(25,5500);
                minPrice = "0";
                maxPrice = "9000000";
                airlineButton.setBackground(context.getResources().getDrawable(R.drawable.blue_round_border_bg));
                airlineButton.setTextColor(context.getResources().getColor(R.color.black2));
                aircraftsButton.setBackground(context.getResources().getDrawable(R.drawable.blue_round_border_bg));
                aircraftsButton.setTextColor(context.getResources().getColor(R.color.black2));
            }
        });

        dayTimemultiSelectToggleGroup.setOnCheckedChangeListener(new MultiSelectToggleGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked) {
                Log.d(TAG, "onCheckedStateChanged: "+ checkedId  + " -> " + isChecked);
                String dayTime ="";
                switch (checkedId)
                {
                    case 2131361925:
                        dayTime = "MIDNIGHT";
                        break;
                    case 2131361926:
                        dayTime = "MORNING";
                        break;
                    case 2131361923:
                        dayTime = "AFTERNOON";
                        break;
                    case 2131361927:
                        dayTime = "NIGHT";
                        break;
                }
//                if (!daytimesString.contains(dayTime))
//                    daytimesString.add(dayTime);
//                else
//                    daytimesString.remove(dayTime);
                // save day time to main activity
//                ((MainFlightActivity)context).dayTimes = daytimesString;
//                Log.d(TAG, "onCheckedStateChanged:1 "+ ((MainFlightActivity)context).dayTimes);
                showApplyButton();
            }
        });


    }

    // close bottom sheet
    private void setupCloseBottomSheet() {
        closeImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                floatingActionButton.show();
            }
        });
    }


    private void init() {
//        daytimesString = ((MainFlightActivity)context).dayTimes;
        resetFilterTextview = view.findViewById(R.id.tv_flight_filter_reset);
        floatingActionButton = view.findViewById(R.id.fbutton_flightresult);
        relativeLayout = view.findViewById(R.id.bottom_sheet_flight_filter);
        bottomSheetBehavior = BottomSheetBehavior.from(relativeLayout);
        rangebarPriceTextview = view.findViewById(R.id.tv_filterflight_price_num);
        rangeBarPrice = view.findViewById(R.id.rangebar_filterflight_price);
        gradientButton = view.findViewById(R.id.button_filterflight_apply);
        gradientButton.setVisibility(View.GONE);
        closeImageview = view.findViewById(R.id.iv_filter_flight_close);
        airlineButton = view.findViewById(R.id.button_flightresult_airline);
        aircraftsButton = view.findViewById(R.id.button_flightresult_aircraft);
        dayTimemultiSelectToggleGroup = view.findViewById(R.id.groupbutton_filterflight);
    }

    // visible apply button
    private void showApplyButton() {
        if (gradientButton.getVisibility() == View.GONE)
        {
            gradientButton.setVisibility(View.VISIBLE);
        }
        resetFilterTextview.setVisibility(View.VISIBLE);
    }

    // callback for when the user click apply
    public interface OnsendDataToResultPage
    {
        void onRecieved(String minPrice, String maxPrice);
    }

    // user click apply button
    private void setupApplyButton() {
        gradientButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                // send max and max and call onrecieved method
                onsendDataToResultPage.onRecieved(minPrice,maxPrice);
                floatingActionButton.show();
            }
        });
    }

    // send user to airline dialog fragment
        private void setupAirlineButton() {
        airlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AirlineDialogFragment airlineDialogFragment = new AirlineDialogFragment();
//                airlineDialogFragment.show(((MainFlightActivity)context).getSupportFragmentManager(), "airline list");
                showApplyButton();
                airlineButton.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_roundshape));
                airlineButton.setTextColor(context.getResources().getColor(R.color.white));
            }
        });
    }

    // send user to aircaraft dialog fragment
    private void setupAircraftButton() {
        aircraftsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AirCraftDialogFragment airCraftDialogFragment = new AirCraftDialogFragment();
//                airCraftDialogFragment.show(((MainFlightActivity)context).getSupportFragmentManager(), "aircraft list");
                showApplyButton();
                aircraftsButton.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_roundshape));
                aircraftsButton.setTextColor(context.getResources().getColor(R.color.white));
            }
        });
    }
}
