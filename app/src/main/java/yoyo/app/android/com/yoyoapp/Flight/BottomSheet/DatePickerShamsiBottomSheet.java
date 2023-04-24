package yoyo.app.android.com.yoyoapp.Flight.BottomSheet;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.dagang.library.GradientButton;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import ir.mirrajabi.persiancalendar.PersianCalendarView;
import ir.mirrajabi.persiancalendar.core.PersianCalendarHandler;
import ir.mirrajabi.persiancalendar.core.interfaces.OnDayClickedListener;
import ir.mirrajabi.persiancalendar.core.interfaces.OnMonthChangedListener;
import ir.mirrajabi.persiancalendar.core.models.CivilDate;
import ir.mirrajabi.persiancalendar.core.models.PersianDate;
import ir.mirrajabi.persiancalendar.helpers.DateConverter;
import yoyo.app.android.com.yoyoapp.Flight.MainFlightActivity;
import yoyo.app.android.com.yoyoapp.R;

import java.util.Calendar;

public class DatePickerShamsiBottomSheet{


    private Snackbar okSnackbar;
    private GradientButton fromDateButton;
    private TextView monthTextview ,fromDateTextView, datePickerDateTextview ,datePickerMonthTextview;
    private String startDateString;
    private PersianDate selectedDate;
    private View view;
    private Context context;
    private PersianCalendarView persianCalendarView;
    private PersianCalendarHandler calendar;
    private PersianDate today;
    private ImageView closeButton;
    private BottomSheetBehavior bottomSheetBehavior;

    public DatePickerShamsiBottomSheet(Context context, View view) {
        this.view = view;
        this.context = context;
        persianCalendarView = view.findViewById(R.id.persian_calendar);
        calendar = persianCalendarView.getCalendar();
        today = calendar.getToday();

        init();
        setupCalendar();
        setupCloseButton();
    }

    private void init() {
        fromDateButton = view.findViewById(R.id.button_datepickershamsi_from);
        monthTextview = view.findViewById(R.id.tv_calendershamsi_month);
        fromDateTextView =  fromDateButton.getButton();
        closeButton = view.findViewById(R.id.iv_datepicker_shamsi_close);
        LinearLayout llBottomSheet = view.findViewById(R.id.ll_datepicker_bottom_sheet_shamsi);
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        datePickerDateTextview = view.findViewById(R.id.tv_flight_date_picker_weekday);
        datePickerMonthTextview = view.findViewById(R.id.tv_flight_date_picker_month);
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

    // setup calender
    private void setupCalendar() {
        monthTextview.setText(calendar.getMonthName(today));

        calendar.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onChanged(PersianDate date) {
                monthTextview.setText(calendar.getMonthName(date));
            }
        });

        persianCalendarView.setOnDayClickedListener(new OnDayClickedListener() {
            @Override
            public void onClick(PersianDate date) {

                selectedDate = date;
                startDateString = date.getYear() + "-" + date.getMonth() +  "-" + date.getDayOfMonth();
                fromDateButton.setButtonStartColor(Color.parseColor("#00000000"));
                fromDateButton.setButtonEndColor(Color.parseColor("#00000000"));
                fromDateTextView.setTextColor(Color.parseColor("#184890"));
                fromDateTextView.setText(calendar.getWeekDayName(selectedDate) + " " +startDateString);
                setupOkSnackbar();
            }
        });
    }

    // show date snack bar
    private void setupOkSnackbar() {

        okSnackbar = Snackbar.make(view, context.getString(R.string.select_the_date) + " " + startDateString + " ؟" , Snackbar.LENGTH_INDEFINITE);
        View sbView = okSnackbar.getView();
        sbView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupOkButton();
                okSnackbar.dismiss();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

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

    // save the selected day to main activity
    private void setupOkButton() {
        PersianDate persianDate = new PersianDate(selectedDate.getYear(),selectedDate.getMonth(),selectedDate.getDayOfMonth());
        CivilDate x = DateConverter.persianToCivil(persianDate);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, x.getYear());
        cal.set(Calendar.MONTH, x.getMonth() -1);
        cal.set(Calendar.DAY_OF_MONTH, x.getDayOfMonth());
        ((MainFlightActivity)context).standardDate = cal;
        ((MainFlightActivity)context).isDateChanged = true;
        datePickerDateTextview.setText(calendar.getMonthName(selectedDate));
        datePickerMonthTextview.setText(String.valueOf(selectedDate.getDayOfMonth()));
    }
}
