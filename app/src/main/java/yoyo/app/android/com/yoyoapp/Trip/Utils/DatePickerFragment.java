package yoyo.app.android.com.yoyoapp.Trip.Utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.dagang.library.GradientButton;
import com.google.android.material.snackbar.Snackbar;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.TripActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class DatePickerFragment extends Fragment {

    private static final String TAG = "DatePickerBottomSheet";
    private Calendar start ,end ,defaultC = Calendar.getInstance();
    private TextView nightNumCalendarTextview;
    private String startDateString, endDateString ;
    private DateRangeCalendarView calendar;
    private long diffDays;
    private GradientButton fromDateButton ,toDateButton;
    private TextView toDateTextView, fromDateTextView;
    private ImageView closeButton;
    private Snackbar okSnackbar;
    private Typeface typeface;
    private Consumer<ArrayList<String>> consumer;
    private View view;

    public DatePickerFragment(Consumer<ArrayList<String>> consumer) {
        this.consumer = consumer;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_date_picker, container, false);

        init();
        setupDefaultDateValue();
        setupCalendar();
        setupCalendar();
        setupCloseButton();

        return view;
    }

    private void setupCloseButton() {
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }

    private void setupDefaultDateValue() {

        startDateString = getDayFormat(defaultC);
        defaultC.add(Calendar.DAY_OF_YEAR, +7);
        endDateString = getDayFormat(defaultC);



        diffDays = 7;
    }

    private void setupCalendar() {

        calendar.setCalendarListener(new DateRangeCalendarView.CalendarListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onFirstDateSelected(Calendar startDate) {
                start = startDate;
                end = null;

                checkInFunc();
            }

            @Override
            public void onDateRangeSelected(Calendar startDate, Calendar endDate) {
                calendar.setSelectedDateRange(startDate, endDate);
                start = startDate;
                end = endDate;

                long millis1 = start.getTimeInMillis();
                long millis2 = end.getTimeInMillis();
                long diff = millis2 - millis1;
                diffDays = diff / (24 * 60 * 60 * 1000);
                nightNumCalendarTextview.setText(String.valueOf(diffDays)+ " Nights");
                checkDuration();
            }
        });

        fromDateButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCalendar();
            }
        });
    }

    private void checkDuration() {
        if (diffDays > 45 || diffDays == 0)
        {
            resetCalendar();
            if (diffDays >45)
                showDurationErrorSnackBar("Duration must be less than 45 nights");
            else
                showDurationErrorSnackBar("Minimum Duration is 1 night");
        }
        else
        {
            checkOutFunc();
        }
    }

    private void init() {
        calendar = view.findViewById(R.id.calendar_datepicker);
        fromDateButton = view.findViewById(R.id.button_datepicker_from);
        toDateButton = view.findViewById(R.id.button_datepicker_to);
        closeButton = view.findViewById(R.id.iv_datepicker_close);
        nightNumCalendarTextview = view.findViewById(R.id.tv_datepicker_night_num);
        fromDateTextView =  fromDateButton.getButton();
        toDateTextView =  toDateButton.getButton();
        typeface = ResourcesCompat.getFont(getContext(),R.font.roboto_medium);

        calendar.setFonts(typeface);
    }

    private void checkOutFunc() {

        calculateEndDate();

        toDateTextView.setTextColor(Color.parseColor("#0265d3"));
        toDateTextView.setText(endDateString);
        fromDateTextView.setText(startDateString);
        okSnackbar = Snackbar.make(view, getActivity().getString(R.string.select_these_dates) + " "
                + startDateString + " - " + endDateString + " ?" , Snackbar.LENGTH_INDEFINITE);
        View sbView = okSnackbar.getView();
        sbView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupOkButton();
                okSnackbar.dismiss();
            }
        });
        sbView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
        TextView tv = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else {
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
        }
        okSnackbar.show();
    }

    private void checkInFunc() {

        calculateStartDate();

        fromDateTextView.setTextColor(Color.parseColor("#ffffffff"));
        fromDateTextView.setText(startDateString);
        fromDateButton.setButtonStartColor(Color.parseColor("#00000000"));
        fromDateButton.setButtonEndColor(Color.parseColor("#00000000"));

        toDateTextView.setText("Select Date");
        toDateButton.setButtonStartColor(Color.parseColor("#fede02"));
        toDateButton.setButtonEndColor(Color.parseColor("#fede02"));
        toDateTextView.setTextColor(Color.parseColor("#FFFFFFFF"));
    }

    private void calculateEndDate() {
        endDateString = getDayFormat(end);
        startDateString = getDayFormat(start);
        ((TripActivity)getContext()).fromTime = start.getTimeInMillis() / 1000;
        ((TripActivity)getActivity()).toTime = end.getTimeInMillis() / 1000;
        Log.d(TAG, "calculateEndDate: "+ start.getTimeInMillis());
        Log.d(TAG, "calculateStartDate2: "+ end.getTimeInMillis());
    }

    private void calculateStartDate() {
        startDateString = getDayFormat(start);
        ((TripActivity)getContext()).fromTime = start.getTimeInMillis() / 1000;
        Log.d(TAG, "calculateStartDate: "+ start.getTimeInMillis());
    }

    private void setupOkButton() {

        if (start != null && end != null)
        {
            saveResults(startDateString,endDateString);
        }
        else
        {
            Toast.makeText(getContext(),"Please select a date range",Toast.LENGTH_SHORT).show();
        }
    }

    public void saveResults(String from, String to)
    {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(from);
        arrayList.add(to);
        arrayList.add(String.valueOf(diffDays));
        arrayList.add(startDateString);
        arrayList.add(endDateString);
        consumer.accept(arrayList);
        ((TripActivity)getActivity()).diffDays = (int) diffDays;
        calendar.setEditable(true);
        getFragmentManager().popBackStack();
    }


    private void resetCalendar() {
        fromDateTextView.setText("Select Date");
        toDateTextView.setText("Select Date");
        nightNumCalendarTextview.setText("");
        calendar.setSelectedDateRange(null,null);

        toDateTextView.setTextColor(Color.parseColor("#ffffffff"));
        toDateButton.setButtonStartColor(Color.parseColor("#00000000"));
        toDateButton.setButtonEndColor(Color.parseColor("#00000000"));

        fromDateButton.setButtonStartColor(Color.parseColor("#fede02"));
        fromDateButton.setButtonEndColor(Color.parseColor("#fede02"));
        fromDateTextView.setTextColor(Color.parseColor("#0265d3"));
    }

    private void showDurationErrorSnackBar(String s) {
        Snackbar snackbar = Snackbar.make(view, s, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red));
        TextView errorDatePicker = (TextView) sbView.findViewById(R.id.snackbar_text);
        errorDatePicker.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        errorDatePicker.setTypeface(typeface);
        snackbar.show();
    }

    private String getDayFormat(Calendar calendar) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("E", Locale.getDefault());
        String dayOfWeekNameFrom = dayFormat.format(calendar.getTime());
        String monthNameFrom = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());

        return "" + dayOfWeekNameFrom + ", "
                + calendar.get(Calendar.DAY_OF_MONTH)
                + " " + monthNameFrom;
    }

}
