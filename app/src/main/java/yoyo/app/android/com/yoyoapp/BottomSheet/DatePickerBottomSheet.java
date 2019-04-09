package yoyo.app.android.com.yoyoapp.BottomSheet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import yoyo.app.android.com.yoyoapp.R;
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.dagang.library.GradientButton;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

public class DatePickerBottomSheet {
    
    private Context context;
    private Calendar start ,end ,defaultC = Calendar.getInstance();
    private TextView checkInEditText ,checkOutEditText;
    private TextView nightNumCalendarTextview ,nightNumTextview;
    public static String startDateString, endDateString ;
    private DateRangeCalendarView calendar;
    public static long diffDays;
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
    }

    private void setupCloseButton() {
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
    }

    private void setupDefaultDateValue() {

        startDateString = getDayFormat(defaultC);
        defaultC.add(Calendar.DAY_OF_YEAR, +7);
        endDateString = getDayFormat(defaultC);

        checkInEditText.setText(startDateString);
        checkOutEditText.setText(endDateString);
        nightNumTextview.setText(String.valueOf(7));
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
        if (diffDays > 10 || diffDays == 0)
        {
            resetCalendar();
            if (diffDays >10)
            showDurationErrorSnackBar("Duration must be less than 10 nights");
            else
                showDurationErrorSnackBar("Minimum Duration is 1 night");
        }
        else
        {
            checkOutFunc();
        }
    }

    private void init() {
        checkInEditText = view.findViewById(R.id.tv_search_check_in);
        checkOutEditText = view.findViewById(R.id.tv_search_check_out);
        nightNumTextview= view.findViewById(R.id.tv_search_night_num);
        calendar = view.findViewById(R.id.calendar_datepicker);
        fromDateButton = view.findViewById(R.id.button_datepicker_from);
        toDateButton = view.findViewById(R.id.button_datepicker_to);
        closeButton = view.findViewById(R.id.iv_datepicker_close);
        nightNumCalendarTextview = view.findViewById(R.id.tv_datepicker_night_num);
        fromDateTextView =  fromDateButton.getButton();
        toDateTextView =  toDateButton.getButton();
        LinearLayout llBottomSheet = view.findViewById(R.id.ll_datepicker_bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        typeface = ResourcesCompat.getFont(context,R.font.roboto_medium);

        calendar.setFonts(typeface);
    }

    private void checkOutFunc() {

        calculateEndDate();

        toDateTextView.setTextColor(Color.parseColor("#0265d3"));
        toDateTextView.setText(endDateString);
        fromDateTextView.setText(startDateString);
        okSnackbar = Snackbar.make(view, "Setting this Date?", Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setupOkButton();
                    }
                }).setActionTextColor(Color.parseColor("#ffffffff"));
        View sbView = okSnackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        TextView errorDatePicker = (TextView) sbView.findViewById(R.id.snackbar_text);
        errorDatePicker.setTextColor(ContextCompat.getColor(context, R.color.white));
        errorDatePicker.setTypeface(typeface);
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
        toDateTextView.setTextColor(Color.parseColor("#0265d3"));
    }

    private void calculateEndDate() {
        endDateString = getDayFormat(end);
        startDateString = getDayFormat(start);
    }

    private void calculateStartDate() {
       startDateString = getDayFormat(start);
    }

    private void setupOkButton() {

        if (start != null && end != null)
        {
            setupDateTextview(startDateString,endDateString);
        }
        else
        {
            Toast.makeText(context,"Please select a date range",Toast.LENGTH_SHORT).show();
        }
    }

    public void setupDateTextview(String from,String to)
    {
        checkInEditText.setText( from );
        checkOutEditText.setText( to);
        nightNumTextview.setText(String.valueOf(diffDays) + "  Nights");
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        calendar.setEditable(true);
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
        sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
        TextView errorDatePicker = (TextView) sbView.findViewById(R.id.snackbar_text);
        errorDatePicker.setTextColor(ContextCompat.getColor(context, R.color.white));
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
