package yoyo.app.android.com.yoyoapp.Flight.Utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class DateCalenderSetup {

    private Context context;
    private TextView textView;
    private DatePickerDialog.OnDateSetListener dateListner;

    public DateCalenderSetup(Context context, TextView textView, DatePickerDialog.OnDateSetListener dateListner) {
        this.context = context;
        this.textView = textView;
        this.dateListner = dateListner;
        setupDatePickers();
    }

    // date of birth date picker
    private void setupDatePickers() {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupCalenderDialog(dateListner); }
            });

        dateListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "-" + month + "-" + day;
                textView.setText(date);
            }
        };

    }

    // setup calender dialog
    private void setupCalenderDialog(DatePickerDialog.OnDateSetListener dateSetListner) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                context,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                dateSetListner,
                year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}
