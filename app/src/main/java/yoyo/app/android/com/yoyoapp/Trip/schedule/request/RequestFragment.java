package yoyo.app.android.com.yoyoapp.Trip.schedule.request;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.Utils.DateCalenderSetup;


public class RequestFragment extends Fragment {

    private EditText firstnameEditText,phoneNumberEditText,lastnameEditText, emailEditText;
    private Button sendButton;
    private DatePickerDialog.OnDateSetListener dateOfBirthListner;
    private TextView passengerCount, date1Textview;
    private ImageView minusImageview, plusImageview;
    private RequstViewModel requstViewModel;
    private JSONArray jsonArray;
    private int passengerNum = 1;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_request, container, false);

        init();
        String tripId ="ec744fa5-9467-4e87-a1a7-d69f656b706a";
        sendButton.setOnClickListener(v-> sendRequest(getJson(), tripId));
        setupDatePickers();
        plusImageview.setOnClickListener(v -> addNumber());
        minusImageview.setOnClickListener(v -> reduceNumber());

        return view;
    }

    private void reduceNumber() {
        if (passengerNum > 1) {
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

    private void setupDatePickers() {
        new DateCalenderSetup(getContext(),date1Textview,dateOfBirthListner,timeStamp ->{
            jsonArray = new JSONArray();
            try {
                jsonArray.put(0,String.valueOf(timeStamp));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }


    private JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("full_name",firstnameEditText.getText().toString()+" " + lastnameEditText.getText().toString());
            jsonObject.put("email", emailEditText.getText().toString());
            jsonObject.put("phone_number",phoneNumberEditText.getText().toString());
            jsonObject.put("passengers_count", passengerCount);
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
        date1Textview = view.findViewById(R.id.tv_request_date1);
        passengerCount = view.findViewById(R.id.tv_request_num);
        sendButton = view.findViewById(R.id.button_request_send);
        minusImageview = view.findViewById(R.id.iv_request_minus);
        plusImageview = view.findViewById(R.id.iv_request_plus);
    }

    private void sendRequest(JSONObject jsonObject, String tripId) {
        requstViewModel = ViewModelProviders.of(getActivity()).get(RequstViewModel.class);
        requstViewModel.initRequest(tripId, jsonObject);
        requstViewModel.getResult().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                if (result)
                Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void appearView(ImageView imageView, TextView textView)
    {
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_black_24dp));
        textView.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

}