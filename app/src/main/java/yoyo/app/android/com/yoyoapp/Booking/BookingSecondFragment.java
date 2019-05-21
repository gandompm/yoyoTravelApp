package yoyo.app.android.com.yoyoapp.Booking;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.uniquestudio.library.CircleCheckBox;
import yoyo.app.android.com.yoyoapp.Flight.ApiServiceFlight;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.Payment;
import yoyo.app.android.com.yoyoapp.Flight.Utils.SnapUpCountDownTimerView;
import yoyo.app.android.com.yoyoapp.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class BookingSecondFragment extends Fragment {

    private static final String TAG = "BookingSecondFragment";
    private CircleCheckBox circleCheckBox;
    private SnapUpCountDownTimerView rushBuyCountDownTimerView;
    private ApiServiceFlight apiServiceFlight;
    private String paymentDaedlineString;
    private int elapsedHours, elapsedMinutes,elapsedSeconds;
    private View view;
    private int different;
    private DecimalFormat decimalFormat;
    private Date serverDeadlineDate = null , nowDate = null;
    private TextView departureTextView, destinationTextView, adultCountTextView, adultPriceTextView, childcountTextView, childPriceTextView,
                     infantCountTextView, infantPriceTextView, totalPriceTextview;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_booking_second, container, false);
        ((BookingActivity)getContext()).continueButton.setVisibility(View.GONE);

        init();

        // get final details for buying tickets
        apiServiceFlight.getPriceDetails(((BookingActivity)getActivity()).voucherNumber,new ApiServiceFlight.OnPriceDetailsRecieved() {
            @Override
            public void onRecieved(Payment payment) {
                if (payment!=null)
                {

                    paymentDaedlineString = payment.getDeadlineTime();
                    Log.d(TAG, "onRecievedxxxxxxxxxx: " + paymentDaedlineString);
                    departureTextView.setText(payment.getOriginCity());
                    destinationTextView.setText(payment.getDestinationCity());
                    adultCountTextView.setText(String.valueOf(payment.getAdultCount()));
                    adultPriceTextView.setText( decimalFormat.format(Double.valueOf(payment.getAdultPrice())));
                    childcountTextView.setText(String.valueOf(payment.getChildCount()));
                    childPriceTextView.setText( decimalFormat.format(Double.valueOf(payment.getChildPrice())));
                    infantCountTextView.setText(String.valueOf(payment.getInfantCount()));
                    infantPriceTextView.setText( decimalFormat.format(Double.valueOf(payment.getInfantPrice())));
                    totalPriceTextview.setText( decimalFormat.format(Double.valueOf(payment.getTotalPrice())));
                    reformatDates();
                }
            }
        });



        circleCheckBox.setListener(new CircleCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(boolean isChecked) {
                if (isChecked)
                {
                    ((BookingActivity)getContext()).continueButton.setVisibility(View.VISIBLE);
                }
                else
                {
                    ((BookingActivity)getContext()).continueButton.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    private void setupDownCounter() {
        rushBuyCountDownTimerView = (SnapUpCountDownTimerView) view.findViewById(R.id.RushBuyCountDownTimerView);
        Log.d(TAG, "setupDownCounter: xxxxxx" + elapsedHours + " " + elapsedMinutes + " " + elapsedSeconds);


        //TODO:Fast Solution fix it later

        elapsedHours+=3;
        elapsedMinutes+=26;
        elapsedSeconds=0;


        rushBuyCountDownTimerView.setTime(elapsedHours,elapsedMinutes,elapsedSeconds);
        rushBuyCountDownTimerView.start();
    }

    private void reformatDates() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss" );
        TimeZone tehran = TimeZone.getTimeZone("Asia/Tehran");
        Calendar now = Calendar.getInstance(tehran);
        try {

             serverDeadlineDate = format.parse(paymentDaedlineString);
             nowDate = now.getTime();
            ((BookingActivity)getActivity()).serverDeadlineDate = serverDeadlineDate;
            ((BookingActivity)getActivity()).nowDate = nowDate;
            calculateDateDifference(nowDate, serverDeadlineDate);
             setupDownCounter();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        decimalFormat = new DecimalFormat("#,###,###");
        circleCheckBox =  view.findViewById(R.id.circlecheckbox_bookingsecond);
        departureTextView = view.findViewById(R.id.tv_booking_second_departure);
        destinationTextView = view.findViewById(R.id.tv_booking_second_destination);
        adultCountTextView = view.findViewById(R.id.tv_booking_second_adult_count);
        adultPriceTextView = view.findViewById(R.id.tv_booking_second_adult_price);
        childcountTextView = view.findViewById(R.id.tv_booking_second_child_count);
        childPriceTextView = view.findViewById(R.id.tv_booking_second_child_price);
        infantCountTextView = view.findViewById(R.id.tv_booking_second_infant_count);
        infantPriceTextView = view.findViewById(R.id.tv_booking_second_infant_price);
        totalPriceTextview = view.findViewById(R.id.tv_booking_second_total_price);
        apiServiceFlight = new ApiServiceFlight(getContext());
    }

    // calculate date difference
    public void calculateDateDifference(Date startDate, Date endDate) {
        //milliseconds
        different = (int) (endDate.getTime() - startDate.getTime());

        int secondsInMilli = 1000;
        int minutesInMilli = secondsInMilli * 60;
        int hoursInMilli = minutesInMilli * 60;
        int daysInMilli = hoursInMilli * 24;

        int elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        elapsedSeconds = different / secondsInMilli;
    }



}
