package yoyo.app.android.com.yoyoapp.Flight.BottomSheet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.dagang.library.GradientButton;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import yoyo.app.android.com.yoyoapp.Flight.FlightSearch.FlightSearchFragment;
import yoyo.app.android.com.yoyoapp.Flight.MainFlightActivity;
import yoyo.app.android.com.yoyoapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatePickerBottomSheet {
    
    private Context context;
    private Calendar start ,end ,defaultC = Calendar.getInstance();
    private TextView dateTextview, dateOfWeekTextview,checkOutTitle;
    private TextView nightNumCalendarTextview ,nullTextview;
    public  String startDateString, endDateString, toDayOfWeek ,fromDayOfWeek;
    private DateRangeCalendarView calendar;
    private long diffDays;
    private GradientButton fromDateButton ,toDateButton;
    private TextView toDateTextView, fromDateTextView;
    private BottomSheetBehavior bottomSheetBehavior;
    private ImageView closeButton;
    private Snackbar okSnackbar;
    private View view;
    private Typeface typeface;

    public DatePickerBottomSheet(Context context, View view) {
        this.context = context;
        this.view = view;
        init();
        setupDefaultDateValue();
        setupCalendar();
        setupCloseButton();
        checkInFunc();
    }

    // close date picker bottom sheet
    private void setupCloseButton() {
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                if (okSnackbar!= null)
                okSnackbar.dismiss();
            }
        });
    }

    private void setupDefaultDateValue() {

        startDateString = getDayFormat(defaultC);
        fromDayOfWeek = getDayOfWeekName(defaultC);
        defaultC.add(Calendar.DAY_OF_YEAR, +7);
        endDateString = getDayFormat(defaultC);
        diffDays = 7;
        toDayOfWeek = getDayOfWeekName(defaultC);
        ((MainFlightActivity)context).standardDate = Calendar.getInstance();
    }

    // setup calender
    private void setupCalendar() {

        calendar.setCalendarListener(new DateRangeCalendarView.CalendarListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onFirstDateSelected(Calendar startDate) {
                start = startDate;
                end = null;
                calculateStartDate();
                checkInFunc();

                if (FlightSearchFragment.isOneWay)
                {
                    // start and end date are same
                    onDateRangeSelected(startDate,startDate);
                }
            }

            @Override
            public void onDateRangeSelected(Calendar startDate, Calendar endDate) {
                calendar.setSelectedDateRange(startDate, endDate);
                start = startDate;
                end = endDate;

                if (!FlightSearchFragment.isOneWay) {
                    long millis1 = start.getTimeInMillis();
                    long millis2 = end.getTimeInMillis();
                    long diff = millis2 - millis1;
                    diffDays = diff / (24 * 60 * 60 * 1000);
                    nightNumCalendarTextview.setText(String.valueOf(diffDays) + " Nights");
                    checkDuration();
                }
                else
                {
                    checkOutFunc();
                }
            }
        });

        fromDateButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetCalendar();

            }
        });
    }

    //
    private void checkDuration() {
        if (diffDays > 10 || diffDays == 0)
        {
            resetCalendar();
            if (diffDays >10)
                showDurationErrorSnackBar(context.getString(R.string.duration_must_be_less_than_10_nights));
            else
                showDurationErrorSnackBar(context.getString(R.string.minimum_Duration_is_1_night));
        }
        else
        {
            checkOutFunc();
        }
    }

    private void init() {
        dateTextview = view.findViewById(R.id.tv_flight_date_picker_month);
        dateOfWeekTextview = view.findViewById(R.id.tv_flight_date_picker_weekday);
        checkOutTitle = view.findViewById(R.id.tv_datepicker_checkout);
        calendar = view.findViewById(R.id.calendar_datepicker);
        fromDateButton = view.findViewById(R.id.button_datepicker_from);
        toDateButton = view.findViewById(R.id.button_datepicker_to);
        closeButton = view.findViewById(R.id.iv_datepicker_close);
        nightNumCalendarTextview = view.findViewById(R.id.tv_datepicker_night_num);
        nullTextview = view.findViewById(R.id.tv_datepicker_null);
        fromDateTextView =  fromDateButton.getButton();
        toDateTextView =  toDateButton.getButton();
        LinearLayout llBottomSheet = view.findViewById(R.id.ll_datepicker_bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        typeface = ResourcesCompat.getFont(context,R.font.roboto_medium);

        calendar.setFonts(typeface);
    }

    private void checkOutFunc() {

        calculateEndDate();

        toDateTextView.setTextColor(Color.parseColor("#ffffffff"));
        toDateTextView.setText(endDateString);
        fromDateTextView.setText(startDateString);

        setupOkSnackbar();
    }

    private void setupOkSnackbar() {
        if (endDateString.equals(startDateString)){
            okSnackbar = Snackbar.make(view, context.getString(R.string.select_the_date) + " " + startDateString + " ?" , Snackbar.LENGTH_INDEFINITE);
        }else {
            okSnackbar = Snackbar.make(view, context.getString(R.string.select_these_dates) + " "
                    + startDateString + " - " + endDateString + " ?" , Snackbar.LENGTH_INDEFINITE);
        }
        View sbView = okSnackbar.getView();
        sbView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupOkButton();
                okSnackbar.dismiss();
            }
        });
        sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        TextView tv = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else {
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
        }
        okSnackbar.show();
    }


    // change views
    public void checkInFunc() {

        fromDateTextView.setTextColor(Color.parseColor("#184890"));
        fromDateTextView.setText(startDateString);
        fromDateButton.setButtonStartColor(Color.parseColor("#00000000"));
        fromDateButton.setButtonEndColor(Color.parseColor("#00000000"));

        if (!FlightSearchFragment.isOneWay)
        {
            toDateButton.setVisibility(View.VISIBLE);
            toDateTextView.setVisibility(View.VISIBLE);
            checkOutTitle.setVisibility(View.VISIBLE);
            nullTextview.setVisibility(View.VISIBLE);
            nightNumCalendarTextview.setVisibility(View.VISIBLE);
            toDateButton.setButtonStartColor(Color.parseColor("#12d4fa"));
            toDateButton.setButtonEndColor(Color.parseColor("#12d4fa"));
            toDateTextView.setTextColor(Color.parseColor("#ffffffff"));
            toDateTextView.setText(context.getString(R.string.select_date));
        }
        else
        {
            toDateButton.setVisibility(View.GONE);
            toDateTextView.setVisibility(View.GONE);
            checkOutTitle.setVisibility(View.GONE);
            nightNumCalendarTextview.setVisibility(View.GONE);
            nullTextview.setVisibility(View.GONE);
        }
    }

    private void calculateEndDate() {
        endDateString = getDayFormat(end);
        startDateString = getDayFormat(start);
    }

    private void calculateStartDate() {
       startDateString = getDayFormat(start);
    }

    private void setupOkButton() {

        if (start != null)
        {
            fromDayOfWeek = getDayOfWeekName(start);
            toDayOfWeek =  getDayOfWeekName(end);
            ((MainFlightActivity)context).standardDate = start;
            setupDateTextview(startDateString,endDateString, fromDayOfWeek,toDayOfWeek, getStandardDateFormat(start) , getStandardDateFormat(end));
        }
        else
        {
            Toast.makeText(context,context.getString(R.string.please_select_a_date_range),Toast.LENGTH_SHORT).show();
        }
    }

    public void setupDateTextview(String from,String to,String fromDayOfWeek, String toDayOfWeek, String fromStandard , String toStandard)
    {
        if (FlightSearchFragment.isOneWay)
        {
            dateTextview.setText(from);
            dateOfWeekTextview.setText(fromDayOfWeek);
            ((MainFlightActivity)context).isDateChanged = true;
        }
        else
        {
            dateTextview.setText(from + " - " + to);
            dateOfWeekTextview.setText(fromDayOfWeek+ " - " + toDayOfWeek);
            ((MainFlightActivity)context).isDateChanged = true;
        }
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        resetCalendar();
        calendar.resetAllSelectedViews();
    }


    public void resetCalendar() {
        fromDateTextView.setText(context.getString(R.string.select_date));
        toDateTextView.setText(context.getString(R.string.select_date));
        nightNumCalendarTextview.setText("");
        calendar.setSelectedDateRange(null,null);

        toDateTextView.setTextColor(Color.parseColor("#ffffffff"));
        toDateButton.setButtonStartColor(Color.parseColor("#00000000"));
        toDateButton.setButtonEndColor(Color.parseColor("#00000000"));

        fromDateButton.setButtonStartColor(Color.parseColor("#12d4fa"));
        fromDateButton.setButtonEndColor(Color.parseColor("#12d4fa"));
        fromDateTextView.setTextColor(Color.parseColor("#ffffffff"));
    }

    private void showDurationErrorSnackBar(String s) {
        Snackbar snackbar = Snackbar.make(view, s, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
        TextView errorDatePicker =  sbView.findViewById(R.id.snackbar_text);
        errorDatePicker.setTextColor(ContextCompat.getColor(context, R.color.white));
        errorDatePicker.setTypeface(typeface);
        snackbar.show();
    }

    private String getDayFormat(Calendar calendar) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("E", Locale.getDefault());
        String dayOfWeekNameFrom = dayFormat.format(calendar.getTime());
        String monthNameFrom = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());

        return calendar.get(Calendar.DAY_OF_MONTH)
                + " " + monthNameFrom;
    }

    public String getStandardDateFormat(Calendar calendar)
    {
        android.text.format.DateFormat df = new android.text.format.DateFormat();

        return df.format("yyyy-MM-dd",calendar).toString();
    }

    private String getDayOfWeekName(Calendar calendar) {

        SimpleDateFormat dayFormat = new SimpleDateFormat("E", Locale.getDefault());
        return dayFormat.format(calendar.getTime());
    }
}
