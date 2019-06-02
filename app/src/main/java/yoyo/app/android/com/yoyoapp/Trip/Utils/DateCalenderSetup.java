package yoyo.app.android.com.yoyoapp.Trip.Utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.core.util.Consumer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateCalenderSetup {

    private Context context;
    private TextView textView;
    private DatePickerDialog.OnDateSetListener dateListner;

    public DateCalenderSetup(Context context, TextView textView, DatePickerDialog.OnDateSetListener dateListner,Consumer<Long> timeStampConsumer) {
        this.context = context;
        this.textView = textView;
        this.dateListner = dateListner;
        setupDatePickers(timeStampConsumer);
    }

    // date of birth date picker
    private void setupDatePickers(Consumer<Long> timeStampConsumer) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupCalenderDialog(dateListner); }
            });

        dateListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dateString = day + "/" + month + "/" + year;
                Date date = null;
                try {
                    date = dateFormat.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long time = date.getTime()/1000;
                timeStampConsumer.accept(time);
                // setup textview
                month = month + 1;
                String dateString2 = year + "-" + month + "-" + day;
                textView.setText(dateString2);
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
