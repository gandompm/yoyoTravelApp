package yoyo.app.android.com.yoyoapp.Flight.BottomSheet;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import yoyo.app.android.com.yoyoapp.R;

public class TravellerBottomSheetDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private ImageView plusAdultImageview, minusAdultImageview, plusChildImageview, minusChildImageview, plusinfantImageview, minusInfantImageview ,closeImageview;
    private Button applyTravellerButton;
    private BotomSheetListener bottomSheetListener;
    private TextView adultTextview, childTextview, infantTextview;
    private int adultNum= 1, childNum= 0, infantNum =0, sum = 1;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_traveller_bottom_sheet_dialog, container, false);

        init();

        applyTravellerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // store these values in main activity for next usages
                bottomSheetListener.onButtonClicked(sum,adultNum,childNum,infantNum);
                dismiss();
            }
        });


        return view;
    }

    private void init() {
        plusAdultImageview = view.findViewById(R.id.iv_flight_plus);
        minusAdultImageview = view.findViewById(R.id.iv_flight_minus);
        plusChildImageview = view.findViewById(R.id.iv_flight_plus_child);
        minusChildImageview = view.findViewById(R.id.iv_flight_minus_child);
        plusinfantImageview = view.findViewById(R.id.iv_flight_plus_infant);
        minusInfantImageview = view.findViewById(R.id.iv_flight_minus_infant);
        applyTravellerButton = view.findViewById(R.id.button_flight_apply_traveller);
        adultTextview = view.findViewById(R.id.tv_flight_num);
        childTextview = view.findViewById(R.id.tv_flight_num_child);
        infantTextview = view.findViewById(R.id.tv_flight_num_infant);
        closeImageview = view.findViewById(R.id.iv_close_bottom_sheet);
        closeImageview.setOnClickListener(this);
        plusinfantImageview.setOnClickListener(this);
        plusChildImageview.setOnClickListener(this);
        plusAdultImageview.setOnClickListener(this);
        minusInfantImageview.setOnClickListener(this);
        minusChildImageview.setOnClickListener(this);
        minusAdultImageview.setOnClickListener(this);
    }

    // setup add and Subtraction button for adult, child and infant's number
    @Override
    public void onClick(View v) {
        applyTravellerButton.setVisibility(View.VISIBLE);
        switch (v.getId())
        {
            case R.id.iv_flight_plus:
                if (adultNum < 9) {
                    adultNum++;
                    sum++;
                    adultTextview.setText(String.valueOf(adultNum));
                }
                if (adultNum ==2)
                {
                    appearView(minusAdultImageview, adultTextview);
                }
                break;
            case R.id.iv_flight_minus:
                if (adultNum > 1) {
                    adultNum--;
                    sum--;
                    adultTextview.setText(String.valueOf(adultNum));
                }
                if (adultNum == 1)
                {
                    minusAdultImageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_light_24dp));
                }
                break;
            case R.id.iv_flight_plus_child:
                if (childNum < 9) {
                    childNum++;
                    sum++;
                    childTextview.setText(String.valueOf(childNum));
                }
                if (childNum == 1)
                {
                    appearView(minusChildImageview, childTextview);
                }
                    break;
            case R.id.iv_flight_minus_child:
                if (childNum > 0) {
                    childNum--;
                    sum--;
                    childTextview.setText(String.valueOf(childNum));
                }
                if (childNum == 0)
                {
                    disappearView(minusChildImageview, childTextview);
                }
                break;
            case R.id.iv_flight_minus_infant:
                if (infantNum > 0) {
                    infantNum--;
                    sum--;
                    infantTextview.setText(String.valueOf(infantNum));
                }
                if (infantNum== 0)
                {
                    disappearView(minusInfantImageview, infantTextview);
                }
                break;
            case R.id.iv_flight_plus_infant:
                if (infantNum < 9) {
                    infantNum++;
                    sum++;
                    infantTextview.setText(String.valueOf(infantNum));
                }
                if (infantNum==1)
                {
                    appearView(minusInfantImageview, infantTextview);
                }
                break;
            case R.id.iv_close_bottom_sheet:
                dismiss();
                break;

        }
    }

    // callback when the user select apply button
    public interface BotomSheetListener
    {
        // this method implements in main activity
        void onButtonClicked(int sum, int adultNum, int childNum, int infantNum);
    }

    // the minus button appear when the number is more than 0
    public void appearView(ImageView imageView,TextView textView)
    {
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_black_24dp));
        textView.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    // the minus button disappear when the number is equals to  0
    public void disappearView(ImageView imageView,TextView textView)
    {
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_light_24dp));
        textView.setTextColor(getResources().getColor(R.color.white1));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        bottomSheetListener = (BotomSheetListener) context;
    }
}
