package yoyo.app.android.com.yoyoapp.Trip.schedule.request;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.JsonArray;
import es.dmoral.toasty.Toasty;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.DataModels.DateCalender;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.adapter.DateCalenderRecyclerViewAdapter;
import yoyo.app.android.com.yoyoapp.Trip.booking.BookingActivity;

import java.util.ArrayList;


public class RequestFragment extends Fragment {

    private EditText firstnameEditText,phoneNumberEditText,lastnameEditText, emailEditText;
    private Button sendButton;
    private DatePickerDialog.OnDateSetListener dateOfBirthListner;
    private TextView passengerCount, date1Textview, calenderCount;
    private ImageView minusImageview, plusImageview,minusCalenderImageview, pluscalenderImageview, backButton;
    private RequstViewModel requstViewModel;
    private ArrayList<DateCalender> dateCalenders;
    private RecyclerView dateCalenderRecyclerView;
    private DateCalenderRecyclerViewAdapter adapter;
    private int passengerNum = 1, calenderNum = 1;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_request, container, false);

        init();
        Bundle bundle = getArguments();
        String tripId = bundle.getString("tripId");
        sendButton.setOnClickListener(v-> {if(checkingFields()) sendRequest(getJson(), tripId); });
        setupRecyclerview();
        pluscalenderImageview.setOnClickListener(v -> addCalenderNumber());
        minusCalenderImageview.setOnClickListener(v -> reduceCalenderNumber());
        plusImageview.setOnClickListener(v -> addNumber());
        minusImageview.setOnClickListener(v -> reduceNumber());
        backButton.setOnClickListener(v-> getFragmentManager().popBackStack());
        return view;
    }

    private void reduceNumber() {
        if (passengerNum > 1)
        {
            passengerNum--;
            passengerCount.setText(String.valueOf(passengerNum));
        }
        if (passengerNum == 1)
        {
            minusImageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_light_24dp));
        }
    }

    private void addNumber() {
        if (passengerNum < 9) {
            passengerNum++;
            passengerCount.setText(String.valueOf(passengerNum));
        }
        if (passengerNum ==2)
        {
            appearView(minusImageview, passengerCount);
        }
    }

    private void setupRecyclerview() {
        dateCalenders.add(new DateCalender());
       adapter = new DateCalenderRecyclerViewAdapter(dateCalenders, getContext(), (timestamp, position, standardDate) -> {
           dateCalenders.get(position).setTimeStamp(timestamp);
           dateCalenders.get(position).setStandardDate(standardDate);
       });
       dateCalenderRecyclerView.setAdapter(adapter);
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
       dateCalenderRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void reduceCalenderNumber() {
        if (calenderNum > 1) {
            calenderNum--;
            calenderCount.setText(String.valueOf(calenderNum));
            dateCalenders.remove(dateCalenders.size() - 1);
            adapter.notifyDataSetChanged();
        }
        if (calenderNum == 1)
        {
            minusCalenderImageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_light_24dp));
        }
    }

    private void addCalenderNumber() {
        if (calenderNum < 9) {
            calenderNum++;
            calenderCount.setText(String.valueOf(calenderNum));
            DateCalender dateCalender = new DateCalender();
            dateCalenders.add(dateCalender);
            adapter.notifyDataSetChanged();
        }
        if (calenderNum >= 2)
        {
            appearView(minusCalenderImageview, calenderCount);
        }
    }

    private boolean checkingFields() {
        if (TextUtils.isEmpty(firstnameEditText.getText()) ||
                TextUtils.isEmpty(lastnameEditText.getText()) ||
                TextUtils.isEmpty(emailEditText.getText()) ||
                TextUtils.isEmpty(phoneNumberEditText.getText()))
        {
            Toasty.error(getContext(),"Please complete all fields").show();
            return false;
        }
        for (int i = 0; i < dateCalenders.size(); i++) {
            if (dateCalenders.get(i).getTimeStamp() == null)
            {
                Toasty.error(getContext(),"Please select your desire dates").show();
                return false;
            }
        }
        return true;
    }
    private JSONObject getJson() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < dateCalenders.size(); i++) {
            jsonArray.put(dateCalenders.get(i).getTimeStamp());
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("full_name",firstnameEditText.getText().toString()+" " + lastnameEditText.getText().toString());
            jsonObject.put("email", emailEditText.getText().toString());
            jsonObject.put("phone_number",phoneNumberEditText.getText().toString());
            jsonObject.put("passengers_count", passengerNum);
            jsonObject.put("dates",jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void init() {
        firstnameEditText = view.findViewById(R.id.et_request_firstname);
        lastnameEditText = view.findViewById(R.id.et_request_lastname);
        emailEditText = view.findViewById(R.id.et_request_email);
        phoneNumberEditText = view.findViewById(R.id.et_request_phone_number);
        passengerCount = view.findViewById(R.id.tv_request_num);
        sendButton = view.findViewById(R.id.button_request_send);
        minusImageview = view.findViewById(R.id.iv_request_minus);
        plusImageview = view.findViewById(R.id.iv_request_plus);
        minusCalenderImageview = view.findViewById(R.id.iv_request_calender_minus);
        pluscalenderImageview = view.findViewById(R.id.iv_request_calender_plus);
        calenderCount = view.findViewById(R.id.tv_request_calender_num);
        backButton = view.findViewById(R.id.iv_request_back);
        dateCalenderRecyclerView = view.findViewById(R.id.rv_request_date_calender);
        dateCalenders = new ArrayList<>();
    }

    private void sendRequest(JSONObject jsonObject, String tripId) {
        requstViewModel = ViewModelProviders.of(getActivity()).get(RequstViewModel.class);
        requstViewModel.initRequest(tripId, jsonObject);
        requstViewModel.getResult().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                if (result){
                    Toasty.normal(getContext(),"your Request has sent successfully").show();
                    getFragmentManager().popBackStack();
                }
                else
                {
                    Toasty.normal(getContext(),"failed to send Request").show();
                }
            }
        });
    }

    public void appearView(ImageView imageView, TextView textView)
    {
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_black_24dp));
        textView.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

}
